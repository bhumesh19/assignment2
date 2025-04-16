package com.example.intern.controller;

import com.example.intern.model.IngestionRequest;
import com.example.intern.service.ClickHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class IngestionController {

    @Autowired
    private ClickHouseService clickHouseService;

    @PostMapping("/connect")
    public ResponseEntity<Map<String, Object>> connect(@RequestBody IngestionRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            if ("ClickHouse".equals(request.getSourceType())) {
                List<String> tables = clickHouseService.getTables(request.getClickHouseConfig());
                response.put("tables", tables);
            } else {
                // For FlatFile, return filename as table
                response.put("tables", List.of(request.getFlatFileName()));
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/columns")
    public ResponseEntity<Map<String, Object>> getColumns(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<String> columns = clickHouseService.getColumns(
                    request.get("sourceType"),
                    request.get("table")
            );
            response.put("columns", columns);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping(value = "/ingest", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, Object>> ingest(
            @RequestPart("request") IngestionRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            long recordCount = clickHouseService.ingestData(
                    request.getSourceType(),
                    request.getTable(),
                    request.getColumns(),
                    request.getTargetType(),
                    file
            );
            response.put("recordCount", recordCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}