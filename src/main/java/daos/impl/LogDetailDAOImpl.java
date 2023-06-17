/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.GenericDAO;
import daos.interfaces.LogDetailDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.LogDetailMapper;
import models.LogDetailModel;

/**
 *
 * @author tinhlam
 */
public class LogDetailDAOImpl implements LogDetailDAO {

    @Inject
    private GenericDAO genericDAO;
    
    @Override
    public List<LogDetailModel> findByLogId(int id) {
        String sql = "select * from LogDetail where logId = ?";
        return genericDAO.executeQuery(sql, new LogDetailMapper(), id);
    }

    @Override
    public void create(Connection connection, LogDetailModel logDetail) throws SQLException {
        String sql = "insert into LogDetail values (?, ?, ?, ?, ?)";
        genericDAO.executeUpdate(connection, sql, null, logDetail.getFieldName(), logDetail.getOldValue(), logDetail.getNewValue(), logDetail.getLog().getId());
    }

}
