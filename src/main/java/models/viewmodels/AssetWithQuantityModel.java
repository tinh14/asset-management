/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.viewmodels;

import models.AssetModel;

/**
 *
 * @author tinhlam
 */
public class AssetWithQuantityModel {
    private AssetModel asset;
    private int quantity;

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
    
}
