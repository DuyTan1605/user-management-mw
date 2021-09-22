/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.google.inject.Inject;
import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.dmp.common.interceptor.ApiProfiler;
import com.vng.zing.exception.NotExistException;
import com.vng.zing.managementuser.dao.UserDAO;
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

    private ValidateService validateService;
    private UserDAO userDAO;

    @Inject
    public UserServices(UserDAO userDAO, ValidateService validateService) {
        this.userDAO = userDAO;
        this.validateService = validateService;
    }

    @ApiProfiler
    public User getUser(int id) {
        User user = new User();
        if (id <= 0) {
            throw new ZInvalidParamException("User ID is not valid");
        }
        user = userDAO.getUser(id);
        return user;
    }

    @ApiProfiler
    public void createUser(CreateUserParams params) throws ParseException, JSONException, NotExistException, NoSuchAlgorithmException, SQLException {
        validateService.validateCreateUserParams(params.user);
        userDAO.createUser(params);
    }

    @ApiProfiler
    public void updateUser(UpdateUserParams params) throws ParseException, JSONException, NotExistException, SQLException {
        validateService.validateUpdateUserParams(params.user);
        userDAO.updateUser(params);
    }

    @ApiProfiler
    public void deleteUser(DeleteUserParams params) throws NotExistException, SQLException {
        if (params.id <= 0) {
            throw new ZInvalidParamException("User ID is not valid");
        }
        userDAO.deleteUser(params);
    }
}
