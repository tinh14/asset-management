/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.ActionDAO;
import daos.interfaces.GenericDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.ActionMapper;
import models.ActionModel;

/**
 *
 * @author tinhlam
 */
public class ActionDAOImpl implements ActionDAO {

    @Inject
    private GenericDAO genericDAO;

    @Override
    public List<ActionModel> findAll() {
        String sql = "select * from Action";
        return genericDAO.executeQuery(sql, new ActionMapper());
    }

    @Override
    public List<ActionModel> findByResourceId(String id) {
        String sql = "select * from Action where resourceId = ? order by id desc";
        return genericDAO.executeQuery(sql, new ActionMapper(), id);
    }

    @Override
    public List<ActionModel> findByIdAndResourceId(String id, String resourceId) {
        String sql = "select * from Action where id = ? and resourceId = ?";
        return genericDAO.executeQuery(sql, new ActionMapper(), id, resourceId);
    }

}
