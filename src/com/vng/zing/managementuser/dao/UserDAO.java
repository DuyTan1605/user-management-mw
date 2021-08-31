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
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author tanhd
 */
public class UserDAO {

    public static final ConnectionManager connectionManager = new ConnectionManager();
    private static final Logger _Logger = ZLogger.getLogger(UserDAO.class);

    public static List<User> getUsers() {
        Connection connection = connectionManager.createConnection();
        try {
            String sqlQuery = "SELECT * FROM user";
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            ResultSet rs = psm.executeQuery();
            List<User> listUser = new ArrayList<User>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Gender gender = Gender.findByValue(rs.getInt("gender"));

                long birthday = 0, createTime = 0, updateTime = 0;

                if (rs.getString("birthday") != null) {

                    birthday = DateTimeUtils.formatDateTime(rs.getString("birthday"));
                }
                if (rs.getString("createtime") != null) {
                    createTime = DateTimeUtils.formatDateTime(rs.getString("createtime"));
                }
                if (rs.getString("updatetime") != null) {
                    updateTime = DateTimeUtils.formatDateTime(rs.getString("updatetime"));
                }

                User user = new User(id, name, username, password, gender, birthday != 0 ? birthday : 0, createTime != 0 ? createTime : 0, updateTime != 0 ? updateTime : 0);
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
            String sqlQuery = "SELECT * FROM user WHERE id=?";
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();
            User user = null;
            while (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Gender gender = Gender.findByValue(rs.getInt("gender"));

                long birthday = 0, createTime = 0, updateTime = 0;

                if (rs.getString("birthday") != null) {

                    birthday = DateTimeUtils.formatDateTime(rs.getString("birthday"));
                }
                if (rs.getString("createtime") != null) {
                    createTime = DateTimeUtils.formatDateTime(rs.getString("createtime"));
                }
                if (rs.getString("updatetime") != null) {
                    updateTime = DateTimeUtils.formatDateTime(rs.getString("updatetime"));
                }

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
            String sqlQuery = "INSERT INTO user (name,username,gender,birthday,password) VALUES (?,?,?,?,?)";
            PreparedStatement psm = connection.prepareStatement(sqlQuery);
            psm.setString(1, params.user.name);
            psm.setString(2, params.user.username);
            psm.setInt(3, params.user.gender.getValue());
            psm.setDate(4, DateTimeUtils.convertUtilToSql(params.user.birthday));
            try {
                psm.setString(5, PasswordHasher.hashPassword(params.user.password));
            } catch (NoSuchAlgorithmException ex) {
                _Logger.error(null, ex);
            }
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

    public static int updateUser(UpdateUserParams params) {
        Connection connection = connectionManager.createConnection();
        try {
            String sqlQuery = "UPDATE user SET name=?,username=?,gender=?,birthday=? where id=?";
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
            String sqlQuery = "DELETE FROM user WHERE id=?";

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
