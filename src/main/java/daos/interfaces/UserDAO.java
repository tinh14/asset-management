/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.UserModel;

/**
 *
 * @author tinhlam
 */
public interface UserDAO {
    public List<UserModel> findAll();
    public List<UserModel> findByPersonId(int personId);
    public void create(Connection connection, UserModel user) throws SQLException;
    public void update(Connection connection, UserModel user) throws SQLException;
}
