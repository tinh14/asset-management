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
import services.interfaces.AccountingPeriodTypeService;

/**
 *
 * @author ACE
 */
@WebServlet(name = "AccountingPeriodTypeController", urlPatterns = {"/accountingPeriodTypes/*"})
public class AccountingPeriodTypeController extends HttpServlet {
    @Inject
    private AccountingPeriodTypeService accountingPeriodTypeService;

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("accountingPeriodTypes", accountingPeriodTypeService.findAll());
        req.getRequestDispatcher("/views/minified/accounting_period_types.jsp").forward(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String page = req.getPathInfo();
        if (page.equals("/view")) {
            handleViewPage(req, res);
        }
    }
}
