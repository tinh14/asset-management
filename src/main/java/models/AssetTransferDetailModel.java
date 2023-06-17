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
public class AssetTransferDetailModel {

    private AssetTransferModel assetTransfer;
    private AssetModel asset;
    private int quantity;

    public AssetTransferDetailModel() {
    }

    public AssetTransferDetailModel(@JsonProperty("assetId") int assetId, 
            @JsonProperty("quantity") int quantity, 
            @JsonProperty("assetTransferId") int assetTransferId) {
        this.asset = new AssetModel(assetId);
        this.quantity = quantity;
        this.assetTransfer = new AssetTransferModel(assetTransferId);
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
     * @return the assetTransfer
     */
    public AssetTransferModel getAssetTransfer() {
        return assetTransfer;
    }

    /**
     * @param assetTransfer the assetTransfer to set
     */
    public void setAssetTransfer(AssetTransferModel assetTransfer) {
        this.assetTransfer = assetTransfer;
    }

}
