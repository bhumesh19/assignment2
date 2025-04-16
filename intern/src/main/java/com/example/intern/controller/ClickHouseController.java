//// src/main/java/com/example/demo/ClickHouseController.java
//package com.example.intern.controller;
//
//import com.example.intern.service.ClickHouseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class ClickHouseController {
//
//    @Autowired
//    private ClickHouseService clickHouseService;
//
//    @GetMapping("/records")
//    public List<Map<String, Object>> getRecords() {
//        return clickHouseService.getAllRecords();
//    }
//}

package com.example.intern.controller;

import com.example.intern.model.ClickHouseConfig;
import com.example.intern.service.ClickHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clickhouse")
public class ClickHouseController {

    @Autowired
    private ClickHouseService clickHouseService;

    // Endpoint to get tables from ClickHouse
    @GetMapping("/tables")
    public List<String> getTables(@RequestParam String host, @RequestParam String port,
                                  @RequestParam String database, @RequestParam String user,
                                  @RequestParam String jwt) throws Exception {
        ClickHouseConfig config = new ClickHouseConfig();
        config.setHost(host);
        config.setPort(port);
        config.setDatabase(database);
        config.setUser(user);
        config.setJwt(jwt);
        return clickHouseService.getTables(config);
    }

    // Endpoint to get columns from a table in ClickHouse
    @GetMapping("/columns")
    public List<String> getColumns(@RequestParam String host, @RequestParam String port,
                                   @RequestParam String database, @RequestParam String user,
                                   @RequestParam String jwt, @RequestParam String table) throws Exception {
        ClickHouseConfig config = new ClickHouseConfig();
        config.setHost(host);
        config.setPort(port);
        config.setDatabase(database);
        config.setUser(user);
        config.setJwt(jwt);
        return clickHouseService.getColumns(config.getDatabase(), table);
    }
}
