/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import utilz.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.UserModel;
import services.interfaces.AccountService;
import services.interfaces.DepartmentService;
import services.interfaces.LogService;
import services.interfaces.UserService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;

/**
 *
 * @author ACE
 */
@WebServlet(name = "UserController", urlPatterns = {"/users/*"})
public class UserController extends HttpServlet {

    @Inject
    private UserService userService;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private AccountService accountService;

    @Inject
    private LogService logService;
    
    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        req.setAttribute("users", userService.findByName(stringKey));
        req.getRequestDispatcher("/views/minified/users.jsp").forward(req, res);
    }

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String action = req.getParameter("action");
        if (action != null) {
            String key = req.getParameter("key");
            switch (action) {
                case "search":
                    handleSearch(req, res);
                    return;
                case "searchDepartment":
                    req.setAttribute("departments", departmentService.findByName(key));
                    break;
                case "searchAccount":
                    req.setAttribute("accounts", accountService.findByUsernameWithWildcard(key));
            }
            req.getRequestDispatcher("/views/minified/update_user.jsp").forward(req, res);
            return;
        }

        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("users", userService.findAll());
            req.setAttribute("departments", departmentService.findAll());
            req.getRequestDispatcher("/views/minified/users.jsp").forward(req, res);
            return;
        }

        try {
            int id = Integer.valueOf(param);
            req.setAttribute("isUpdatePage", true);
            req.setAttribute("user", userService.findById(id).get(0));
            req.getRequestDispatcher("/views/minified/update_user.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "ID không tồn tại";
            int status = HttpServletResponse.SC_BAD_REQUEST;
            ControllerUtilz.sendMessage(new ResponseMessage(status, message), res);
        }
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("isUpdatePage", false);
        req.getRequestDispatcher("/views/minified/update_user.jsp").forward(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String page = req.getPathInfo();
        if (page.equals("/view")) {
            handleViewPage(req, res);
        } else {
            handleAddPage(req, res);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.userValidator(node);
        UserModel newUser = null;
        if (!responseMessage.isError()) {
            newUser = mapper.treeToValue(node, UserModel.class);
            responseMessage = userService.create(newUser);
        }
        
        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, newUser);
        }
        
        ControllerUtilz.sendMessage(responseMessage, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.userValidator(node);
        UserModel newUser = null;
        UserModel oldUser = null;
        if (!responseMessage.isError()) {
            newUser = mapper.treeToValue(node, UserModel.class);
            oldUser = userService.findById(newUser.getId()).get(0);
            responseMessage = userService.update(newUser);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, oldUser, newUser);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }
}
