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
public class AssetDisposeModel {
    private int id;
    private Date disposalDate;
    private UserModel disposer;
    private DepartmentModel disposalDepartment;
    private String reason;
    private List<AssetDisposeDetailModel> AssetDisposeDetailList;

    public AssetDisposeModel() {
    }
    
    public AssetDisposeModel(int id){
        this.id = id;
    }

    public AssetDisposeModel(@JsonProperty("id") int id,
           @JsonProperty("disposalDate") Date disposalDate,
           @JsonProperty("disposerId") int disposerId, 
           @JsonProperty("disposalDepartmentId") int disposalDepartmentId, 
           @JsonProperty("reason") String reason, 
           @JsonProperty("assetDisposeDetailList") List<AssetDisposeDetailModel> AssetDisposeDetailList) {
        this.id = id;
        this.disposalDate = disposalDate;
        this.disposer = new UserModel(disposerId);
        this.disposalDepartment = new DepartmentModel(disposalDepartmentId);
        this.reason = reason;
        this.AssetDisposeDetailList = AssetDisposeDetailList;
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
     * @return the disposalDate
     */
    public Date getDisposalDate() {
        return disposalDate;
    }

    /**
     * @param disposalDate the disposalDate to set
     */
    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
    }

    /**
     * @return the disposer
     */
    public UserModel getDisposer() {
        return disposer;
    }

    /**
     * @param disposer the disposer to set
     */
    public void setDisposer(UserModel disposer) {
        this.disposer = disposer;
    }

    /**
     * @return the disposalDepartment
     */
    public DepartmentModel getDisposalDepartment() {
        return disposalDepartment;
    }

    /**
     * @param disposalDepartment the disposalDepartment to set
     */
    public void setDisposalDepartment(DepartmentModel disposalDepartment) {
        this.disposalDepartment = disposalDepartment;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the AssetDisposeDetailList
     */
    public List<AssetDisposeDetailModel> getAssetDisposeDetailList() {
        return AssetDisposeDetailList;
    }

    /**
     * @param AssetDisposeDetailList the AssetDisposeDetailList to set
     */
    public void setAssetDisposeDetailList(List<AssetDisposeDetailModel> AssetDisposeDetailList) {
        this.AssetDisposeDetailList = AssetDisposeDetailList;
    }

    
}
