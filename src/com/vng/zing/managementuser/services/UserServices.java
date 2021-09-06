/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.dmp.common.exception.ZInvalidParamException;
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

    private ValidateService validateService = new ValidateService();
    private UserDAO userDAO = new UserDAO();

    public User getUser(int id) {
        if (id <= 0) {
            throw new ZInvalidParamException("User ID is not valid");
        }
        User user = userDAO.getUser(id);
        return user;
    }

    public void createUser(CreateUserParams params) throws ParseException, JSONException, NotExistException, NoSuchAlgorithmException, SQLException {
        validateService.validateCreateUserParams(params.user);
        userDAO.createUser(params);
    }

    public void updateUser(UpdateUserParams params) throws ParseException, JSONException, NotExistException, SQLException {
        validateService.validateUpdateUserParams(params.user);
        userDAO.updateUser(params);
    }

    public void deleteUser(DeleteUserParams params) throws NotExistException, SQLException {
        if (params.id <= 0) {
            throw new ZInvalidParamException("User ID is not valid");
        }
        userDAO.deleteUser(params);
    }
}
