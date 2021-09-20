/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vng.zing.thriftserver.ThriftServers;
import com.vng.zing.managementuser.handlers.UserHandler;
import com.vng.zing.managementuser.modules.UserListModule;
import com.vng.zing.managementuser.modules.UserModule;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.managementuser.services.UserServices;
import com.vng.zing.userservice.thrift.UserService;

/**
 *
 * @author namnq
 */
public class TServers {

    public boolean setupAndStart() {
        ThriftServers servers = new ThriftServers("Main");
        Injector injector = Guice.createInjector(new UserListModule(), new UserModule());
        
        UserListService userListService = injector.getInstance(UserListService.class);
        UserServices userServices = injector.getInstance(UserServices.class);
        
        UserService.Processor processor = new UserService.Processor(new UserHandler(userListService, userServices));
        
        servers.setup(processor);
        return servers.start();
    }
}
