/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.modules;

import com.google.inject.AbstractModule;
import com.vng.zing.managementuser.dao.UserDAO;
import com.vng.zing.managementuser.services.ValidateService;

/**
 *
 * @author tanhd
 */
public class UserModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserDAO.class);
        bind(ValidateService.class);
    }

}
