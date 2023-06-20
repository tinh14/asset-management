/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.UserDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.UserMapper;
import models.UserModel;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author tinhlam
 */
public class UserDAOImpl implements UserDAO {

    @Inject
    GenericDAO genericDAO;

    @Override
    public List<UserModel> findAll() {
        String sql = "select * from User";
        return genericDAO.executeQuery(sql, new UserMapper());
    }

    @Override
    public List<UserModel> findById(int id) {
        String sql = "select * from User where id = ?";
        return genericDAO.executeQuery(sql, new UserMapper(), id);
    }
    
    @Override
    public List<UserModel> findByName(String name){
        String sql = "select * from User where lastName like ? or firstName like ?";
        return genericDAO.executeQuery(sql, new UserMapper(), "%" + name + "%", "%" + name + "%");
    }
    
    @Override
    public List<UserModel> findByAccountUsername(String accountUsername){
        String sql = "select * from user where accountUsername = ?";
        return genericDAO.executeQuery(sql, new UserMapper(), accountUsername);
    }

    @Override
    public int create(Connection connection, UserModel user) throws SQLException {
        String sql = "insert into User values (?, ?, ?, ?, ?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, user.getId(),
                user.getLastName(), user.getFirstName(), user.getDateOfBirth(), user.getAddress(),
                user.getAvatar(), user.getDepartment().getId(), user.getAccount().getUsername());
    }

    @Override
    public void update(Connection connection, UserModel user) throws SQLException {
        String sql = "update User set lastName = ?, firstName = ?, dateOfBirth = ?, address = ?, avatar = ?, departmentId = ?, accountUsername = ? where id = ?";
        genericDAO.executeUpdate(connection, sql,
                user.getLastName(), user.getFirstName(), user.getDateOfBirth(), user.getAddress(),
                user.getAvatar(), user.getDepartment().getId(), user.getAccount().getUsername(), user.getId());
    }
}
