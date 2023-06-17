/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import utilz.ResponseMessage;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.interfaces.LogService;
import utilz.ControllerUtilz;

/**
 *
 * @author ACE
 */
@WebServlet(name = "LogController", urlPatterns = {"/log/*"})
public class LogController extends HttpServlet {

    @Inject
    private LogService logService;

    public void handleViewPage(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // ex: ?action=...
        String action = req.getParameter("action");
        if (action != null) {
            String key = req.getParameter("key");
            switch (action) {
                case "search":
                    req.setAttribute("logList", logService.findByRecordId(key));
                    break;
            }
            req.getRequestDispatcher("/views/minified/log.jsp").forward(req, res);
            return;
        }

        // ex: /view
        String param = req.getParameter("id");
        if (param == null) {
            req.setAttribute("logList", logService.findAll());
            req.getRequestDispatcher("/views/minified/log.jsp").forward(req, res);
            return;
        }

        // ex: /view?id=1
        try {
            int id = Integer.parseInt(param);
            req.setAttribute("isUpdatePage", true);
            req.setAttribute("log", logService.findByLogId(id).get(0));
            req.getRequestDispatcher("/views/minified/log_detail.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            String message = "ID không tồn tại";
            ControllerUtilz.sendMessage(new ResponseMessage(HttpServletResponse.SC_BAD_REQUEST, message), res);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String page = req.getPathInfo();
        if (page.equals("/view")) {
            handleViewPage(req, res);
        }
    }
}
