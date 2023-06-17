/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.LogDetailModel;
import models.LogModel;

/**
 *
 * @author tinhlam
 */
public class LogDetailMapper implements RowMapper<LogDetailModel> {

    @Override
    public LogDetailModel mapRow(ResultSet res) {
        LogDetailModel logDetail = null;
        try {
            logDetail = new LogDetailModel();
            logDetail.setId(res.getInt("id"));
            logDetail.setFieldName(res.getString("fieldName"));
            logDetail.setOldValue(res.getString("oldValue"));
            logDetail.setNewValue(res.getString("newValue"));
            logDetail.setLog(new LogModel(res.getInt("logId")));
        } catch (SQLException ex) {
            Logger.getLogger(LogDetailMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logDetail;
    }

}
