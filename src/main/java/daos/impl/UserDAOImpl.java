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
public class UserDAOImpl implements UserDAO{

    @Inject
    GenericDAO genericDAO;
    
    @Override
    public List<UserModel> findAll(){
        String sql = "select * from User";
        return genericDAO.executeQuery(sql, new UserMapper());
    }
    
    @Override
    public List<UserModel> findByPersonId(int id) {
        String sql = "select * from User where personId = ?";
        return genericDAO.executeQuery(sql, new UserMapper(), id);
    }

    @Override
    public void create(Connection connection, UserModel user) throws SQLException{
        String sql = "insert into User values (?)";
         genericDAO.executeUpdate(connection, sql, user.getId());
    }

    @Override
    public void update(Connection connection, UserModel user) throws SQLException{
        String sql = "update User where personId = ?";
        genericDAO.executeUpdate(connection, sql, user.getId());
    }
    
}
