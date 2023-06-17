/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AccountingPeriodTypeDAO;
import daos.interfaces.GenericDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.AccountingPeriodTypeMapper;
import models.AccountingPeriodTypeModel;

/**
 *
 * @author tinhlam
 */
public class AccountingPeriodTypeDAOImpl implements AccountingPeriodTypeDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<AccountingPeriodTypeModel> findAll() {
        String sql = "select * from AccountingPeriodType";
        return genericDAO.executeQuery(sql, new AccountingPeriodTypeMapper());
    }

    @Override
    public List<AccountingPeriodTypeModel> findById(String id) {
        String sql = "select * from AccountingPeriodType where id = ?";
        return genericDAO.executeQuery(sql, new AccountingPeriodTypeMapper(), id);
    }

    @Override
    public List<AccountingPeriodTypeModel> findByName(String name) {
        String sql = "select * from AccountingPeriodType where name like ?";
        return genericDAO.executeQuery(sql, new AccountingPeriodTypeMapper(), "%" + name + "%");
    }

}
