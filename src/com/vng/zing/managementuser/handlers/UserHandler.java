/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.handlers;

import com.mysql.cj.result.LocalDateTimeValueFactory;
import com.vng.zing.common.ZErrorDef;
import com.vng.zing.connection.ConnectionManager;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DateFormatter;
import org.apache.thrift.TException;

/**
 *
 * @author tanhd
 */
public class UserHandler implements UserService.Iface {

    public ConnectionManager connectionManager = new ConnectionManager();

    public static long formatDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateFormat = LocalDateTime.parse(dateTime, formatter);
        return dateFormat.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
    }

    public static java.sql.Date convertUtilToSql(long msSeconds) {
        java.util.Date uDate = new java.util.Date(msSeconds);
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    @Override
    public ListUserResult getUsers(ListUserParams params) throws TException {
        ListUserResult result = new ListUserResult();
        try {
            String sqlQuery = "SELECT * FROM user";
            Connection connection = connectionManager.createConnection();
            List<User> listUsers = new ArrayList<User>();
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            ResultSet rs = psm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Gender gender = Gender.findByValue(rs.getInt("gender"));

                long birthday = 0, createTime = 0, updateTime = 0;

                if (rs.getString("birthday") != null) {

                    birthday = formatDateTime(rs.getString("birthday"));
                }
                if (rs.getString("createtime") != null) {
                    createTime = formatDateTime(rs.getString("createtime"));
                }
                if (rs.getString("updatetime") != null) {
                    updateTime = formatDateTime(rs.getString("updatetime"));
                }

                User user = new User(id, name, username, password, gender, birthday != 0 ? birthday : 0, createTime != 0 ? createTime : 0, updateTime != 0 ? updateTime : 0);
                listUsers.add(user);
                //System.out.println(id + " " + name + " " + username + " " + password + " " + gender + " " + birthday + " " + createTime + " " + updateTime);
            }
            result.setCode(ZErrorDef.SUCCESS);
            result.setData(listUsers);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            result.setCode(ZErrorDef.FAIL);
            result.setData(null);
        }

        return result;
    }

    @Override
    public DetailUserResult getUser(DetailUserParams params) throws TException {
        DetailUserResult result = new DetailUserResult();
        User user = null;
        try {
            String sqlQuery = "SELECT * FROM user WHERE id=?";
            Connection connection = connectionManager.createConnection();
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setInt(1, params.id);
            ResultSet rs = psm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Gender gender = Gender.findByValue(rs.getInt("gender"));

                long birthday = 0, createTime = 0, updateTime = 0;

                if (rs.getString("birthday") != null) {

                    birthday = formatDateTime(rs.getString("birthday"));
                }
                if (rs.getString("createtime") != null) {
                    createTime = formatDateTime(rs.getString("createtime"));
                }
                if (rs.getString("updatetime") != null) {
                    updateTime = formatDateTime(rs.getString("updatetime"));
                }

                user = new User(id, name, username, password, gender, birthday != 0 ? birthday : 0, createTime != 0 ? createTime : 0, updateTime != 0 ? updateTime : 0);
            }
            int code = user != null ? ZErrorDef.SUCCESS : ZErrorDef.BAD_REQUEST;
            result.setCode(code);
            result.setData(user);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            result.setCode(ZErrorDef.FAIL);
            result.setData(null);
        }

        return result;
    }

    @Override
    public CreateUserResult createUser(CreateUserParams params) throws TException {
        CreateUserResult result = new CreateUserResult();
        try {
            String sqlQuery = "INSERT INTO user (name,username,gender,birthday,password) VALUES (?,?,?,?,?)";
            Connection connection = connectionManager.createConnection();
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setString(1, params.user.name);
            psm.setString(2, params.user.username);
            psm.setInt(3, params.user.gender.getValue());
            psm.setDate(4, convertUtilToSql(params.user.birthday));
            psm.setString(5, params.user.password);

            int effectedRow = psm.executeUpdate();
            int code = effectedRow == 1 ? ZErrorDef.SUCCESS : ZErrorDef.FAIL;
            String message = effectedRow == 1 ? "Create user successfully" : "Fail to create user";
            result.setCode(code);
            result.setMessage(message);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            result.setCode(ZErrorDef.FAIL);
            result.setMessage("Error occur");
        }
        return result;
    }

    @Override
    public UpdateUserResult updateUser(UpdateUserParams params) throws TException {
        UpdateUserResult result = new UpdateUserResult();
        try {
            String sqlQuery = "UPDATE user SET name=?,username=?,gender=?,birthday=? where id=?";
            Connection connection = connectionManager.createConnection();
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setString(1, params.user.name);
            psm.setString(2, params.user.username);
            psm.setInt(3, params.user.gender.getValue());
            psm.setDate(4, convertUtilToSql(params.user.birthday));
            psm.setInt(5, params.user.id);

            int effectedRow = psm.executeUpdate();
            int code = effectedRow == 1 ? ZErrorDef.SUCCESS : ZErrorDef.FAIL;
            String message = effectedRow == 1 ? "Update user successfully" : "Fail to update user";
            result.setCode(code);
            result.setMessage(message);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            result.setCode(ZErrorDef.FAIL);
            result.setMessage("Error occur");
        }
        return result;
    }

    @Override
    public DeleteUserResult deleteUser(DeleteUserParams params) throws TException {
        DeleteUserResult result = new DeleteUserResult();
        try {
            String sqlQuery = "DELETE FROM user WHERE id=?";
            Connection connection = connectionManager.createConnection();
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setInt(1, params.id);
            int effectedRow = psm.executeUpdate();
            int code = effectedRow == 1 ? ZErrorDef.SUCCESS : ZErrorDef.FAIL;
            String message = effectedRow == 1 ? "Delete user successfully" : "Fail to delete user";
            result.setCode(code);
            result.setMessage(message);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            result.setCode(ZErrorDef.FAIL);
            result.setMessage("Error occur");
        }
        return result;
    }

}
