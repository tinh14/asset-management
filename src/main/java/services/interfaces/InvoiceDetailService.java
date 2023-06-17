/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.interfaces;

import java.util.List;
import models.InventoryTransactionDetailModel;

/**
 *
 * @author tinhlam
 */
public interface InvoiceDetailService {
    public List<InventoryTransactionDetailModel> findByAssetId(int id);
}
