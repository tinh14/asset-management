/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author tinhlam
 */
public class InventoryTransactionModel {

    private int id;
    private Date receiptDate;
    private UserModel warehouseReceiver;
    private InvoiceModel invoice;
    private List<InventoryTransactionDetailModel> inventoryTransactionDetailList;

    public InventoryTransactionModel() {
    }

    public InventoryTransactionModel(@JsonProperty("id") int id,
            @JsonProperty("receiptDate") Date receiptDate,
            @JsonProperty("warehouseReceiverId") int warehouseReceiverId,
            @JsonProperty("invoiceId") String invoiceId,
            @JsonProperty("inventoryTransactionDetailList") List<InventoryTransactionDetailModel> inventoryTransactionDetailList) {
        this.id = id;
        this.receiptDate = receiptDate;
        this.warehouseReceiver = new UserModel(warehouseReceiverId);
        this.invoice = new InvoiceModel(invoiceId);
        this.inventoryTransactionDetailList = inventoryTransactionDetailList;
        System.out.println(inventoryTransactionDetailList.get(0).getAsset().getId());
    }

    public InventoryTransactionModel(int id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the receiptDate
     */
    public Date getReceiptDate() {
        return receiptDate;
    }

    /**
     * @param receiptDate the receiptDate to set
     */
    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    /**
     * @return the warehouseReceiver
     */
    public UserModel getWarehouseReceiver() {
        return warehouseReceiver;
    }

    /**
     * @param warehouseReceiver the warehouseReceiver to set
     */
    public void setWarehouseReceiver(UserModel warehouseReceiver) {
        this.warehouseReceiver = warehouseReceiver;
    }

    /**
     * @return the invoice
     */
    public InvoiceModel getInvoice() {
        return invoice;
    }

    /**
     * @param invoice the invoice to set
     */
    public void setInvoice(InvoiceModel invoice) {
        this.invoice = invoice;
    }

    /**
     * @return the inventoryTransactionDetailList
     */
    public List<InventoryTransactionDetailModel> getInventoryTransactionDetailList() {
        return inventoryTransactionDetailList;
    }

    /**
     * @param inventoryTransactionDetailList the inventoryTransactionDetailList
     * to set
     */
    public void setInventoryTransactionDetailList(List<InventoryTransactionDetailModel> inventoryTransactionDetailList) {
        this.inventoryTransactionDetailList = inventoryTransactionDetailList;
    }

}
