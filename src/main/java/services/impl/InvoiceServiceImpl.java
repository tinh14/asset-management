/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import daos.interfaces.InvoiceDAO;
import daos.interfaces.SupplierDAO;
import daos.interfaces.TransactionManager;
import java.sql.Connection;
import java.sql.SQLException;
import services.interfaces.*;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.InvoiceModel;

/**
 *
 * @author tinhlam
 */
public class InvoiceServiceImpl implements InvoiceService {

    @Inject
    private InvoiceDAO invoiceDAO;

    @Inject
    private SupplierDAO supplierDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<InvoiceModel> findAll() {
        List<InvoiceModel> invoiceList = invoiceDAO.findAll();

        for (InvoiceModel invoice : invoiceList) {
            invoice.setSupplier(supplierDAO.findById(invoice.getSupplier().getId()).get(0));
        }

        return invoiceList;
    }

    @Override
    public List<InvoiceModel> findById(String id) {
        List<InvoiceModel> invoiceList = invoiceDAO.findById(id);
        for (InvoiceModel invoice : invoiceList) {
            invoice.setSupplier(supplierDAO.findById(invoice.getSupplier().getId()).get(0));
        }
        return invoiceList;
    }

    @Override
    public List<InvoiceModel> findByIdWithWildcard(String id) {
        List<InvoiceModel> invoiceList = invoiceDAO.findByIdWithWildcard(id);
        for (InvoiceModel invoice : invoiceList) {
            invoice.setSupplier(supplierDAO.findById(invoice.getSupplier().getId()).get(0));
        }
        return invoiceList;
    }

    private ResponseMessage checkPrimaryKey(String id) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (!invoiceDAO.findById(id).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Mã hóa đơn đã tồn tại\n");
        }
        return response;
    }

    private ResponseMessage checkForeignKey(int id) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (supplierDAO.findById(id).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Nhà cung cấp không tồn tại\n");
        }
        return response;
    }

    @Override
    public ResponseMessage create(InvoiceModel invoice) {
        ResponseMessage response = checkForeignKey(invoice.getSupplier().getId());
        ResponseMessage response2 = checkPrimaryKey(invoice.getId());
        if (response.isError()) {
            response.appendMessage(response2.getMessage());
            return response;
        }

        if (response2.isError()) {
            return response2;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            invoiceDAO.create(transactionManager.getConnection(), invoice);
            invoice.setSupplier(supplierDAO.findById(invoice.getSupplier().getId()).get(0));
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();

        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(InvoiceModel invoice) {
        ResponseMessage response = checkForeignKey(invoice.getSupplier().getId());

        if (response.isError()) {
            return response;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            invoiceDAO.update(transactionManager.getConnection(), invoice);
            invoice.setSupplier(supplierDAO.findById(invoice.getSupplier().getId()).get(0));
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

}
