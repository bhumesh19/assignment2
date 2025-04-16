package com.example.intern.model;

public class ClickHouseConfig {
    private String host;
    private String port;
    private String database;
    private String user;
    private String jwt;

    // Getter and Setter for host
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    // Getter and Setter for port
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    // Getter and Setter for database
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    // Getter and Setter for user
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // Getter and Setter for jwt
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
