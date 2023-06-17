/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import utilz.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.DepreciationModel;
import models.InventoryTransactionModel;
import services.interfaces.AccountingPeriodService;
import services.interfaces.DepreciationDetailService;
import services.interfaces.DepreciationMethodService;
import services.interfaces.DepreciationService;
import services.interfaces.InventoryTransactionService;
import services.interfaces.LogService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;

/**
 *
 * @author ACE
 */
@WebServlet(name = "DepreciationController", urlPatterns = {"/depreciation/*"})
public class DepreciationController extends HttpServlet {

    @Inject
    private InventoryTransactionService inventoryTransactionService;

    @Inject
    private DepreciationService depreciationService;

    @Inject
    private DepreciationMethodService depreciationMethodService;

    @Inject
    private AccountingPeriodService accountingPeriodService;

    @Inject
    private DepreciationDetailService depreciationDetailService;
    

    @Inject
    private LogService logService;

    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        List<DepreciationModel> list = depreciationService.findByAccountingPeriodName(stringKey);
        req.setAttribute("depreciationSchedules", list);
        req.getRequestDispatcher("/views/minified/depreciation.jsp").forward(req, res);
    }

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // ex: ?action=...
        String action = req.getParameter("action");
        if (action != null) {
            String key = req.getParameter("key");
            switch (action) {
                case "search":
                    handleSearch(req, res);
                    return;
                case "getDepreciationDetailList":
                    DepreciationModel model = new DepreciationModel();
                    model.setDepreciationDetailList(depreciationDetailService.findByAccoutingPeriod(accountingPeriodService.findById(Integer.valueOf(key)).get(0)));
                    req.setAttribute("depreciationSchedule", model);
                    break;
                case "searchAccountingPeriod":
                    req.setAttribute("accountingPeriods", accountingPeriodService.findByName(key));
                    break;
                case "searchDepreciationMethod":
                    req.setAttribute("depreciationMethods", depreciationMethodService.findByName(key));
                    break;
            }
            req.getRequestDispatcher("/views/minified/update_depreciation.jsp").forward(req, res);
            return;
        }

        req.setAttribute("depreciationMethods", depreciationMethodService.findAll());

        // ex: /view
        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("depreciationSchedules", depreciationService.findAll());
            req.getRequestDispatcher("/views/minified/depreciation.jsp").forward(req, res);
            return;
        }

        // ex: /view?id=1
        try {
            int id = Integer.parseInt(param);
            req.setAttribute("isUpdatePage", true);
            req.setAttribute("depreciationSchedule", depreciationService.findById(id).get(0));
            req.getRequestDispatcher("/views/minified/update_depreciation.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "ID không tồn tại";
            ControllerUtilz.sendMessage(new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, message), res);
        }
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("isUpdatePage", false);
        req.setAttribute("depreciationMethods", depreciationMethodService.findAll());
        req.getRequestDispatcher("/views/minified/update_depreciation.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.depreciationValidator(node);
        DepreciationModel depreciation = null;
        if (!responseMessage.isError()) {
            depreciation = mapper.treeToValue(node, DepreciationModel.class);
            responseMessage = depreciationService.create(depreciation);
            depreciation = depreciationService.findById(depreciation.getId()).get(0);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, depreciation);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.inventoryTransactionValidator(node);
        InventoryTransactionModel newInventoryTransaction = null;
        InventoryTransactionModel oldInventoryTransaction = null;
        if (!responseMessage.isError()) {
            newInventoryTransaction = mapper.treeToValue(node, InventoryTransactionModel.class);
            oldInventoryTransaction = inventoryTransactionService.findById(newInventoryTransaction.getId()).get(0);
            responseMessage = inventoryTransactionService.update(newInventoryTransaction);
            newInventoryTransaction = inventoryTransactionService.findById(newInventoryTransaction.getId()).get(0);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, oldInventoryTransaction, newInventoryTransaction);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

}
