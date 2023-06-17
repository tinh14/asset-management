/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.DepartmentModel;

/**
 *
 * @author tinhlam
 */
public class DepartmentMapper implements RowMapper<DepartmentModel> {

    @Override
    public DepartmentModel mapRow(ResultSet res) {
        DepartmentModel model = null;
        try {
            model = new DepartmentModel();
            model.setId(res.getInt("id"));
            model.setName(res.getString("name"));
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

}
