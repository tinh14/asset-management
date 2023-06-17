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
public class DepreciationModel {

    private int id;
    private Date depreciationDate;
    private int periodDepreciationExpense;
    private int accumulatedDepreciation;
    private int remainingValue;
    private int totalValue;
    private DepreciationMethodModel depreciationMethod;
    private AccountingPeriodModel accountingPeriod;
    private UserModel creator;
    private List<DepreciationDetailModel> depreciationDetailList;

    public DepreciationModel() {

    }

    public DepreciationModel(int id) {
        this.id = id;
    }

    public DepreciationModel(
            @JsonProperty("id") int id,
            @JsonProperty("depreciationDate") Date depreciationDate,
            @JsonProperty("creatorId") int creatorId,
            @JsonProperty("depreciationMethodId") String depreciationMethodId,
            @JsonProperty("accountingPeriodId") int accountingPeriodId,
            @JsonProperty("totalValue") int totalValue,
            @JsonProperty("periodDepreciationExpense") int periodDepreciationExpense,
            @JsonProperty("accumulatedDepreciation") int accumulatedDepreciation,
            @JsonProperty("remainingValue") int remainingValue,
            @JsonProperty("depreciationDetailList") List<DepreciationDetailModel> depreciationDetailList) {
        this.id = id;
        this.depreciationDate = depreciationDate;
        this.creator = new UserModel(creatorId);
        this.depreciationMethod = new DepreciationMethodModel(depreciationMethodId);
        this.accountingPeriod = new AccountingPeriodModel(accountingPeriodId);
        this.totalValue = totalValue;
        this.periodDepreciationExpense = periodDepreciationExpense;
        this.accumulatedDepreciation = accumulatedDepreciation;
        this.remainingValue = remainingValue;
        this.depreciationDetailList = depreciationDetailList;
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
     * @return the depreciationDate
     */
    public Date getDepreciationDate() {
        return depreciationDate;
    }

    /**
     * @param depreciationDate the depreciationDate to set
     */
    public void setDepreciationDate(Date depreciationDate) {
        this.depreciationDate = depreciationDate;
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

    /**
     * @return the depreciationMethod
     */
    public DepreciationMethodModel getDepreciationMethod() {
        return depreciationMethod;
    }

    /**
     * @param depreciationMethod the depreciationMethod to set
     */
    public void setDepreciationMethod(DepreciationMethodModel depreciationMethod) {
        this.depreciationMethod = depreciationMethod;
    }

    /**
     * @return the accountingPeriod
     */
    public AccountingPeriodModel getAccountingPeriod() {
        return accountingPeriod;
    }

    /**
     * @param accountingPeriod the accountingPeriod to set
     */
    public void setAccountingPeriod(AccountingPeriodModel accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    /**
     * @return the creator
     */
    public UserModel getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    /**
     * @return the depreciationDetailList
     */
    public List<DepreciationDetailModel> getDepreciationDetailList() {
        return depreciationDetailList;
    }

    /**
     * @param depreciationDetailList the depreciationDetailList to set
     */
    public void setDepreciationDetailList(List<DepreciationDetailModel> depreciationDetailList) {
        this.depreciationDetailList = depreciationDetailList;
    }

}
