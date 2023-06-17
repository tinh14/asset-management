/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import daos.interfaces.ActionDAO;
import daos.interfaces.ResourceDAO;
import java.util.List;
import javax.inject.Inject;
import models.ResourceModel;
import services.interfaces.ResourceService;

/**
 *
 * @author tinhlam
 */
public class ResourceServiceImpl implements ResourceService{

    @Inject
    private ResourceDAO resourceDAO;
    
    @Inject
    private ActionDAO actionDAO;
    
    @Override
    public List<ResourceModel> findAll() {
        List<ResourceModel> resources = resourceDAO.findAll();
        for (ResourceModel resource : resources){
            resource.setActions(actionDAO.findByResourceId(resource.getId()));
        }
        return resources;
    }
    
}
