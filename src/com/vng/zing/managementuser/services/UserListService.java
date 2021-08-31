/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.dao.UserDAO;
import com.vng.zing.managementuser.utils.DateTimeUtils;
import com.vng.zing.userservice.thrift.Gender;
import com.vng.zing.userservice.thrift.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author tanhd
 */
public class UserListService {

    private static final Logger _Logger = ZLogger.getLogger(UserListService.class);

    public static List<User> getUsers() {
        List<User> listUser = UserDAO.getUsers();
        return listUser;
    }
}
