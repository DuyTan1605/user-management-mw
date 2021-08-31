/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.dao.UserDAO;
import com.vng.zing.managementuser.utils.DateTimeUtils;
import com.vng.zing.userservice.thrift.CreateUserParams;
import com.vng.zing.userservice.thrift.DeleteUserParams;
import com.vng.zing.userservice.thrift.Gender;
import com.vng.zing.userservice.thrift.UpdateUserParams;
import com.vng.zing.userservice.thrift.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author tanhd
 */
public class UserServices {

    private static final Logger _Logger = ZLogger.getLogger(UserListService.class);

    public static User getUser(int id) {
        User user = UserDAO.getUser(id);
        return user;
    }

    public static int createUser(CreateUserParams params) {
        int effectedRow = UserDAO.createUser(params);
        return effectedRow;
    }

    public static int updateUser(UpdateUserParams params) {
        int effectedRow = UserDAO.updateUser(params);
        return effectedRow;
    }

    public static int deleteUser(DeleteUserParams params) {
        int effectedRow = UserDAO.deleteUser(params);
        return effectedRow;
    }
}
