/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.modules;

import com.google.inject.AbstractModule;
import com.vng.zing.managementuser.dao.UserDAO;

/**
 *
 * @author tanhd
 */
public class UserListModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserDAO.class);
    }

}
