/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.impl;

import java.util.List;
import javax.inject.Inject;
import models.InventoryTransactionDetailModel;
import services.interfaces.*;
import daos.interfaces.InventoryTransactionDetailDAO;

/**
 *
 * @author tinhlam
 */
public class InvoiceDetailServiceImpl implements InvoiceDetailService{

    @Inject
    private InventoryTransactionDetailDAO invoiceDetailDAO;
    
    @Override
    public List<InventoryTransactionDetailModel> findByAssetId(int id) {
        return invoiceDetailDAO.findByAssetId(id);
    }
    
}
