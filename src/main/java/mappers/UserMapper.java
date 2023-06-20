/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AccountModel;
import models.DepartmentModel;
import models.UserModel;

/**
 *
 * @author tinhlam
 */
public class UserMapper implements RowMapper<UserModel> {
    
    @Override
    public UserModel mapRow(ResultSet res) {
        UserModel model = null;
        try {
            model = new UserModel();
            model.setId(res.getInt("id"));
            model.setLastName(res.getString("lastName"));
            model.setFirstName(res.getString("firstName"));
            model.setAddress(res.getString("address"));
            model.setDateOfBirth(res.getDate("dateOfBirth"));
            model.setAvatar(res.getString("avatar"));
            model.setDepartment(new DepartmentModel(res.getInt("departmentId")));
            model.setAccount(new AccountModel(res.getString("accountUsername")));
        } catch (SQLException ex) {
            Logger.getLogger(UserMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

}
