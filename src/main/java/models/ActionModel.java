/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author tinhlam
 */
public class ActionModel {
    private String id;
    private String name;
    private ResourceModel resource;
    
    public ActionModel(){
    }
    
    public ActionModel(String id){
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
     * @return the resource
     */
    public ResourceModel getResource() {
        return resource;
    }

    /**
     * @param resource the resource to set
     */
    public void setResource(ResourceModel resource) {
        this.resource = resource;
    }



    
}
