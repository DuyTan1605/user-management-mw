/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.connection;

/**
 *
 * @author tanhd
 */
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author tanhd
 */
public class ConnectionManager {

    private static String dbUrl;
    private static String userName;
    private static String password;

    public ConnectionManager() {
    }

    public ConnectionManager(String dbUrl, String userName, String password) {
        ConnectionManager.dbUrl = dbUrl;
        ConnectionManager.userName = userName;
        ConnectionManager.password = password;
    }

    public Connection createConnection() {
        try {
            getConnectionData();
            System.out.println("connect successfully!");
            return DriverManager.getConnection(dbUrl, userName, password);
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
            return null;
        }
    }

    public void getConnectionData() {
        try {
            FileInputStream file = new FileInputStream("db.properties");
            Properties props = new Properties();
            props.load(file);
            new ConnectionManager(props.getProperty("DB_URL"), props.getProperty("USER_NAME"), props.getProperty("PASSWORD"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
