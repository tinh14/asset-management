/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.DepreciationMethodDAO;
import daos.interfaces.GenericDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.DepreciationMethodMapper;
import models.DepreciationMethodModel;

/**
 *
 * @author tinhlam
 */
public class DepreciationMethodDAOImpl implements DepreciationMethodDAO{

    @Inject
    private GenericDAO genericDAO;
    
    @Override
    public List<DepreciationMethodModel> findAll() {
        String sql = "select * from DepreciationMethod";
        return genericDAO.executeQuery(sql, new DepreciationMethodMapper());
    }

    @Override
    public List<DepreciationMethodModel> findById(String id) {
        String sql = "select * from DepreciationMethod where id = ?";
        return genericDAO.executeQuery(sql, new DepreciationMethodMapper(), id);
    }

    @Override
    public List<DepreciationMethodModel> findByName(String name) {
        String sql = "select * from DepreciationMethod where name like ?";
        return genericDAO.executeQuery(sql, new DepreciationMethodMapper(), "%"+name+"%");
    }
    
}
