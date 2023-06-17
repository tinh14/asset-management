/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.LogAssetDetailModel;
import models.LogModel;

/**
 *
 * @author tinhlam
 */
public class LogAssetDetailMapper implements RowMapper<LogAssetDetailModel> {

    @Override
    public LogAssetDetailModel mapRow(ResultSet res) {
        LogAssetDetailModel logAssetDetail = null;
        try {
            logAssetDetail = new LogAssetDetailModel();
            logAssetDetail.setId(res.getInt("id"));
            logAssetDetail.setFieldName(res.getString("fieldName"));
            logAssetDetail.setOldValue(res.getString("oldValue"));
            logAssetDetail.setNewValue(res.getString("newValue"));
            logAssetDetail.setLog(new LogModel(res.getInt("logId")));
        } catch (SQLException ex) {
            Logger.getLogger(LogAssetDetailMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logAssetDetail;
    }

}
