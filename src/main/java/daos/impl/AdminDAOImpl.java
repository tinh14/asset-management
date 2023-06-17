/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos.impl;

import daos.interfaces.AdminDAO;
import java.util.List;
import javax.inject.Inject;
import mappers.AdminMapper;
import models.AdminModel;
import daos.interfaces.GenericDAO;

/**
 *
 * @author tinhlam
 */
public class AdminDAOImpl implements AdminDAO{
    @Inject
    GenericDAO genericDAO;
    
    @Override
    public List<AdminModel> findByPersonId(int personId) {
        String sql = "select * from Admin where personId = ?";
        return genericDAO.executeQuery(sql, new AdminMapper(), personId);
    }

}
