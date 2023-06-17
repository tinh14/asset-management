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
public class AssetDisposeDetailModel {

    private AssetDisposeModel assetDispose;
    private AssetModel asset;
    private int quantity;

    public AssetDisposeDetailModel() {
    }

    public AssetDisposeDetailModel(@JsonProperty("assetDisposeId") int assetDisposalId,
            @JsonProperty("assetId") int assetId,
            @JsonProperty("quantity") int quantity) {
        this.assetDispose = new AssetDisposeModel(assetDisposalId);
        this.asset = new AssetModel(assetId);
        this.quantity = quantity;
    }

    /**
     * @return the assetDispose
     */
    public AssetDisposeModel getAssetDispose() {
        return assetDispose;
    }

    /**
     * @param assetDispose the assetDispose to set
     */
    public void setAssetDispose(AssetDisposeModel assetDispose) {
        this.assetDispose = assetDispose;
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
}
