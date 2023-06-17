/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author tinhlam
 */
public class LogModel {
    private int id;
    private Timestamp timestamp;
    private String recordId;
    private ActionModel action;
    private UserModel user;
    private List<LogDetailModel> logDetailList;
    private List<LogAssetDetailModel> logAssetDetailList;
    
    
    public LogModel() {
    }
    
    public LogModel(int id){
        this.id = id;
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
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the recordId
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the action
     */
    public ActionModel getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(ActionModel action) {
        this.action = action;
    }

    /**
     * @return the user
     */
    public UserModel getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserModel user) {
        this.user = user;
    }

    /**
     * @return the logDetailList
     */
    public List<LogDetailModel> getLogDetailList() {
        return logDetailList;
    }

    /**
     * @param logDetailList the logDetailList to set
     */
    public void setLogDetailList(List<LogDetailModel> logDetailList) {
        this.logDetailList = logDetailList;
    }

    /**
     * @return the logAssetDetailList
     */
    public List<LogAssetDetailModel> getLogAssetDetailList() {
        return logAssetDetailList;
    }

    /**
     * @param logAssetDetailList the logAssetDetailList to set
     */
    public void setLogAssetDetailList(List<LogAssetDetailModel> logAssetDetailList) {
        this.logAssetDetailList = logAssetDetailList;
    }
}
