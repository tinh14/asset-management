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
import services.interfaces.InventoryService;

/**
 *
 * @author ACE
 */
@WebServlet(name = "InventoryController", urlPatterns = {"/inventory/*"})
public class InventoryController extends HttpServlet {

    @Inject
    private AssetService assetService;

    @Inject
    private InventoryService inventoryService;

    public void handleSearch(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String stringKey = req.getParameter("key");
        req.setAttribute("assetWithQuantityList", inventoryService.findAssetWithQuantity(assetService.findByName(stringKey)));
        req.getRequestDispatcher("/views/minified/inventory.jsp").forward(req, res);
    }

    public void handleListPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (req.getParameter("action") != null) {
            handleSearch(req, res);
            return;
        }
        req.setAttribute("assetWithQuantityList", inventoryService.findAssetWithQuantity(assetService.findAll()));
        req.getRequestDispatcher("/views/minified/inventory.jsp").forward(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String page = req.getPathInfo();
        if (page.equals("/view")) {
            handleListPage(req, res);
        }
    }
}
