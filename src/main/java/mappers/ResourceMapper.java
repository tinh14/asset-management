/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ResourceModel;

/**
 *
 * @author tinhlam
 */
public class ResourceMapper implements RowMapper<ResourceModel> {

    @Override
    public ResourceModel mapRow(ResultSet res) {
        ResourceModel resource = null;
        try {
            resource = new ResourceModel();
            resource.setId(res.getString("id"));
            resource.setName(res.getString("name"));
        } catch (SQLException ex) {
            Logger.getLogger(ResourceMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resource;
    }

}
