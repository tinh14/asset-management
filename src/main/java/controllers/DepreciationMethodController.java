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
import services.interfaces.DepreciationMethodService;

/**
 *
 * @author ACE
 */
@WebServlet(name = "DepreciationMethodController", urlPatterns = {"/depreciationMethods/*"})
public class DepreciationMethodController extends HttpServlet {

    @Inject
    private DepreciationMethodService depreciationMethodService;
    
    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("depreciationMethods", depreciationMethodService.findAll());
        req.getRequestDispatcher("/views/minified/depreciation_methods.jsp").forward(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String page = req.getPathInfo();
        if (page.equals("/view")) {
            handleViewPage(req, res);
        }
    }
}
