/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.GenericDAO;
import daos.interfaces.LogDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.LogMapper;
import models.LogModel;

/**
 *
 * @author tinhlam
 */
public class LogDAOImpl implements LogDAO{

    @Inject
    private GenericDAO genericDAO;
    
    @Override
    public List<LogModel> findAll() {
        String sql = "select * from Log order by timestamp desc";
        return genericDAO.executeQuery(sql, new LogMapper());
    }

    @Override
    public List<LogModel> findById(int id) {
        String sql = "select * from Log where id = ?";
        return genericDAO.executeQuery(sql, new LogMapper(), id);
    }

    @Override
    public int create(Connection connection, LogModel log) throws SQLException {
        String sql = "insert into log values (?, ?, ?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, null, log.getTimestamp(), log.getAction().getId(), log.getAction().getResource().getId(), log.getRecordId(), log.getUser().getId());
    }

    @Override
    public List<LogModel> findByRecordId(String id) {
        String sql = "select * from Log where recordId = ? order by timestamp desc";
        return genericDAO.executeQuery(sql, new LogMapper(), id);
    }
    
}
