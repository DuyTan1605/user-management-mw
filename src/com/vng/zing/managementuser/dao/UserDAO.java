/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.dao;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.utils.DateTimeUtils;
import com.vng.zing.managementuser.utils.PasswordHasher;
import com.vng.zing.userservice.thrift.CreateUserParams;
import com.vng.zing.userservice.thrift.DeleteUserParams;
import com.vng.zing.userservice.thrift.Gender;
import com.vng.zing.userservice.thrift.UpdateUserParams;
import com.vng.zing.userservice.thrift.User;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author tanhd
 */
public class UserDAO {

    public static final ConnectionManager connectionManager = new ConnectionManager();
    private static final Logger _Logger = ZLogger.getLogger(UserDAO.class);

    private static final String USER_TABLE = "user";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String USER_NAME = "username";
    private static final String GENDER = "gender";
    private static final String PASSWORD = "password";
    private static final String BIRTHDAY = "birthday";
    private static final String CREATE_TIME = "createtime";
    private static final String UPDATE_TIME = "updatetime";

    public List<User> getUsers() {
        Connection connection = connectionManager.createConnection();
        try {
            String sqlQuery = "SELECT * FROM " + USER_TABLE;
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            ResultSet rs = psm.executeQuery();
            List<User> listUser = new ArrayList<User>();
            while (rs.next()) {
                int id = rs.getInt(ID);
                String name = rs.getString(NAME);
                String username = rs.getString(USER_NAME);
                String password = rs.getString(PASSWORD);
                Gender gender = Gender.findByValue(rs.getInt(GENDER));
                long createTime = DateTimeUtils.formatDateTime(rs.getString(CREATE_TIME));
                long updateTime = DateTimeUtils.formatDateTime(rs.getString(UPDATE_TIME));
                long birthday = DateTimeUtils.formatDateTime(rs.getString(BIRTHDAY));

                User user = new User(id, name, username, password, gender, birthday, createTime, updateTime);
                listUser.add(user);
            }
            return listUser;
        } catch (SQLException ex) {
            _Logger.error(null, ex);
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                _Logger.error(null, ex);
            }
        }
    }

    public static User getUser(int id) {
        Connection connection = connectionManager.createConnection();
        try {
            String sqlQuery = "SELECT * FROM " + USER_TABLE + " WHERE id=?";
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            User user = null;
            while (rs.next()) {
                int userId = rs.getInt(ID);
                String name = rs.getString(NAME);
                String username = rs.getString(USER_NAME);
                String password = rs.getString(PASSWORD);
                Gender gender = Gender.findByValue(rs.getInt(GENDER));
                long createTime = DateTimeUtils.formatDateTime(rs.getString(CREATE_TIME));
                long updateTime = DateTimeUtils.formatDateTime(rs.getString(UPDATE_TIME));
                long birthday = DateTimeUtils.formatDateTime(rs.getString(BIRTHDAY));

                user = new User(userId, name, username, password, gender, birthday, createTime, updateTime);
            }
            return user;
        } catch (SQLException ex) {
            _Logger.error(null, ex);
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                _Logger.error(null, ex);
            }
        }
    }

    public static int createUser(CreateUserParams params) {
        Connection connection = connectionManager.createConnection();
        try {
            String sqlQuery = "INSERT INTO " + USER_TABLE + "(" + NAME + "," + USER_NAME + "," + GENDER + "," + BIRTHDAY + "," + PASSWORD + ")" + "VALUES (?,?,?,?,?)";
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setString(1, params.user.name);
            psm.setString(2, params.user.username);
            psm.setInt(3, params.user.gender.getValue());
            psm.setDate(4, DateTimeUtils.convertUtilToSql(params.user.birthday));

            psm.setString(5, PasswordHasher.hashPassword(params.user.password));
            int effectedRow = psm.executeUpdate();
            return effectedRow;
        } catch (SQLException ex) {
            _Logger.error(null, ex);
            return 0;
        } catch (NoSuchAlgorithmException ex) {
            _Logger.error(null, ex);
            return 0;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                _Logger.error(null, ex);
            }
        }
    }

    public static int updateUser(UpdateUserParams params) {
        Connection connection = connectionManager.createConnection();
        try {
            String sqlQuery = "UPDATE " + USER_TABLE + " SET " + NAME + "=?," + USER_NAME + "=?," + GENDER + "=?," + BIRTHDAY + "=? where " + ID + "=?";
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setString(1, params.user.name);
            psm.setString(2, params.user.username);
            psm.setInt(3, params.user.gender.getValue());
            psm.setDate(4, DateTimeUtils.convertUtilToSql(params.user.birthday));
            psm.setInt(5, params.user.id);

            int effectedRow = psm.executeUpdate();
            return effectedRow;
        } catch (SQLException ex) {
            _Logger.error(null, ex);
            return 0;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                _Logger.error(null, ex);
            }
        }
    }

    public static int deleteUser(DeleteUserParams params) {
        Connection connection = connectionManager.createConnection();
        try {
            String sqlQuery = "DELETE FROM " + USER_TABLE + " WHERE " + ID + "=?";

            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setInt(1, params.id);
            int effectedRow = psm.executeUpdate();
            return effectedRow;
        } catch (SQLException ex) {
            _Logger.error(null, ex);
            return 0;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                _Logger.error(null, ex);
            }
        }
    }
}
