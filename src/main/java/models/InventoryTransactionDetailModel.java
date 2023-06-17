/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author tinhlam
 */
public class InventoryTransactionDetailModel {

    private AssetModel asset;
    private int quantity;
    private int price;
    private int VAT;
    private InventoryTransactionModel inventoryTransaction;

    public InventoryTransactionDetailModel() {
    }
    
    public InventoryTransactionDetailModel(@JsonProperty("inventoryTransactionId") int inventoryTransactionId,
            @JsonProperty("assetId") int assetId,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("price") int price,
            @JsonProperty("VAT") int VAT) {
        this.inventoryTransaction = new InventoryTransactionModel(inventoryTransactionId);
        this.asset = new AssetModel(assetId);
        this.quantity = quantity;
        this.price = price;
        this.VAT = VAT;
    }

    /**
     * @return the asset
     */
    public AssetModel getAsset() {
        return asset;
    }

    /**
     * @param asset the asset to set
     */
    public void setAsset(AssetModel asset) {
        this.asset = asset;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the VAT
     */
    public int getVAT() {
        return VAT;
    }

    /**
     * @param VAT the VAT to set
     */
    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    /**
     * @return the inventoryTransaction
     */
    public InventoryTransactionModel getInventoryTransaction() {
        return inventoryTransaction;
    }

    /**
     * @param inventoryTransaction the inventoryTransaction to set
     */
    public void setInventoryTransaction(InventoryTransactionModel inventoryTransaction) {
        this.inventoryTransaction = inventoryTransaction;
    }

    
}
