/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import daos.interfaces.AssetDAO;
import daos.interfaces.InventoryTransactionDAO;
import daos.interfaces.InventoryTransactionDetailDAO;
import daos.interfaces.InvoiceDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.InventoryTransactionDetailModel;
import models.InventoryTransactionModel;
import services.interfaces.InventoryTransactionService;
import services.interfaces.UserService;

/**
 *
 * @author tinhlam
 */
@Default
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    @Inject
    private InventoryTransactionDAO inventoryTransactionDAO;

    @Inject
    private InventoryTransactionDetailDAO inventoryTransactionDetailDAO;

    @Inject
    private AssetDAO assetDAO;

    @Inject
    private InvoiceDAO invoiceDAO;
    
    @Inject
    private UserService userService;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<InventoryTransactionModel> findAll() {
        List<InventoryTransactionModel> inventoryTransactions = inventoryTransactionDAO.findAll();
        for (InventoryTransactionModel inventoryTransaction : inventoryTransactions) {
            inventoryTransaction.setWarehouseReceiver(userService.findById(inventoryTransaction.getWarehouseReceiver().getId()).get(0));
        }
        return inventoryTransactions;
    }

    @Override
    public List<InventoryTransactionModel> findById(int id) {
        List<InventoryTransactionModel> inventoryTransactions = inventoryTransactionDAO.findById(id);
        for (InventoryTransactionModel inventoryTransaction : inventoryTransactions) {
            inventoryTransaction.setWarehouseReceiver(userService.findById(inventoryTransaction.getWarehouseReceiver().getId()).get(0));
            List<InventoryTransactionDetailModel> list = inventoryTransactionDetailDAO.findByInventoryTransactionId(inventoryTransaction.getId());
            inventoryTransaction.setInventoryTransactionDetailList(list);
            for (InventoryTransactionDetailModel itdm : list) {
                itdm.setAsset(assetDAO.findById(itdm.getAsset().getId()).get(0));
            }
        }
        return inventoryTransactions;
    }

    @Override
    public List<InventoryTransactionModel> findByInvoiceIdWithWildcard(String id) {
        List<InventoryTransactionModel> inventoryTransactions = inventoryTransactionDAO.findByInvoiceIdWithWildcard(id);
        for (InventoryTransactionModel inventoryTransaction : inventoryTransactions) {
            inventoryTransaction.setWarehouseReceiver(userService.findById(inventoryTransaction.getWarehouseReceiver().getId()).get(0));
        }
        return inventoryTransactions;
    }
    
    private ResponseMessage checkForeignKey(String invoiceId) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (invoiceId != null && invoiceDAO.findById(invoiceId).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Hóa đơn không tồn tại");
        }
        return response;
    }

    @Override
    public ResponseMessage create(InventoryTransactionModel inventoryTransaction) {
        ResponseMessage response = checkForeignKey(inventoryTransaction.getInvoice().getId());

        if (response.isError()) {
            return response;
        }
        
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = inventoryTransactionDAO.create(transactionManager.getConnection(), inventoryTransaction);
            inventoryTransaction.setId(id);
            for (InventoryTransactionDetailModel detail : inventoryTransaction.getInventoryTransactionDetailList()) {
                detail.getInventoryTransaction().setId(id);
                inventoryTransactionDetailDAO.create(transactionManager.getConnection(), detail);
                detail.setAsset(assetDAO.findById(detail.getAsset().getId()).get(0));
            }
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(InventoryTransactionModel inventoryTransaction) {
        ResponseMessage response = checkForeignKey(inventoryTransaction.getInvoice().getId());

        if (response.isError()) {
            return response;
        }
        
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            inventoryTransactionDAO.update(transactionManager.getConnection(), inventoryTransaction);
            inventoryTransactionDetailDAO.deleteByInventoryTransactionId(transactionManager.getConnection(), inventoryTransaction.getId());
            for (InventoryTransactionDetailModel detail : inventoryTransaction.getInventoryTransactionDetailList()) {
                inventoryTransactionDetailDAO.create(transactionManager.getConnection(), detail);
                detail.setAsset(assetDAO.findById(detail.getAsset().getId()).get(0));
            }
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

}
