/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vng.zing.dmp.common.module.CommonModule;
import com.vng.zing.managementuser.dao.ConnectionManager;
import com.vng.zing.managementuser.dao.UserDAO;

/**
 *
 * @author namnq
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CommonModule());
        TServers tServers = injector.getInstance(TServers.class);
        if (!tServers.setupAndStart()) {
            System.err.println("Could not start thrift servers! Exit now.");
            System.exit(1);
        }

    }
}
