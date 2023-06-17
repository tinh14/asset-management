/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.GenericDAO;
import daos.interfaces.LogAssetDetailDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.LogAssetDetailMapper;
import models.LogAssetDetailModel;

/**
 *
 * @author tinhlam
 */
public class LogAssetDetailDAOImpl implements LogAssetDetailDAO {

    @Inject
    private GenericDAO genericDAO;
    
    @Override
    public List<LogAssetDetailModel> findByLogId(int id) {
        String sql = "select * from LogAssetDetail where logId = ?";
        return genericDAO.executeQuery(sql, new LogAssetDetailMapper(), id);
    }

    @Override
    public void create(Connection connection, LogAssetDetailModel logAssetDetail) throws SQLException {
        String sql = "insert into LogAssetDetail values (?, ?, ?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, null, logAssetDetail.getFieldName(), logAssetDetail.getOldValue(), logAssetDetail.getNewValue(), logAssetDetail.getLog().getId());
    }

}
