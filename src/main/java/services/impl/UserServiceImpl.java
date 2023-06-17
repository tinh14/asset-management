/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.impl;

import utilz.ResponseMessage;
import constants.Constants;
import daos.interfaces.AccountDAO;
import daos.interfaces.DepartmentDAO;
import daos.interfaces.PersonDAO;
import daos.interfaces.TransactionManager;
import daos.interfaces.UserDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import models.PersonModel;
import models.UserModel;
import services.interfaces.UserService;

/**
 *
 * @author tinhlam
 */
public class UserServiceImpl implements UserService {

    @Inject
    private PersonDAO personDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private AccountDAO accountDAO;

    @Inject
    private TransactionManager transactionManager;

    @Override
    public List<UserModel> findAll() {
        List<UserModel> users = userDAO.findAll();
        for (UserModel user : users) {
            PersonModel person = personDAO.findById(user.getId()).get(0);
            user.setPerson(person);
            user.setDepartment(departmentDAO.findById(person.getDepartment().getId()).get(0));
        }
        return users;
    }

    @Override
    public List<UserModel> findByPersonId(int id) {
        List<UserModel> users = userDAO.findByPersonId(id);
        for (UserModel user : users) {
            PersonModel person = personDAO.findById(user.getId()).get(0);
            user.setPerson(person);
            user.setDepartment(departmentDAO.findById(person.getDepartment().getId()).get(0));
        }
        return users;
    }

    @Override
    public List<UserModel> findByName(String name) {
        List<PersonModel> people = personDAO.findByName(name);
        List<UserModel> users = new ArrayList<>();
        for (PersonModel person : people) {
            UserModel user = userDAO.findByPersonId(person.getId()).get(0);
            user.setPerson(person);
            user.setDepartment(departmentDAO.findById(user.getDepartment().getId()).get(0));
            users.add(user);
        }
        return users;
    }

    private ResponseMessage checkForeignKey(int id) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (departmentDAO.findById(id).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Phòng ban không tồn tại\n");
        }
        return response;
    }

    private ResponseMessage checkForeignKey(String username) {
        ResponseMessage response = new ResponseMessage(HttpServletResponse.SC_OK, "");
        if (accountDAO.findByUsername(username).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Tài khoản không tồn tại\n");
        }
        return response;
    }

    private ResponseMessage checkForeignKeyForCreate(String username) {
        ResponseMessage response = checkForeignKey(username);

        if (response.isError()) {
            return response;
        }

        if (!personDAO.findByAccountUsername(username).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Tài khoản đã được sử dụng\n");
        }

        return response;
    }

    private ResponseMessage checkForeignKeyForUpdate(int id, String username) {
        ResponseMessage response = checkForeignKey(username);

        if (response.isError()) {
            return response;
        }
        List<PersonModel> people = personDAO.findByAccountUsername(username);

        if (people.isEmpty()) {
            return response;
        }

        if (people.get(0).getId() != id) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setMessage("Tài khoản đã được sử dụng\n");
        }

        return response;
    }

    @Override
    public ResponseMessage create(UserModel user) {
        ResponseMessage response = checkForeignKey(user.getDepartment().getId());
        ResponseMessage response2 = checkForeignKeyForCreate(user.getAccount().getUsername());

        if (response.isError()) {
            response.appendMessage(response2.getMessage());
            return response;
        }

        if (response2.isError()) {
            return response2;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            int id = personDAO.create(transactionManager.getConnection(), user);
            user.setId(id);
            user.setDepartment(departmentDAO.findById(user.getDepartment().getId()).get(0));
            user.setAccount(accountDAO.findByUsername(user.getAccount().getUsername()).get(0));
            userDAO.create(transactionManager.getConnection(), user);
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            transactionManager.closeConnection();
        }
        return new ResponseMessage(status, message);
    }

    @Override
    public ResponseMessage update(UserModel user) {
        ResponseMessage response = checkForeignKey(user.getDepartment().getId());
        ResponseMessage response2 = checkForeignKeyForUpdate(user.getId(), user.getAccount().getUsername());

        if (response.isError()) {
            response.appendMessage(response2.getMessage());
            return response;
        }

        if (response2.isError()) {
            return response2;
        }

        int status = HttpServletResponse.SC_OK;
        String message = Constants.SAVE_SUCCESS;
        try {
            transactionManager.initConnection();
            personDAO.update(transactionManager.getConnection(), user);
            user.setDepartment(departmentDAO.findById(user.getDepartment().getId()).get(0));
            user.setAccount(accountDAO.findByUsername(user.getAccount().getUsername()).get(0));
            transactionManager.commitAndCloseConnection();
        } catch (SQLException ex) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = Constants.SAVE_FAIL;
            System.out.println(ex);
            transactionManager.closeConnection();

        }
        return new ResponseMessage(status, message);
    }
}
