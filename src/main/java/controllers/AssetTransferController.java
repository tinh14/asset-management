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
import models.AssetTransferModel;
import models.viewmodels.AssetWithQuantityModel;
import services.interfaces.AssetService;
import services.interfaces.AssetTransferService;
import services.interfaces.DepartmentService;
import services.interfaces.InventoryService;
import services.interfaces.LogService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;

/**
 *
 * @author ACE
 */
@WebServlet(name = "AssetTransferController", urlPatterns = {"/assetTransfer/*"})
public class AssetTransferController extends HttpServlet {

    @Inject
    private AssetTransferService assetTransferService;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private AssetService assetService;

    @Inject
    private LogService logService;

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // ex: ?action=...
        String action = req.getParameter("action");
        if (action != null) {
            String key = req.getParameter("key");
            switch (action) {
                case "searchAsset":
                    req.setAttribute("assetWithQuantityList", inventoryService.findAssetWithQuantity(assetService.findByName(key)));
                    break;
                case "searchDepartment":
                    req.setAttribute("departments", departmentService.findByName(key));
                    break;
                case "addItem":
                    List<AssetWithQuantityModel> list = inventoryService.findAssetWithQuantity(assetService.findById(Integer.valueOf(key)));
                    req.setAttribute("assetWithQuantityList", list);
                    break;
            }
            req.getRequestDispatcher("/views/minified/update_asset_transfer.jsp").forward(req, res);
            return;
        }

        // ex: /view
        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("assetTransferList", assetTransferService.findAll());
            req.setAttribute("departments", departmentService.findAll());
            req.getRequestDispatcher("/views/minified/asset_transfer.jsp").forward(req, res);
            return;
        }

        // ex: /view?id=1
        try {
            int id = Integer.parseInt(param);
            req.setAttribute("isUpdatePage", true);
            AssetTransferModel model = assetTransferService.findById(id).get(0);
            req.setAttribute("assetTransfer", model);
            req.setAttribute("quantities", assetTransferService.getQuantities(model.getAssetTransferDetailList()));
            req.getRequestDispatcher("/views/minified/update_asset_transfer.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "ID không tồn tại";
            ControllerUtilz.sendMessage(new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, message), res);
        }
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("isUpdatePage", false);
        req.getRequestDispatcher("/views/minified/update_asset_transfer.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.assetTransferValidator(node);
        AssetTransferModel assetTransfer = null;
        if (!responseMessage.isError()) {
            assetTransfer = mapper.treeToValue(node, AssetTransferModel.class);
            responseMessage = assetTransferService.create(assetTransfer);
            assetTransfer = assetTransferService.findById(assetTransfer.getId()).get(0);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, assetTransfer);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.assetTransferValidator(node);
        AssetTransferModel newAssetTransfer = null;
        AssetTransferModel oldAssetTransfer = null;
        if (!responseMessage.isError()) {
            newAssetTransfer = mapper.treeToValue(node, AssetTransferModel.class);
            oldAssetTransfer = assetTransferService.findById(newAssetTransfer.getId()).get(0);
            responseMessage = assetTransferService.update(newAssetTransfer);
            newAssetTransfer = assetTransferService.findById(newAssetTransfer.getId()).get(0);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, oldAssetTransfer, newAssetTransfer);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

}
