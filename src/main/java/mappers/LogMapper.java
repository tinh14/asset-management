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
import models.LogModel;
import models.ResourceModel;
import models.UserModel;

/**
 *
 * @author tinhlam
 */
public class LogMapper implements RowMapper<LogModel> {

    @Override
    public LogModel mapRow(ResultSet res) {
        LogModel log = null;
        try {
            log = new LogModel();
            log.setId(res.getInt("id"));
            log.setTimestamp(res.getTimestamp("timestamp"));
            log.setRecordId(res.getString("recordId"));
            ActionModel action = new ActionModel();
            action.setId(res.getString("actionId"));
            action.setResource(new ResourceModel(res.getString("resourceId")));
            log.setAction(action);
            log.setUser(new UserModel(res.getInt("userId")));
        } catch (SQLException ex) {
            Logger.getLogger(LogMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }

}
