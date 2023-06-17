/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import utilz.ResponseMessage;
import java.util.List;
import models.InventoryTransactionModel;

/**
 *
 * @author tinhlam
 */
public interface InventoryTransactionService {
    public List<InventoryTransactionModel> findAll();
    public List<InventoryTransactionModel> findById(int id);
    public List<InventoryTransactionModel> findByInvoiceIdWithWildcard(String id);
    public ResponseMessage create(InventoryTransactionModel inventoryTransaction);
    public ResponseMessage update(InventoryTransactionModel inventoryTransaction);
}
