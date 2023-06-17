/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import models.InventoryTransactionDetailModel;

/**
 *
 * @author tinhlam
 */
public interface InventoryTransactionDetailDAO {

    public List<InventoryTransactionDetailModel> findAll();

    public List<InventoryTransactionDetailModel> findByAssetId(int id);
    
    public List<InventoryTransactionDetailModel> findByAssetIdWithGroupByAssetId(int id);

    public List<InventoryTransactionDetailModel> findByInventoryTransactionId(int id);

    public void deleteByAssetId(Connection connection, int id) throws SQLException;

    public void deleteByInventoryTransactionId(Connection connection, int id) throws SQLException;

    public void create(Connection connection, InventoryTransactionDetailModel invoiceDetail) throws SQLException;

}
