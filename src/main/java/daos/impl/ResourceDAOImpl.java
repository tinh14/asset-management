/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.GenericDAO;
import daos.interfaces.ResourceDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.ResourceMapper;
import models.ResourceModel;

/**
 *
 * @author tinhlam
 */
public class ResourceDAOImpl implements ResourceDAO{

    @Inject
    private GenericDAO genericDAO;
    
    @Override
    public List<ResourceModel> findAll() {
        String sql = "select * from Resource";
        return genericDAO.executeQuery(sql, new ResourceMapper());
    }
    
}
