/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

/**
 *
 * @author namnq
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TServers tServers = new TServers();
        if (!tServers.setupAndStart()) {
            System.err.println("Could not start thrift servers! Exit now.");
            System.exit(1);
        }

    }
}
