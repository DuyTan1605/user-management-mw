/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.exception.NotExistException;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.dao.UserDAO;
import com.vng.zing.userservice.thrift.CreateUserParams;
import com.vng.zing.userservice.thrift.DeleteUserParams;
import com.vng.zing.userservice.thrift.UpdateUserParams;
import com.vng.zing.userservice.thrift.User;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tanhd
 */
public class UserServices {

    private static final Logger _Logger = ZLogger.getLogger(UserListService.class);
    public static final ValidateService validateService = new ValidateService();

    public static User getUser(int id) {
        if (!validateService.validateId(id)) {
            throw new ZInvalidParamException("User ID is not valid");
        } else {
            User user = UserDAO.getUser(id);
            return user;
        }
    }

    public static int createUser(CreateUserParams params) throws ParseException, JSONException, NotExistException {
        validateService.validateCreateUserParams(params.user);
        int effectedRow = UserDAO.createUser(params);
        return effectedRow;
    }

    public static int updateUser(UpdateUserParams params) throws ParseException, JSONException, NotExistException {
        validateService.validateUpdateUserParams(params.user);
        int effectedRow = UserDAO.updateUser(params);
        return effectedRow;
    }

    public static int deleteUser(DeleteUserParams params) throws NotExistException {
        if (!validateService.validateId(params.id)) {
            throw new ZInvalidParamException("User ID is not valid");
        } else {
            int effectedRow = UserDAO.deleteUser(params);
            return effectedRow;
        }
    }
}
