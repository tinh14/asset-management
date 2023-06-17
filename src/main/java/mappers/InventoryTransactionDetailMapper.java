/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AssetModel;
import models.InventoryTransactionDetailModel;
import models.InventoryTransactionModel;
import models.InvoiceModel;

/**
 *
 * @author tinhlam
 */
public class InventoryTransactionDetailMapper implements RowMapper<InventoryTransactionDetailModel> {

    @Override
    public InventoryTransactionDetailModel mapRow(ResultSet res) {
        InventoryTransactionDetailModel inventoryTransactionDetail = null;
        try {
            inventoryTransactionDetail = new InventoryTransactionDetailModel();
            inventoryTransactionDetail.setAsset(new AssetModel(res.getInt("assetId")));
            inventoryTransactionDetail.setQuantity(res.getInt("quantity"));

            if (res.getMetaData().getColumnCount() == 2) {
                return inventoryTransactionDetail;
            }

            inventoryTransactionDetail.setVAT(res.getInt("VAT"));
            inventoryTransactionDetail.setPrice(res.getInt("price"));
            inventoryTransactionDetail.setInventoryTransaction(new InventoryTransactionModel(res.getInt("inventoryTransactionId")));
        } catch (SQLException ex) {
            Logger.getLogger(InventoryTransactionDetailMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inventoryTransactionDetail;
    }

}
