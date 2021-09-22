/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.dao;

/**
 *
 * @author tanhd
 */
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import com.vng.zing.configer.ZConfig;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author tanhd
 */
public class ConnectionManager {

    private String dbUrl;
    private String userName;
    private String password;

    public ConnectionManager() {
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection createConnection() {
        try {
            setConnectionData();
            System.out.println("connect successfully!");
            return DriverManager.getConnection(dbUrl, userName, password);
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
            return null;
        }
    }

    public void setConnectionData() {
        try {
            setDbUrl(ZConfig.Instance.getString(ConnectionManager.class, "Main", "DB_URL"));
            setUserName(ZConfig.Instance.getString(ConnectionManager.class, "Main", "USER_NAME"));
            setPassword(ZConfig.Instance.getString(ConnectionManager.class, "Main", "PASSWORD"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
