package com.example.intern.service;

import com.example.intern.model.ClickHouseConfig;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClickHouseService {

    private Connection connection;
    private String csvPath = "data/sample_data.csv";

    public List<String> getTables(ClickHouseConfig config) throws SQLException {
        configureClickHouse(config);
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
            List<String> tables = new ArrayList<>();
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
            return tables;
        }
    }

    public List<String> getColumns(String sourceType, String table) throws Exception {
        if ("ClickHouse".equals(sourceType)) {
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("DESCRIBE TABLE " + table)) {
                List<String> columns = new ArrayList<>();
                while (rs.next()) {
                    columns.add(rs.getString("name"));
                }
                return columns;
            }
        } else {
            try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
                return List.of(reader.readNext());
            }
        }
    }

    public long ingestData(String sourceType, String table, List<String> columns, String targetType, MultipartFile file) throws Exception {
        if ("ClickHouse".equals(sourceType)) {
            return ingestFromClickHouse(table, columns);
        } else {
            if (file != null) {
                saveUploadedFile(file);
            }
            return ingestFromFlatFile(table, columns);
        }
    }

    private void configureClickHouse(ClickHouseConfig config) throws SQLException {
        String url = String.format("jdbc:ch://%s:%s/%s?ssl=true", config.getHost(), config.getPort(), config.getDatabase());
        connection = DriverManager.getConnection(url, config.getUser(), config.getJwt());
    }

    private long ingestFromClickHouse(String table, List<String> columns) throws SQLException, IOException {
        String query = "SELECT " + String.join(", ", columns) + " FROM " + table;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             CSVWriter writer = new CSVWriter(new FileWriter(csvPath))) {
            writer.writeNext(columns.toArray(new String[0]));
            long count = 0;
            while (rs.next()) {
                String[] row = new String[columns.size()];
                for (int i = 0; i < columns.size(); i++) {
                    row[i] = rs.getString(i + 1);
                }
                writer.writeNext(row);
                count++;
            }
            return count;
        }
    }

    private long ingestFromFlatFile(String table, List<String> columns) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            String[] headers = reader.readNext();
            List<Integer> columnIndices = new ArrayList<>();
            for (String col : columns) {
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].equals(col)) {
                        columnIndices.add(i);
                        break;
                    }
                }
            }

            String insertQuery = "INSERT INTO " + table + " (" + String.join(", ", columns) + ") VALUES ("
                    + "?,".repeat(columns.size() - 1) + "?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                long count = 0;
                String[] row;
                while ((row = reader.readNext()) != null) {
                    for (int i = 0; i < columnIndices.size(); i++) {
                        pstmt.setString(i + 1, row[columnIndices.get(i)]);
                    }
                    pstmt.executeUpdate();
                    count++;
                }
                return count;
            }
        }
    }

    private void saveUploadedFile(MultipartFile file) throws IOException {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();
        File dest = new File(csvPath);
        file.transferTo(dest);
    }
}