/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import daos.interfaces.SupplierDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.SupplierModel;
import services.interfaces.SupplierService;

/**
 *
 * @author tinhlam
 */
public class SupplierServiceImpl implements SupplierService {

    @Inject
    private SupplierDAO supplierDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<SupplierModel> findAll() {
        return supplierDAO.findAll();
    }

    @Override
    public List<SupplierModel> findById(int id) {
        return supplierDAO.findById(id);
    }

    @Override
    public List<SupplierModel> findByName(String name) {
        return supplierDAO.findByName(name);
    }

    @Override
    public ResponseMessage create(SupplierModel supplier) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = supplierDAO.create(transactionManager.getConnection(), supplier);
            supplier.setId(id);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();

        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(SupplierModel supplier) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            supplierDAO.update(transactionManager.getConnection(), supplier);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();

        }
        return new ResponseMessage(status, message);
    }

}
