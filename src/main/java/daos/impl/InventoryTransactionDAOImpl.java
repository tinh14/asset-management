/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.GenericDAO;
import daos.interfaces.InventoryTransactionDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import mappers.InventoryTransactionMapper;
import models.InventoryTransactionModel;

/**
 *
 * @author tinhlam
 */
public class InventoryTransactionDAOImpl implements InventoryTransactionDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<InventoryTransactionModel> findAll() {
        String sql = "select * from InventoryTransaction";
        return genericDAO.executeQuery(sql, new InventoryTransactionMapper());
    }

    @Override
    public List<InventoryTransactionModel> findById(int id) {
        String sql = "select * from InventoryTransaction where id = ?";
        return genericDAO.executeQuery(sql, new InventoryTransactionMapper(), id);
    }
    
    @Override
    public List<InventoryTransactionModel> findByInvoiceIdWithWildcard(String id) {
        String sql = "select * from InventoryTransaction where invoiceId like ?";
        return genericDAO.executeQuery(sql, new InventoryTransactionMapper(), "%"+id+"%");
    }

    @Override
    public int create(Connection connection, InventoryTransactionModel inventoryTransaction) throws SQLException {
        String sql = "insert into InventoryTransaction "
                + "values (?, ?, ?, ?)";
        return genericDAO.executeScalar(connection, sql, null, inventoryTransaction.getReceiptDate(), inventoryTransaction.getWarehouseReceiver().getId(), inventoryTransaction.getInvoice().getId());
    }

    @Override
    public void update(Connection connection, InventoryTransactionModel inventoryTransaction) throws SQLException {
        String sql = "update InventoryTransaction set receiptDate = ?, warehouseReceiverId = ?, invoiceId = ? where id = ?";
        genericDAO.executeUpdate(connection, sql, inventoryTransaction.getReceiptDate(), inventoryTransaction.getWarehouseReceiver().getId(), inventoryTransaction.getInvoice().getId(), inventoryTransaction.getId());
    }

}
