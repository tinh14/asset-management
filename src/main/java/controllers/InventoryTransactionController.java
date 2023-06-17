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
import models.InventoryTransactionModel;
import services.interfaces.AssetService;
import services.interfaces.InventoryTransactionService;
import services.interfaces.InvoiceService;
import services.interfaces.LogService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;

/**
 *
 * @author ACE
 */
@WebServlet(name = "InventoryTransactionController", urlPatterns = {"/inventoryTransactions/*"})
public class InventoryTransactionController extends HttpServlet {

    @Inject
    private InventoryTransactionService inventoryTransactionService;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private AssetService assetService;

    @Inject
    private LogService logService;

    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        req.setAttribute("inventoryTransactions", inventoryTransactionService.findByInvoiceIdWithWildcard(stringKey));
        req.getRequestDispatcher("/views/minified/inventory_transactions.jsp").forward(req, res);
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
                case "searchAsset":
                    req.setAttribute("assets", assetService.findByName(key));
                    break;
                case "searchInvoice":
                    req.setAttribute("invoices", invoiceService.findByIdWithWildcard(key));
                    break;
                case "addItem":
                    req.setAttribute("addedAssets", assetService.findById(Integer.valueOf(key)));
                    break;
            }
            req.getRequestDispatcher("/views/minified/update_inventory_transaction.jsp").forward(req, res);
            return;
        }

        // ex: /view
        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("inventoryTransactions", inventoryTransactionService.findAll());
            req.getRequestDispatcher("/views/minified/inventory_transactions.jsp").forward(req, res);
            return;
        }

        // ex: /view?id=1
        try {
            int id = Integer.parseInt(param);
            req.setAttribute("isUpdatePage", true);
            req.setAttribute("inventoryTransaction", inventoryTransactionService.findById(id).get(0));
            req.getRequestDispatcher("/views/minified/update_inventory_transaction.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "ID không tồn tại";
            ControllerUtilz.sendMessage(new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, message), res);
        }
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("isUpdatePage", false);
        req.getRequestDispatcher("/views/minified/update_inventory_transaction.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.inventoryTransactionValidator(node);
        InventoryTransactionModel inventoryTransaction = null;
        if (!responseMessage.isError()) {
            inventoryTransaction = mapper.treeToValue(node, InventoryTransactionModel.class);
            responseMessage = inventoryTransactionService.create(inventoryTransaction);
            inventoryTransaction = inventoryTransactionService.findById(inventoryTransaction.getId()).get(0);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, inventoryTransaction);
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
