/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author tinhlam
 */
public class DepreciationDetailModel {

    private DepreciationModel depreciation;
    private AssetModel asset;
    private int depreciationPeriod;
    private int percentageDepreciation;
    private int periodDepreciationExpense;
    private int accumulatedDepreciation;
    private int remainingValue;
    private int totalValue;

    public DepreciationDetailModel() {

    }
    
    public DepreciationDetailModel(
            @JsonProperty("depreciationId") int depreciationId,
            @JsonProperty("assetId") int assetId,
            @JsonProperty("depreciationPeriod") int depreciationPeriod,
            @JsonProperty("percentageDepreciation") int percentageDepreciation,
            @JsonProperty("periodDepreciationExpense") int periodDepreciationExpense,
            @JsonProperty("accumulatedDepreciation") int accumulatedDepreciation,
            @JsonProperty("remainingValue") int remainingValue,
            @JsonProperty("totalValue") int totalValue) {
        this.depreciationPeriod = depreciationPeriod;
        this.percentageDepreciation = percentageDepreciation;
        this.periodDepreciationExpense = periodDepreciationExpense;
        this.accumulatedDepreciation = accumulatedDepreciation;
        this.remainingValue = remainingValue;
        this.totalValue = totalValue;
        this.depreciation = new DepreciationModel(depreciationId);
        this.asset = new AssetModel(assetId);
    }

    /**
     * @return the depreciation
     */
    public DepreciationModel getDepreciation() {
        return depreciation;
    }

    /**
     * @param depreciation the depreciation to set
     */
    public void setDepreciation(DepreciationModel depreciation) {
        this.depreciation = depreciation;
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
     * @return the depreciationPeriod
     */
    public int getDepreciationPeriod() {
        return depreciationPeriod;
    }

    /**
     * @param depreciationPeriod the depreciationPeriod to set
     */
    public void setDepreciationPeriod(int depreciationPeriod) {
        this.depreciationPeriod = depreciationPeriod;
    }

    /**
     * @return the percentageDepreciation
     */
    public int getPercentageDepreciation() {
        return percentageDepreciation;
    }

    /**
     * @param percentageDepreciation the percentageDepreciation to set
     */
    public void setPercentageDepreciation(int percentageDepreciation) {
        this.percentageDepreciation = percentageDepreciation;
    }

    /**
     * @return the periodDepreciationExpense
     */
    public int getPeriodDepreciationExpense() {
        return periodDepreciationExpense;
    }

    /**
     * @param periodDepreciationExpense the periodDepreciationExpense to set
     */
    public void setPeriodDepreciationExpense(int periodDepreciationExpense) {
        this.periodDepreciationExpense = periodDepreciationExpense;
    }

    /**
     * @return the accumulatedDepreciation
     */
    public int getAccumulatedDepreciation() {
        return accumulatedDepreciation;
    }

    /**
     * @param accumulatedDepreciation the accumulatedDepreciation to set
     */
    public void setAccumulatedDepreciation(int accumulatedDepreciation) {
        this.accumulatedDepreciation = accumulatedDepreciation;
    }

    /**
     * @return the remainingValue
     */
    public int getRemainingValue() {
        return remainingValue;
    }

    /**
     * @param remainingValue the remainingValue to set
     */
    public void setRemainingValue(int remainingValue) {
        this.remainingValue = remainingValue;
    }

    /**
     * @return the totalValue
     */
    public int getTotalValue() {
        return totalValue;
    }

    /**
     * @param totalValue the totalValue to set
     */
    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

}
