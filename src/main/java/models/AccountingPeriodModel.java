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
public class AccountingPeriodModel {

    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private AccountingPeriodTypeModel accountingPeriodType;

    public AccountingPeriodModel() {
    }

    public AccountingPeriodModel(int id) {
        this.id = id;
    }

    public AccountingPeriodModel(@JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("startDate") Date startDate,
            @JsonProperty("endDate") Date endDate,
            @JsonProperty("accountingPeriodTypeId") String accountingPeriodTypeId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accountingPeriodType = new AccountingPeriodTypeModel(accountingPeriodTypeId);
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the accountingPeriodType
     */
    public AccountingPeriodTypeModel getAccountingPeriodType() {
        return accountingPeriodType;
    }

    /**
     * @param accountingPeriodType the accountingPeriodType to set
     */
    public void setAccountingPeriodType(AccountingPeriodTypeModel accountingPeriodType) {
        this.accountingPeriodType = accountingPeriodType;
    }

}
