/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import daos.interfaces.DepartmentDAO;
import daos.interfaces.TransactionManager;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.AuthorizationModel;
import models.DepartmentModel;
import services.interfaces.DepartmentService;

/**
 *
 * @author tinhlam
 */
public class DepartmentServiceImpl implements DepartmentService {

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<DepartmentModel> findAll() {
        return departmentDAO.findAll();
    }

    @Override
    public List<DepartmentModel> findById(int id) {
        return departmentDAO.findById(id);
    }

    @Override
    public List<DepartmentModel> findByName(String name) {
        return departmentDAO.findByName(name);
    }

    @Override
    public ResponseMessage create(DepartmentModel department) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = departmentDAO.create(transactionManager.getConnection(), department);
            department.setId(id);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(DepartmentModel department) {
        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            departmentDAO.update(transactionManager.getConnection(), department);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();

        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage delete(int id) {
//        ResponseMessage res = departmentDAO.delete(transactionManager.getConnection(), id);
//        transactionManager.commitAndCloseConnection();
//        return res;
        return null;
    }

}
