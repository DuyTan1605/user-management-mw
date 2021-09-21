/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.vng.zing.dmp.common.interceptor.ApiProfiler;
import com.vng.zing.dmp.common.interceptor.ThreadProfiler;
import com.vng.zing.managementuser.dao.UserDAO;
import com.vng.zing.stats.Profiler;
import com.vng.zing.userservice.thrift.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tanhd
 */
public class UserListService {

    private UserDAO userDAO;

    @Inject
    public UserListService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @ApiProfiler
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        users = this.userDAO.getUsers();
        return users;
    }
}
