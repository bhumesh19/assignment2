package com.example.intern;

import com.example.intern.model.ClickHouseConfig;
import com.example.intern.service.ClickHouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClickHouseServiceTest {

    @Autowired
    private ClickHouseService clickHouseService;

    private ClickHouseConfig config;

    @BeforeEach
    void setUp() {
        config = new ClickHouseConfig();
        config.setHost("localhost");
        config.setPort("8123");
        config.setDatabase("default");
        config.setUser("user1");
        config.setJwt("password1");
    }

    @Test
    void testGetTables() throws Exception {
        List<String> tables = clickHouseService.getTables(config);
        assertNotNull(tables);
        assertTrue(tables.contains("uk_price_paid") || tables.contains("ontime"));
    }

    @Test
    void testGetColumnsFromClickHouse() throws Exception {
        List<String> columns = clickHouseService.getColumns("ClickHouse", "uk_price_paid");
        assertNotNull(columns);
        assertTrue(columns.contains("price"));
    }
}