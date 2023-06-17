/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;

/**
 *
 * @author tinhlam
 */
public class InvoiceModel {

    private String id;
    private int number;
    private Date invoiceDate;
    private SupplierModel supplier;

    public InvoiceModel() {
    }

    public InvoiceModel(String id) {
        this.id = id;
    }

    public InvoiceModel(
            @JsonProperty("id") String id,
            @JsonProperty("number") int number,
            @JsonProperty("invoiceDate") Date invoiceDate,
            @JsonProperty("supplierId") int supplierId) {
        this.id = id;
        this.number = number;
        this.invoiceDate = invoiceDate;
        this.supplier = new SupplierModel(supplierId);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the invoiceDate
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * @param invoiceDate the invoiceDate to set
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * @return the supplier
     */
    public SupplierModel getSupplier() {
        return supplier;
    }

    /**
     * @param supplier the supplier to set
     */
    public void setSupplier(SupplierModel supplier) {
        this.supplier = supplier;
    }

}
