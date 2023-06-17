/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import daos.interfaces.DepreciationMethodDAO;
import java.util.List;
import javax.inject.Inject;
import models.DepreciationMethodModel;
import services.interfaces.DepreciationMethodService;

/**
 *
 * @author tinhlam
 */
public class DepreciationMethodServiceImpl implements DepreciationMethodService{

    @Inject
    private DepreciationMethodDAO depreciationMethodDAO;
    
    @Override
    public List<DepreciationMethodModel> findAll() {
        return depreciationMethodDAO.findAll();
    }

    @Override
    public List<DepreciationMethodModel> findById(String id) {
        return depreciationMethodDAO.findById(id);
    }

    @Override
    public List<DepreciationMethodModel> findByName(String name) {
        return depreciationMethodDAO.findByName(name);
    }
    
}
