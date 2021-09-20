/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
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

    public List<User> getUsers() {
        Profiler.getThreadProfiler().push(this.getClass(), "getUsers");
        List<User> users = new ArrayList<User>();
        try {
            users = this.userDAO.getUsers();
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "getUsers");
        }
        return users;
    }
}
