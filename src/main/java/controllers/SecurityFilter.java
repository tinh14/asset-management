/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import utilz.ResponseMessage;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.AccountModel;
import models.PersonModel;
import services.interfaces.AuthorizationService;
import utilz.ControllerUtilz;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    @Inject
    private AuthorizationService authorizationService;

    public boolean checkAuthentication(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        PersonModel person = (PersonModel) session.getAttribute("sessionPerson");
        return person != null;
    }

    public boolean checkAuthorization(HttpServletRequest req, HttpServletResponse res, String uriWithoutContextPath) throws IOException, ServletException {
        String[] arr = uriWithoutContextPath.split("/");
        String resource = arr[1].replaceAll("([A-Z])", "_$1");
        
        // handle dashboard page and logout action
        if (resource.equals("dashboard") || resource.equals("logout")) {
            return true;
        }

        String method = req.getMethod();
        String action = null;

        switch (method) {
            case "POST":
                action = "ADD";
                break;
            case "PUT":
                action = "EDIT";
                break;
            case "DELETE":
                action = "DELETE";
                break;
            default:
                action = "VIEW";
                break;
        }
        HttpSession session = req.getSession();
        AccountModel account = (AccountModel) session.getAttribute("sessionAccount");
        return authorizationService.checkPermission(account.getUsername(), resource, action);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String requestURI = req.getRequestURI();
        String uriWithoutContextPath = requestURI.substring(contextPath.length());

        // handle login url
        if (uriWithoutContextPath.equals("/") || uriWithoutContextPath.equals("/login") || requestURI.contains("/templates" )) {
            chain.doFilter(req, res);
            return;
        }
        
        if (!checkAuthentication(req, res)) {
            res.sendRedirect(contextPath + "/login");
            return;
        }

        if (!checkAuthorization(req, res, uriWithoutContextPath)) {
            int status = HttpServletResponse.SC_FORBIDDEN;
            String message = "Bạn không có quyền truy cập vào tài nguyên này";
            ControllerUtilz.sendMessage(new ResponseMessage(status, message), res);
            return;
        }

        String[] arr = uriWithoutContextPath.split("/");
        req.setAttribute("resourceId", arr[1].replaceAll("([A-Z])", "_$1"));
        String method = req.getMethod();
        String actionId = null;
        switch (method) {
            case "POST":
                actionId = "ADD";
                break;
            case "PUT":
                actionId = "EDIT";
                break;
            case "DELETE":
                actionId = "DELETE";
                break;
            default:
                break;
        }
        req.setAttribute("actionId", actionId);
//        req.getRequestDispatcher("/LogController").forward(req, res);
        chain.doFilter(req, res);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
