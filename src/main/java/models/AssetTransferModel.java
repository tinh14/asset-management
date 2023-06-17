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
public class AssetTransferModel {

    private int id;
    private Date transferDate;
    private DepartmentModel receivingDepartment;
    private UserModel transferor;
    private List<AssetTransferDetailModel> assetTransferDetailList;

    public AssetTransferModel() {
    }

    public AssetTransferModel(int id) {
        this.id = id;
    }

    public AssetTransferModel(@JsonProperty("id") int id,
            @JsonProperty("transferDate") Date transferDate, 
            @JsonProperty("receivingDepartmentId") int receivingDepartmentId, 
            @JsonProperty("transferorId") int transferorId, 
            @JsonProperty("assetTransferDetailList") List<AssetTransferDetailModel> assetTransferDetailList) {
        this.id = id;
        this.transferDate = transferDate;
        this.receivingDepartment = new DepartmentModel(receivingDepartmentId);
        this.transferor = new UserModel(transferorId);
        this.assetTransferDetailList = assetTransferDetailList;
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
     * @return the transferDate
     */
    public Date getTransferDate() {
        return transferDate;
    }

    /**
     * @param transferDate the transferDate to set
     */
    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    /**
     * @return the receivingDepartment
     */
    public DepartmentModel getReceivingDepartment() {
        return receivingDepartment;
    }

    /**
     * @param receivingDepartment the receivingDepartment to set
     */
    public void setReceivingDepartment(DepartmentModel receivingDepartment) {
        this.receivingDepartment = receivingDepartment;
    }

    /**
     * @return the assetTransferDetailList
     */
    public List<AssetTransferDetailModel> getAssetTransferDetailList() {
        return assetTransferDetailList;
    }

    /**
     * @param assetTransferDetailList the assetTransferDetailList to set
     */
    public void setAssetTransferDetailList(List<AssetTransferDetailModel> assetTransferDetailList) {
        this.assetTransferDetailList = assetTransferDetailList;
    }

    /**
     * @return the transferor
     */
    public UserModel getTransferor() {
        return transferor;
    }

    /**
     * @param transferor the transferor to set
     */
    public void setTransferor(UserModel transferor) {
        this.transferor = transferor;
    }

}
