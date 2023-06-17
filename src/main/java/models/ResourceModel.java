/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;

/**
 *
 * @author tinhlam
 */
public class ResourceModel {

    private String id;
    private String name;
    private List<ActionModel> actions;

    public ResourceModel(){
    }
    
    public ResourceModel(String id) {
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
     * @return the actions
     */
    public List<ActionModel> getActions() {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(List<ActionModel> actions) {
        this.actions = actions;
    }

}
