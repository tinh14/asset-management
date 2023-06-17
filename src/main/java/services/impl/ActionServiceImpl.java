/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import daos.interfaces.ActionDAO;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import models.ActionModel;
import services.interfaces.ActionService;

/**
 *
 * @author tinhlam
 */
@RequestScoped
public class ActionServiceImpl implements ActionService {

    @Inject
    private ActionDAO actionDAO;

    @Override
    public List<ActionModel> findAll() {
        return actionDAO.findAll();
    }

    @Override
    public List<ActionModel> findByIdAndResourceId(String id, String resourceId) {
        return actionDAO.findByIdAndResourceId(id, resourceId);
    }
    
}
