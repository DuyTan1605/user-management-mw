/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.userservice.thrift.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tanhd
 */
public class ValidateService {

    public boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^[A-Za-z]{5,29}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(name);
            return m.matches();
        }
    }

    public boolean validateUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^[A-Za-z0-9]{5,29}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(userName);
            return m.matches();
        }
    }

    public boolean validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(password);
            return m.matches();
        }
    }

    public void validateCreateUserParams(User user) throws ParseException, JSONException {
        if (user.birthday <= 0 || (user.gender.getValue() != 1 && user.gender.getValue() != 0) || !validateName(user.name) || !validateUserName(user.username) || !validatePassword(user.password)) {
            throw new ZInvalidParamException("User data " + user + " not valid");
        }
    }

    public void validateUpdateUserParams(User user) throws ParseException, JSONException {
        validateCreateUserParams(user);
        if (user.id <= 0) {
            throw new ZInvalidParamException("User data " + user + " not valid");
        }
    }
}
