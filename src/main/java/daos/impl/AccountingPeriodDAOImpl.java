/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AccountingPeriodDAO;
import daos.interfaces.GenericDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.AccountingPeriodMapper;
import models.AccountingPeriodModel;

/**
 *
 * @author tinhlam
 */
public class AccountingPeriodDAOImpl implements AccountingPeriodDAO{

    @Inject
    private GenericDAO genericDAO;
    
    @Override
    public List<AccountingPeriodModel> findAll() {
        String sql = "select * from AccountingPeriod";
        return genericDAO.executeQuery(sql, new AccountingPeriodMapper());
    }

    @Override
    public List<AccountingPeriodModel> findById(int id) {
        String sql = "select * from AccountingPeriod where id = ?";
        return genericDAO.executeQuery(sql, new AccountingPeriodMapper(), id);
    }

    @Override
    public List<AccountingPeriodModel> findByName(String name) {
        String sql = "select * from AccountingPeriod where name like ?";
        return genericDAO.executeQuery(sql, new AccountingPeriodMapper(), "%"+name+"%");
    }

    @Override
    public int create(Connection connection, AccountingPeriodModel model) throws SQLException {
        String sql = "insert into AccountingPeriod values (?, ?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, null, model.getName(), model.getStartDate(), model.getEndDate(), model.getAccountingPeriodType().getId());
    }
    
}
