/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.handlers;

import com.mysql.cj.result.LocalDateTimeValueFactory;
import com.vng.zing.common.ZErrorDef;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.dao.ConnectionManager;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.managementuser.services.UserServices;
import com.vng.zing.userservice.thrift.CreateUserParams;
import com.vng.zing.userservice.thrift.CreateUserResult;
import com.vng.zing.userservice.thrift.DeleteUserParams;
import com.vng.zing.userservice.thrift.DeleteUserResult;
import com.vng.zing.userservice.thrift.DetailUserParams;
import com.vng.zing.userservice.thrift.DetailUserResult;
import com.vng.zing.userservice.thrift.Gender;
import com.vng.zing.userservice.thrift.ListUserParams;
import com.vng.zing.userservice.thrift.ListUserResult;
import com.vng.zing.userservice.thrift.UpdateUserParams;
import com.vng.zing.userservice.thrift.UpdateUserResult;
import com.vng.zing.userservice.thrift.User;
import com.vng.zing.userservice.thrift.UserService;
import com.vng.zing.managementuser.utils.DateTimeUtils;
import com.vng.zing.managementuser.utils.PasswordHasher;
import com.vng.zing.zcommon.thrift.ECode;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.DateFormatter;
import org.apache.thrift.TException;
import org.apache.log4j.Logger;

/**
 *
 * @author tanhd
 */
public class UserHandler implements UserService.Iface {

    public ConnectionManager connectionManager = new ConnectionManager();
    private static final Logger _Logger = ZLogger.getLogger(UserHandler.class);

    @Override
    public ListUserResult getUsers(ListUserParams params) {
        ListUserResult result = new ListUserResult();
        try {
            List<User> users = UserListService.getUsers();
            result.setCode(ECode.C_SUCCESS.getValue());
            result.setData(users);
        } catch (Exception ex) {
            _Logger.error(null, ex);
            result.setCode(ECode.C_FAIL.getValue());
            result.setData(null);
        }
        return result;
    }

    @Override
    public DetailUserResult getUser(DetailUserParams params) {
        DetailUserResult result = new DetailUserResult();
        if (params.id == 0) {
            result.setCode(ECode.C_FAIL.getValue());
            result.setData(null);
        } else {

            try {
                User user = UserServices.getUser(params.id);
                result.setCode(ECode.C_SUCCESS.getValue());
                result.setData(user);
            } catch (Exception ex) {
                _Logger.error(null, ex);
                result.setCode(ECode.C_FAIL.getValue());
                result.setData(null);
            }

        }
        return result;
    }

    @Override
    public CreateUserResult createUser(CreateUserParams params) throws TException {
        CreateUserResult result = new CreateUserResult();
        System.out.println(params.user);
        if (params.user.password == null || params.user.name == null || params.user.username == null || params.user.getFieldValue(User._Fields.GENDER) == null || params.user.getFieldValue(User._Fields.BIRTHDAY) == null) {
            result.setCode(ECode.C_FAIL.getValue());
            result.setMessage("Missing new user infomation");
        } else {
            try {
                int effectedRow = UserServices.createUser(params);
                if (effectedRow == 1) {
                    result.setCode(ECode.C_SUCCESS.getValue());
                    result.setMessage("Create user successfully");
                } else {
                    result.setCode(ECode.C_FAIL.getValue());
                    result.setMessage("Fail to create user");
                }
            } catch (Exception ex) {
                _Logger.error(null, ex);
                result.setCode(ECode.C_FAIL.getValue());
                result.setMessage("Error occur");
            }
        }
        return result;
    }

    @Override
    public UpdateUserResult updateUser(UpdateUserParams params) throws TException {
        UpdateUserResult result = new UpdateUserResult();
        if (params.user.name == null || params.user.username == null || params.user.gender == null || params.user.getFieldValue(User._Fields.BIRTHDAY) == null || params.user.getFieldValue(User._Fields.ID) == null) {
            result.setCode(ECode.C_FAIL.getValue());
            result.setMessage("Missing update infomation");
        } else {
            try {
                int effectedRow = UserServices.updateUser(params);
                if (effectedRow == 1) {
                    result.setCode(ECode.C_SUCCESS.getValue());
                    result.setMessage("Update user successfully");
                } else {
                    result.setCode(ECode.C_FAIL.getValue());
                    result.setMessage("Fail to update user");
                }
            } catch (Exception ex) {
                _Logger.error(null, ex);
                result.setCode(ECode.C_FAIL.getValue());
                result.setMessage("Error occur");
            }
        }
        return result;
    }

    @Override
    public DeleteUserResult deleteUser(DeleteUserParams params) throws TException {
        DeleteUserResult result = new DeleteUserResult();
        if (params.id == 0) {
            result.setCode(ECode.C_FAIL.getValue());
            result.setMessage("Missing user ID");
        } else {
            try {
                int effectedRow = UserServices.deleteUser(params);
                if (effectedRow == 1) {
                    result.setCode(ECode.C_SUCCESS.getValue());
                    result.setMessage("Delete user successfully");
                } else {
                    result.setCode(ECode.C_FAIL.getValue());
                    result.setMessage("Fail to delete user");
                }
            } catch (Exception ex) {
                _Logger.error(null, ex);
                result.setCode(ECode.C_FAIL.getValue());
                result.setMessage("Error occur");
            }
        }
        return result;
    }

}
