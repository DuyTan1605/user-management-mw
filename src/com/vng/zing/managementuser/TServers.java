/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser;

import com.google.inject.Inject;
import com.vng.zing.thriftserver.ThriftServers;
import com.vng.zing.managementuser.handlers.UserHandler;
import com.vng.zing.userservice.thrift.UserService;

/**
 *
 * @author namnq
 */
public class TServers {

    private UserHandler userHanler;

    @Inject
    public TServers(UserHandler userHanler) {
        this.userHanler = userHanler;
    }

    public boolean setupAndStart() {
        ThriftServers servers = new ThriftServers("Main");
        UserService.Processor processor = new UserService.Processor(userHanler);
        servers.setup(processor);
        return servers.start();
    }
}
