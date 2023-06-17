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
import models.AssetModel;
import services.interfaces.AssetService;
import services.interfaces.AssetTypeService;
import services.interfaces.LogService;
import utilz.ControllerUtilz;
import utilz.JsonMapperValidator;

/**
 *
 * @author ACE
 */
@WebServlet(name = "AssetController", urlPatterns = {"/assets/*"})
public class AssetController extends HttpServlet {

    @Inject
    private AssetService assetService;

    @Inject
    private AssetTypeService assetTypeService;

    @Inject
    private LogService logService;

    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        req.setAttribute("assets", assetService.findByName(stringKey));
        req.getRequestDispatcher("/views/minified/assets.jsp").forward(req, res);
    }

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // ex: action=search
        String action = req.getParameter("action");
        if (action != null) {
            String key = req.getParameter("key");
            switch (action) {
                case "search":
                    handleSearch(req, res);
                    return;
                case "searchAssetType":
                    req.setAttribute("assetTypes", assetTypeService.findByName(key));
                    break;
            }
            req.setAttribute("assetTypes", assetTypeService.findByName(key));
            req.getRequestDispatcher("/views/minified/update_asset.jsp").forward(req, res);
            return;
        }

        // ex: /view
        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("assets", assetService.findAll());
            req.setAttribute("assetTypes", assetTypeService.findAll());
            req.getRequestDispatcher("/views/minified/assets.jsp").forward(req, res);
            return;
        }

        // ex: /view?id=1
        try {
            int id = Integer.parseInt(param);
            req.setAttribute("isUpdatePage", true);
            req.setAttribute("asset", assetService.findById(id).get(0));
            req.getRequestDispatcher("/views/minified/update_asset.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "ID không tồn tại";
            ControllerUtilz.sendMessage(new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, message), res);
        }
    }

    public void handleAddPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("assetTypeList", assetTypeService.findAll());
        req.setAttribute("isUpdatePage", false);
        req.getRequestDispatcher("/views/minified/update_asset.jsp").forward(req, res);
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
        ResponseMessage responseMessage = JsonMapperValidator.assetValidator(node);
        AssetModel newAsset = null;
        if (!responseMessage.isError()) {
            newAsset = mapper.treeToValue(node, AssetModel.class);
            responseMessage = assetService.create(newAsset);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, null, newAsset);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(req.getInputStream());
        ResponseMessage responseMessage = JsonMapperValidator.assetValidator(node);
        AssetModel newAsset = null;
        AssetModel oldAsset = null;
        
        if (!responseMessage.isError()) {
            newAsset = mapper.treeToValue(node, AssetModel.class);
            oldAsset = assetService.findById(newAsset.getId()).get(0);
            responseMessage = assetService.update(newAsset);
        }

        if (!responseMessage.isError()) {
            responseMessage = logService.create(req, oldAsset, newAsset);
        }

        ControllerUtilz.sendMessage(responseMessage, res);
    }
}
