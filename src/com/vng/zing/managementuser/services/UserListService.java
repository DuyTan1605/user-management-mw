/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.managementuser.dao.UserDAO;
import com.vng.zing.userservice.thrift.User;
import java.util.List;

/**
 *
 * @author tanhd
 */
public class UserListService {

    private UserDAO userDAO = new UserDAO();

    public List<User> getUsers() {
        return userDAO.getUsers();
    }
}
