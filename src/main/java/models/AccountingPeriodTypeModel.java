/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author tinhlam
 */
public class AccountingPeriodTypeModel {
    private String id;
    private String name;
    private int numberOfMonths;

    public AccountingPeriodTypeModel(){
        
    }
    
    public AccountingPeriodTypeModel(String id){
        this.id = id;
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
     * @return the numberOfMonths
     */
    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    /**
     * @param numberOfMonths the numberOfMonths to set
     */
    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }
}
