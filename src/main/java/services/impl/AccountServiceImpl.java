/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import constants.Constants;
import daos.interfaces.AccountDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import javax.inject.Inject;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import models.AccountModel;
import services.interfaces.AccountService;
import utilz.ResponseMessage;

/**
 *
 * @author tinhlam
 */
public class AccountServiceImpl implements AccountService {

    @Inject
    private AccountDAO accountDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<AccountModel> findAll() {
        return accountDAO.findAll();
    }

    @Override
    public List<AccountModel> findByUsername(String username) {
        return accountDAO.findByUsername(username);
    }

    @Override
    public List<AccountModel> findByUsernameWithWildcard(String username) {
        return accountDAO.findByUsernameWithWildcard(username);
    }

    @Override
    public List<AccountModel> findByUsernameAndPassword(String username, String password) {
        return accountDAO.findByUsernameAndPassword(username, password);
    }

    private ResponseMessage checkPrimaryKey(String username) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (!accountDAO.findByUsername(username).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Tên tài khoản đã được sử dụng\n");
        }
        return response;
    }

    @Override
    public ResponseMessage create(AccountModel account) {
        ResponseMessage response = checkPrimaryKey(account.getUsername());
        if (response.isError()) {
            return response;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            accountDAO.create(transactionManager.getConnection(), account);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(AccountModel account) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            accountDAO.update(transactionManager.getConnection(), account);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

}
