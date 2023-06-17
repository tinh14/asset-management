/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import constants.Constants;
import daos.interfaces.AccountingPeriodDAO;
import daos.interfaces.AccountingPeriodTypeDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.AccountingPeriodModel;
import services.interfaces.AccountingPeriodService;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
public class AccountingPeriodServiceImpl implements AccountingPeriodService {

    @Inject
    private AccountingPeriodDAO accountingPeriodDAO;

    @Inject
    private AccountingPeriodTypeDAO accountingPeriodTypeDAO;
    
    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<AccountingPeriodModel> findAll() {
        List<AccountingPeriodModel> list = accountingPeriodDAO.findAll();
        for (AccountingPeriodModel model : list){
            model.setAccountingPeriodType(accountingPeriodTypeDAO.findById(model.getAccountingPeriodType().getId()).get(0));
        }
        return list;
    }

    @Override
    public List<AccountingPeriodModel> findById(int id) {
        List<AccountingPeriodModel> list = accountingPeriodDAO.findById(id);
        for (AccountingPeriodModel model : list){
            model.setAccountingPeriodType(accountingPeriodTypeDAO.findById(model.getAccountingPeriodType().getId()).get(0));
        }
        return list;
    }

    @Override
    public List<AccountingPeriodModel> findByName(String name) {
        List<AccountingPeriodModel> list = accountingPeriodDAO.findByName(name);
        for (AccountingPeriodModel model : list){
            model.setAccountingPeriodType(accountingPeriodTypeDAO.findById(model.getAccountingPeriodType().getId()).get(0));
        }
        return list;
    }

    @Override
    public ResponseMessage create(AccountingPeriodModel accountingPeriod) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = accountingPeriodDAO.create(transactionManager.getConnection(), accountingPeriod);
            accountingPeriod.setId(id);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

}
