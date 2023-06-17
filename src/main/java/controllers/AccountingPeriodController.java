/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.AccountingPeriodModel;
import services.interfaces.AccountingPeriodService;
import services.interfaces.AccountingPeriodTypeService;
import services.interfaces.LogService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;
import utilz.ResponseMessage;

/**
 *
 * @author ACE
 */
@WebServlet(name = "AccountingPeriodController", urlPatterns = {"/accountingPeriods/*"})
public class AccountingPeriodController extends HttpServlet {

    @Inject
    private AccountingPeriodService accountingPeriodService;

    @Inject
    private AccountingPeriodTypeService accountingPeriodTypeService;

    @Inject
    private LogService logService;

    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        req.setAttribute("accountingPeriods", accountingPeriodService.findByName(stringKey));
        req.getRequestDispatcher("/views/minified/accounting_periods.jsp").forward(req, res);
    }

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "search":
                    handleSearch(req, res);
                    return;
                case "searchAccountingPeriodType":
                    String stringKey = req.getParameter("key");
                    req.setAttribute("accountingPeriodTypes", accountingPeriodTypeService.findByName(stringKey));
                    break;
            }
            req.getRequestDispatcher("/views/minified/update_accounting_period.jsp").forward(req, res);
            return;
        }

        req.setAttribute("accountingPeriodTypes", accountingPeriodTypeService.findAll());

        req.setAttribute("accountingPeriods", accountingPeriodService.findAll());
        req.getRequestDispatcher("/views/minified/accounting_periods.jsp").forward(req, res);
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("accountingPeriodTypes", accountingPeriodTypeService.findAll());
        req.getRequestDispatcher("/views/minified/update_accounting_period.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.accountingPeriodValidator(node);
        AccountingPeriodModel accountingPeriod = null;
        if (!responseMessage.isError()) {
            accountingPeriod = mapper.treeToValue(node, AccountingPeriodModel.class);
            responseMessage = accountingPeriodService.create(accountingPeriod);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, accountingPeriod);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

}
