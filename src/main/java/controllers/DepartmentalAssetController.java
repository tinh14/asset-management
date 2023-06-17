/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.interfaces.AssetService;
import services.interfaces.AssetTypeService;
import services.interfaces.DepartmentService;
import services.interfaces.InventoryService;

/**
 *
 * @author ACE
 */
@WebServlet(name = "DepartmentalAssetController", urlPatterns = {"/departmentalAssets/*"})
public class DepartmentalAssetController extends HttpServlet {

    @Inject
    private DepartmentService departmentService;

    @Inject
    private AssetService assetService;

    @Inject
    private AssetTypeService assetTypeService;

    @Inject
    private InventoryService inventoryService;

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
            }
            req.getRequestDispatcher("/views/minified/departmental_assets.jsp").forward(req, res);
            return;
        }

        req.setAttribute("departments", departmentService.findAll());
        req.setAttribute("assetTypes", assetTypeService.findAll());

        // ex: /view
        String key = req.getParameter("key");
        if (key != null) {
            int id = Integer.valueOf(key);
            req.setAttribute("assetWithQuantityList", inventoryService.findDepartmentalAssetWithQuantity(id, assetService.findByDepartmentId(id)));
        }
        req.getRequestDispatcher("/views/minified/departmental_assets.jsp").forward(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String page = req.getPathInfo();
        if (page.equals("/view")) {
            handleViewPage(req, res);
        }
    }

}
