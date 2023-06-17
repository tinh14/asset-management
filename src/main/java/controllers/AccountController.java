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
import models.AccountModel;
import services.interfaces.AccountService;
import services.interfaces.LogService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;

/**
 *
 * @author ACE
 */
@WebServlet(name = "AccountController", urlPatterns = {"/accounts/*"})
public class AccountController extends HttpServlet {

    @Inject
    private AccountService accountService;
    
    @Inject
    private LogService logService;

    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        req.setAttribute("accounts", accountService.findByUsernameWithWildcard(stringKey));
        req.getRequestDispatcher("/views/minified/accounts.jsp").forward(req, res);
    }

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getParameter("action");
        if (action != null) {
            handleSearch(req, res);
            return;
        }

        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("accounts", accountService.findAll());
            req.getRequestDispatcher("/views/minified/accounts.jsp").forward(req, res);
            return;
        }

        try {
            String username = param;
            req.setAttribute("isUpdatePage", true);
            req.setAttribute("account", accountService.findByUsername(username).get(0));
            req.getRequestDispatcher("/views/minified/update_account.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "Tên đăng nhập không tồn tại";
            int status = HttpServletResponse.SC_BAD_REQUEST;
            ControllerUtilz.sendMessage(new ResponseMessage(status, message), res);
        }
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("isUpdatePage", false);
        req.getRequestDispatcher("/views/minified/update_account.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.accountValidator(node);
        AccountModel newAccount = null;
        if (!responseMessage.isError()) {
            newAccount = mapper.treeToValue(node, AccountModel.class);
            responseMessage = accountService.create(newAccount);
        }
        
        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, newAccount);
        }
        
        ControllerUtilz.sendMessage(responseMessage, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.accountValidator(node);
        AccountModel newAccount = null;
        AccountModel oldAccount = null;
        if (!responseMessage.isError()) {
            newAccount = mapper.treeToValue(node, AccountModel.class);
            oldAccount = accountService.findByUsername(newAccount.getUsername()).get(0);
            responseMessage = accountService.update(newAccount);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, oldAccount, newAccount);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }
}
