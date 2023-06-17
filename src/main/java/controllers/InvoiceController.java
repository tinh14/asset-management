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
import models.InvoiceModel;
import services.interfaces.InvoiceService;
import services.interfaces.LogService;
import services.interfaces.SupplierService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;
import utilz.ResponseMessage;

/**
 *
 * @author ACE
 */
@WebServlet(name = "InvoiceController", urlPatterns = {"/invoices/*"})
public class InvoiceController extends HttpServlet {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private SupplierService supplierService;
    
    @Inject
    private LogService logService;
    
    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        req.setAttribute("invoices", invoiceService.findByIdWithWildcard(stringKey));
        req.getRequestDispatcher("/views/minified/invoices.jsp").forward(req, res);
    }

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String action = req.getParameter("action");
        if (action != null) {
            String key = req.getParameter("key");
            switch (action) {
                case "search":
                    handleSearch(req, res);
                    return;
                case "searchSupplier":
                    req.setAttribute("suppliers", supplierService.findByName(key));
                    break;
            }
            req.getRequestDispatcher("/views/minified/update_invoice.jsp").forward(req, res);
            return;
        }

        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("invoices", invoiceService.findAll());
            req.setAttribute("suppliers", supplierService.findAll());
            req.getRequestDispatcher("/views/minified/invoices.jsp").forward(req, res);
            return;
        }

        req.setAttribute("isUpdatePage", true);
        req.setAttribute("invoice", invoiceService.findById(param).get(0));
        req.getRequestDispatcher("/views/minified/update_invoice.jsp").forward(req, res);
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("isUpdatePage", false);
        req.getRequestDispatcher("/views/minified/update_invoice.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.invoiceValidator(node);
        InvoiceModel invoice = null;
        if (!responseMessage.isError()) {
            invoice = mapper.treeToValue(node, InvoiceModel.class);
            responseMessage = invoiceService.create(invoice);
        }
        
        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, invoice);
        }
        
        ControllerUtilz.sendMessage(responseMessage, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.invoiceValidator(node);
        InvoiceModel newInvoice = null;
        InvoiceModel oldInvoice = null;
        if (!responseMessage.isError()) {
            newInvoice = mapper.treeToValue(node, InvoiceModel.class);
            oldInvoice = invoiceService.findById(newInvoice.getId()).get(0);
            responseMessage = invoiceService.update(newInvoice);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, oldInvoice, newInvoice);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

}
