/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ActionModel;
import models.ResourceModel;

/**
 *
 * @author tinhlam
 */
public class ActionMapper implements RowMapper<ActionModel>{
    @Override
    public ActionModel mapRow(ResultSet res) {
        ActionModel action = null;
        try {
            action = new ActionModel();
            action.setId(res.getString("id"));
            action.setName(res.getString("name"));
            action.setResource(new ResourceModel(res.getString("resourceId")));
        } catch (SQLException ex) {
            Logger.getLogger(ResourceMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return action;
    }
}
