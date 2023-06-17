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
import models.SupplierModel;
import services.interfaces.LogService;
import services.interfaces.SupplierService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;

/**
 *
 * @author ACE
 */
@WebServlet(name = "SupplierController", urlPatterns = {"/suppliers/*"})
public class SupplierController extends HttpServlet {

    @Inject
    private SupplierService supplierService;

    @Inject
    private LogService logService;
    
    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        req.setAttribute("suppliers", supplierService.findByName(stringKey));
        req.getRequestDispatcher("/views/minified/suppliers.jsp").forward(req, res);
    }

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        if (req.getParameter("action") != null) {
            handleSearch(req, res);
            return;
        }

        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("suppliers", supplierService.findAll());
            req.getRequestDispatcher("/views/minified/suppliers.jsp").forward(req, res);
            return;
        }

        try {
            int id = Integer.parseInt(param);
            req.setAttribute("isUpdatePage", true);
            req.setAttribute("supplier", supplierService.findById(id).get(0));
            req.getRequestDispatcher("/views/minified/update_supplier.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "ID không tồn tại";
            ControllerUtilz.sendMessage(new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, message), res);
        }
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("isUpdatePage", false);
        req.getRequestDispatcher("/views/minified/update_supplier.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.supplierValidator(node);
        SupplierModel newSupplier = null;
        if (!responseMessage.isError()) {
            newSupplier = mapper.treeToValue(node, SupplierModel.class);
            responseMessage = supplierService.create(newSupplier);
        }
        
        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, newSupplier);
        }
        
        ControllerUtilz.sendMessage(responseMessage, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.supplierValidator(node);
        SupplierModel newSupplier = null;
        SupplierModel oldSupplier = null;
        if (!responseMessage.isError()) {
            newSupplier = mapper.treeToValue(node, SupplierModel.class);
            oldSupplier = supplierService.findById(newSupplier.getId()).get(0);
            responseMessage = supplierService.update(newSupplier);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, oldSupplier, newSupplier);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }
}
