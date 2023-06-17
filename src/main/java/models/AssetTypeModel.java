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
public class AssetTypeModel {
    @JsonProperty("id")
    private int id;
    private String name;
    private int depreciationPeriod;

    public AssetTypeModel() {
    }
    
    public AssetTypeModel(int id){
        this.id = id;
    }

    public AssetTypeModel(int id, String name, int depreciationPeriod) {
        this.id = id;
        this.name = name;
        this.depreciationPeriod = depreciationPeriod;
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

   

}
