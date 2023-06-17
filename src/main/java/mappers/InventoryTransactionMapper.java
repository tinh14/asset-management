/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.InventoryTransactionModel;
import models.InvoiceModel;
import models.UserModel;

/**
 *
 * @author tinhlam
 */
public class InventoryTransactionMapper implements RowMapper<InventoryTransactionModel> {

    @Override
    public InventoryTransactionModel mapRow(ResultSet res) {
        InventoryTransactionModel inventoryTransaction = null;
        try {
            inventoryTransaction = new InventoryTransactionModel();
            inventoryTransaction.setId(res.getInt("id"));
            inventoryTransaction.setReceiptDate(res.getDate("receiptDate"));
            inventoryTransaction.setWarehouseReceiver(new UserModel(res.getInt("warehouseReceiverId")));
            inventoryTransaction.setInvoice(new InvoiceModel(res.getString("invoiceId")));
        } catch (SQLException ex) {
            Logger.getLogger(InventoryTransactionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inventoryTransaction;
    }

}
