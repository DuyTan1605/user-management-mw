/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.dao;

import com.vng.zing.dmp.common.exception.ZNotExistException;
import com.vng.zing.dmp.common.exception.ZRemoteFailureException;
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
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author tanhd
 */
public class UserDAO {

    private static final Logger logger = ZLogger.getLogger(UserDAO.class);

    private final String USER_TABLE = "user";
    private final String ID = "id";
    private final String NAME = "name";
    private final String USER_NAME = "username";
    private final String GENDER = "gender";
    private final String PASSWORD = "password";
    private final String BIRTHDAY = "birthday";
    private final String CREATE_TIME = "createtime";
    private final String UPDATE_TIME = "updatetime";

    private ConnectionManager connectionManager = new ConnectionManager();

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
            logger.error(ex);
            return Collections.EMPTY_LIST;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
    }

    public User getUser(int id) {
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
            if (user == null) {
                throw new ZNotExistException("User with ID=" + id + " not existed");
            }
            return user;
        } catch (SQLException ex) {
            logger.error(ex);
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
    }

    public void createUser(CreateUserParams params) throws NoSuchAlgorithmException, SQLException {
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
            if (effectedRow == 0) {
                throw new ZRemoteFailureException("Can not create new user " + params.user);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
    }

    public void updateUser(UpdateUserParams params) throws SQLException {
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
            if (effectedRow == 0) {
                throw new ZRemoteFailureException("Can not update user " + params.user);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
    }

    public void deleteUser(DeleteUserParams params) throws SQLException {
        Connection connection = connectionManager.createConnection();
        try {
            String sqlQuery = "DELETE FROM " + USER_TABLE + " WHERE " + ID + "=?";

            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setInt(1, params.id);
            int effectedRow = psm.executeUpdate();
            if (effectedRow == 0) {
                throw new ZRemoteFailureException("Can delete user with ID=" + params.id);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
    }
}
