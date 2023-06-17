/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import services.interfaces.AuthorizationService;
import javax.enterprise.inject.Default;
import java.util.List;
import javax.inject.Inject;
import models.AuthorizationModel;
import daos.interfaces.AuthorizationDAO;
import daos.interfaces.TransactionManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tinhlam
 */
@Default
public class AuthorizationServiceImpl implements AuthorizationService {

    @Inject
    private AuthorizationDAO authorizationDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public boolean checkPermission(String accountUsername, String resourceId, String actionId) {
        return authorizationDAO.checkPermission(accountUsername, resourceId, actionId);
    }

    @Override
    public List<AuthorizationModel> getPermission(String accountUsername) {
        return authorizationDAO.getPermission(accountUsername);
    }

    @Override
    public ResponseMessage update(List<AuthorizationModel> list) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();

            if (list.size() == 1 && list.get(0).getAction().getId() == null) {
                authorizationDAO.revokeAllPermissionByUsername(transactionManager.getConnection(), list.get(0).getAccount().getUsername());
                transactionManager.commitAndCloseConnection();
                return new ResponseMessage(status, message);
            }

            authorizationDAO.revokeAllPermissionByUsername(transactionManager.getConnection(), list.get(0).getAccount().getUsername());
            for (AuthorizationModel authorization : list) {
                authorizationDAO.grantPermission(transactionManager.getConnection(), authorization);
            }
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            message = ex.getMessage();
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

}
