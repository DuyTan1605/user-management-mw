/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.handlers;

import com.google.inject.Inject;
import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.dmp.common.exception.ZNotExistException;
import com.vng.zing.dmp.common.exception.ZRemoteFailureException;
import com.vng.zing.dmp.common.interceptor.ThreadProfiler;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.managementuser.services.UserServices;
import com.vng.zing.userservice.thrift.CreateUserParams;
import com.vng.zing.userservice.thrift.CreateUserResult;
import com.vng.zing.userservice.thrift.DeleteUserParams;
import com.vng.zing.userservice.thrift.DeleteUserResult;
import com.vng.zing.userservice.thrift.DetailUserParams;
import com.vng.zing.userservice.thrift.DetailUserResult;
import com.vng.zing.userservice.thrift.ListUserParams;
import com.vng.zing.userservice.thrift.ListUserResult;
import com.vng.zing.userservice.thrift.UpdateUserParams;
import com.vng.zing.userservice.thrift.UpdateUserResult;
import com.vng.zing.userservice.thrift.User;
import com.vng.zing.userservice.thrift.UserService;
import com.vng.zing.zcommon.thrift.ECode;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.log4j.Logger;

/**
 *
 * @author tanhd
 */
public class UserHandler implements UserService.Iface {

    private static final Logger logger = ZLogger.getLogger(UserHandler.class);

    private UserListService userListService;
    private UserServices userServices;

    @Inject
    public UserHandler(UserListService userListService, UserServices userServices) {
        this.userListService = userListService;
        this.userServices = userServices;
    }

    private int handleErrorCode(Exception ex) {
        int errorCode = -ECode.EXCEPTION.getValue();
        if (ex instanceof ZInvalidParamException) {
            errorCode = -ECode.INVALID_PARAM.getValue();
        }
        if (ex instanceof ZRemoteFailureException) {
            errorCode = -ECode.C_FAIL.getValue();
        }
        if (ex instanceof ZNotExistException) {
            errorCode = -ECode.NOT_EXIST.getValue();
        }
        return errorCode;
    }

    @ThreadProfiler
    @Override
    public ListUserResult getUsers(ListUserParams params) {
        ListUserResult result = new ListUserResult();
        try {
            List<User> users = userListService.getUsers();
            result.setCode(ECode.C_SUCCESS.getValue());
            result.setData(users);
        } catch (Exception ex) {
            logger.error(ex);
            result.setCode(handleErrorCode(ex));
        }
        return result;
    }

    @ThreadProfiler
    @Override
    public DetailUserResult getUser(DetailUserParams params) {
        DetailUserResult result = new DetailUserResult();
        try {
            User user = userServices.getUser(params.id);
            result.setCode(ECode.C_SUCCESS.getValue());
            result.setData(user);
        } catch (Exception ex) {
            logger.error(ex);
            result.setCode(handleErrorCode(ex));
        }
        return result;
    }

    @ThreadProfiler
    @Override
    public CreateUserResult createUser(CreateUserParams params) throws TException {
        CreateUserResult result = new CreateUserResult();
        try {
            userServices.createUser(params);
            result.setCode(ECode.C_SUCCESS.getValue());
            result.setMessage("Create user successfully");
        } catch (Exception ex) {
            logger.error(ex);
            result.setCode(handleErrorCode(ex));
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @ThreadProfiler
    @Override
    public UpdateUserResult updateUser(UpdateUserParams params) throws TException {
        UpdateUserResult result = new UpdateUserResult();
        try {
            userServices.updateUser(params);
            result.setCode(ECode.C_SUCCESS.getValue());
            result.setMessage("Update user successfully");
        } catch (Exception ex) {
            logger.error(ex);
            result.setCode(handleErrorCode(ex));
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @ThreadProfiler
    @Override
    public DeleteUserResult deleteUser(DeleteUserParams params) throws TException {
        DeleteUserResult result = new DeleteUserResult();
        try {
            userServices.deleteUser(params);
            result.setCode(ECode.C_SUCCESS.getValue());
            result.setMessage("Delete user successfully");
        } catch (Exception ex) {
            logger.error(ex);
            result.setCode(handleErrorCode(ex));
            result.setMessage(ex.getMessage());
        }
        return result;
    }

}
