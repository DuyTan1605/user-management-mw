/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.exception.NotExistException;
import com.vng.zing.managementuser.dao.UserDAO;
import com.vng.zing.stats.Profiler;
import com.vng.zing.userservice.thrift.CreateUserParams;
import com.vng.zing.userservice.thrift.DeleteUserParams;
import com.vng.zing.userservice.thrift.UpdateUserParams;
import com.vng.zing.userservice.thrift.User;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tanhd
 */
public class UserServices {

    private ValidateService validateService = new ValidateService();
    private UserDAO userDAO = new UserDAO();

    public User getUser(int id) {
        Profiler.getThreadProfiler().push(this.getClass(), "getUser");
        User user = new User();
        try {
            if (id <= 0) {
                throw new ZInvalidParamException("User ID is not valid");
            }
            user = userDAO.getUser(id);
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "getUser");
        }
        return user;
    }

    public void createUser(CreateUserParams params) throws ParseException, JSONException, NotExistException, NoSuchAlgorithmException, SQLException {
        Profiler.getThreadProfiler().push(this.getClass(), "createUser");
        try {
            validateService.validateCreateUserParams(params.user);
            userDAO.createUser(params);
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "createUser");
        }
    }

    public void updateUser(UpdateUserParams params) throws ParseException, JSONException, NotExistException, SQLException {
        Profiler.getThreadProfiler().push(this.getClass(), "updateUser");
        try {
            validateService.validateUpdateUserParams(params.user);
            userDAO.updateUser(params);
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "updateUser");
        }
    }

    public void deleteUser(DeleteUserParams params) throws NotExistException, SQLException {
        Profiler.getThreadProfiler().push(this.getClass(), "deleteUser");
        try {
            if (params.id <= 0) {
                throw new ZInvalidParamException("User ID is not valid");
            }
            userDAO.deleteUser(params);
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "deleteUser");
        }
    }
}
