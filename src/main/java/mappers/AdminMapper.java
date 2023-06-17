/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AdminModel;

/**
 *
 * @author tinhlam
 */
public class AdminMapper implements RowMapper<AdminModel> {

    @Override
    public AdminModel mapRow(ResultSet res) {
        AdminModel admin = null;
        try {
            admin = new AdminModel(res.getInt("personId"));
            return admin;
        } catch (SQLException ex) {
            Logger.getLogger(AdminMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return admin;
    }

}
