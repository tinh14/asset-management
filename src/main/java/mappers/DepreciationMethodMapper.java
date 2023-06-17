/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.DepreciationMethodModel;

/**
 *
 * @author tinhlam
 */
public class DepreciationMethodMapper implements RowMapper<DepreciationMethodModel> {

    @Override
    public DepreciationMethodModel mapRow(ResultSet res) {
        DepreciationMethodModel model = null;
        try {
            model = new DepreciationMethodModel();

            model.setId(res.getString("id"));
            model.setName(res.getString("name"));
            model.setDescription(res.getString("description"));

        } catch (SQLException ex) {
            Logger.getLogger(DepreciationMethodMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

}
