/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.handlers;

import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.exception.NotExistException;
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

    public ConnectionManager connectionManager = new ConnectionManager();
    private static final Logger _Logger = ZLogger.getLogger(UserHandler.class);
    private final UserListService userListService = new UserListService();

    @Override
    public ListUserResult getUsers(ListUserParams params) {
        ListUserResult result = new ListUserResult();
        try {
            List<User> users = userListService.getUsers();
            result.setCode(ECode.C_SUCCESS.getValue());
            result.setData(users);
        } catch (Exception ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.C_FAIL.getValue());
            result.setData(null);
        }
        return result;
    }

    @Override
    public DetailUserResult getUser(DetailUserParams params) {
        DetailUserResult result = new DetailUserResult();
        try {
            int id = params.id;
            User user = UserServices.getUser(id);
            result.setCode(ECode.C_SUCCESS.getValue());
            result.setData(user);
        } catch (ZInvalidParamException ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.INVALID_PARAM.getValue());
            result.setData(null);
        } catch (Exception ex) {
            _Logger.error(null, ex);
            result.setCode(ECode.C_FAIL.getValue());
            result.setData(null);
        }
        return result;
    }

    @Override
    public CreateUserResult createUser(CreateUserParams params) throws TException {
        CreateUserResult result = new CreateUserResult();
        try {
            int effectedRow = UserServices.createUser(params);
            if (effectedRow == 1) {
                result.setCode(ECode.C_SUCCESS.getValue());
                result.setMessage("Create user successfully");
            } else {
                result.setCode(-ECode.C_FAIL.getValue());
                result.setMessage("Fail to create user");
            }
        } catch (NotExistException ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.INVALID_PARAM.getValue());
            result.setMessage("Missing credentials");
        } catch (ZInvalidParamException ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.INVALID_PARAM.getValue());
            result.setMessage(ex.getMessage());
        } catch (Exception ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.C_FAIL.getValue());
            result.setMessage("Error occur");
        }
        return result;
    }

    @Override
    public UpdateUserResult updateUser(UpdateUserParams params) throws TException {
        UpdateUserResult result = new UpdateUserResult();
        try {
            int effectedRow = UserServices.updateUser(params);
            if (effectedRow == 1) {
                result.setCode(ECode.C_SUCCESS.getValue());
                result.setMessage("Update user successfully");
            } else {
                result.setCode(-ECode.C_FAIL.getValue());
                result.setMessage("Fail to update user");
            }
        } catch (NotExistException ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.INVALID_PARAM.getValue());
            result.setMessage("Missing credentials");
        } catch (ZInvalidParamException ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.INVALID_PARAM.getValue());
            result.setMessage(ex.getMessage());
        } catch (Exception ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.C_FAIL.getValue());
            result.setMessage("Error occur");
        }
        return result;
    }

    @Override
    public DeleteUserResult deleteUser(DeleteUserParams params) throws TException {
        DeleteUserResult result = new DeleteUserResult();
        try {
            int effectedRow = UserServices.deleteUser(params);
            if (effectedRow == 1) {
                result.setCode(ECode.C_SUCCESS.getValue());
                result.setMessage("Delete user successfully");
            } else {
                result.setCode(-ECode.C_FAIL.getValue());
                result.setMessage("Fail to delete user");
            }
        } catch (NotExistException ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.INVALID_PARAM.getValue());
            result.setMessage("Missing credentials");
        } catch (ZInvalidParamException ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.INVALID_PARAM.getValue());
            result.setMessage(ex.getMessage());
        } catch (Exception ex) {
            _Logger.error(null, ex);
            result.setCode(-ECode.C_FAIL.getValue());
            result.setMessage("Error occur");
        }
        return result;
    }

}
