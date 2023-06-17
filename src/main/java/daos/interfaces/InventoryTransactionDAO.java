/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.InventoryTransactionModel;

/**
 *
 * @author tinhlam
 */
public interface InventoryTransactionDAO {

    public List<InventoryTransactionModel> findAll();

    public List<InventoryTransactionModel> findById(int id);
    
    public List<InventoryTransactionModel> findByInvoiceIdWithWildcard(String id);

    public int create(Connection connection, InventoryTransactionModel inventoryTransaction) throws SQLException;

    public void update(Connection connection, InventoryTransactionModel inventoryTransaction) throws SQLException;
}
