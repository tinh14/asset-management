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
import models.AssetDisposeModel;
import services.interfaces.AssetDisposeService;
import services.interfaces.AssetService;
import services.interfaces.DepartmentService;
import services.interfaces.InventoryService;
import services.interfaces.LogService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;

/**
 *
 * @author ACE
 */
@WebServlet(name = "AssetDisposeController", urlPatterns = {"/assetDispose/*"})
public class AssetDisposeController extends HttpServlet {

    @Inject
    private AssetDisposeService assetDisposeService;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private AssetService assetService;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private LogService logService;
    
    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // ex: ?action=...
        String action = req.getParameter("action");
        if (action != null) {
            String key = req.getParameter("key");
            switch (action) {
                case "searchAsset":
                    int departmentId = Integer.valueOf(req.getParameter("departmentId"));
                    req.setAttribute("assetWithQuantityList", inventoryService.findDepartmentalAssetWithQuantity(departmentId, assetService.findByAssetNameAndDepartmentId(key, departmentId)));
                    break;
                case "searchDepartment":
                    req.setAttribute("departments", departmentService.findByName(key));
                    break;
                case "addItem":
                    int departmentIdd = Integer.valueOf(req.getParameter("departmentId"));
                    req.setAttribute("assetWithQuantityList", inventoryService.findDepartmentalAssetWithQuantity(departmentIdd, assetService.findById(Integer.valueOf(key))));
                    break;
            }
            req.getRequestDispatcher("/views/minified/update_asset_dispose.jsp").forward(req, res);
            return;
        }

        req.setAttribute("departments", departmentService.findAll());

        // ex: /view
        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("assetDisposeList", assetDisposeService.findAll());
            req.getRequestDispatcher("/views/minified/asset_dispose.jsp").forward(req, res);
            return;
        }

        // ex: /view?id=1
        try {
            int id = Integer.parseInt(param);
            req.setAttribute("isUpdatePage", true);
            AssetDisposeModel model = assetDisposeService.findById(id).get(0);
            req.setAttribute("assetDispose", model);
            req.setAttribute("quantities", assetDisposeService.getQuantities(model.getDisposalDepartment().getId(), model.getAssetDisposeDetailList()));
            req.getRequestDispatcher("/views/minified/update_asset_dispose.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "ID không tồn tại";
            ControllerUtilz.sendMessage(new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, message), res);
        }
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("isUpdatePage", false);
        req.getRequestDispatcher("/views/minified/update_asset_dispose.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.assetDisposeValidator(node);
        AssetDisposeModel assetDispose = null;
        if (!responseMessage.isError()) {
            assetDispose = mapper.treeToValue(node, AssetDisposeModel.class);
            responseMessage = assetDisposeService.create(assetDispose);
            assetDispose = assetDisposeService.findById(assetDispose.getId()).get(0);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, assetDispose);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.assetDisposeValidator(node);
        AssetDisposeModel newAssetTransfer = null;
        AssetDisposeModel oldAssetTransfer = null;
        if (!responseMessage.isError()) {
            newAssetTransfer = mapper.treeToValue(node, AssetDisposeModel.class);
            oldAssetTransfer = assetDisposeService.findById(newAssetTransfer.getId()).get(0);
            responseMessage = assetDisposeService.update(newAssetTransfer);
            newAssetTransfer = assetDisposeService.findById(newAssetTransfer.getId()).get(0);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, oldAssetTransfer, newAssetTransfer);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

}
