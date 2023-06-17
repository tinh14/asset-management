/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.AuthorizationModel;
import services.interfaces.AccountService;
import services.interfaces.AuthorizationService;
import services.interfaces.ResourceService;
import utilz.ControllerUtilz;

/**
 *
 * @author ACE
 */
@WebServlet(name = "AuthorizationController", urlPatterns = {"/accounts/authorization/*"})
public class AuthorizationController extends HttpServlet {

    @Inject
    private ResourceService resourceService;

    @Inject
    private AuthorizationService authorizationService;

    @Inject
    private AccountService accountService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        req.setAttribute("authorizations", authorizationService.getPermission(username));
        req.setAttribute("resources", resourceService.findAll());
        req.setAttribute("account", accountService.findByUsername(username).get(0));
        req.getRequestDispatcher("/views/minified/authorization.jsp").forward(req, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, AuthorizationModel.class);
        List<AuthorizationModel> authorizations = objectMapper.readValue(req.getInputStream(), javaType);
        ControllerUtilz.sendMessage(authorizationService.update(authorizations), res);
    }
}
