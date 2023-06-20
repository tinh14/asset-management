/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

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
import javax.servlet.http.HttpSession;
import models.AccountModel;
import models.UserModel;
import utilz.ControllerUtilz;
import services.interfaces.AccountService;
import services.interfaces.UserService;

/**
 *
 * @author ACE
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Inject
    private AccountService accountService;

    @Inject
    private UserService userService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher("/views/minified/login.jsp").forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        AccountModel account = mapper.readValue(req.getInputStream(), AccountModel.class);

        List<AccountModel> accounts = accountService.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (accounts.isEmpty()) {
            int status = HttpServletResponse.SC_UNAUTHORIZED;
            String message = "Tên đăng nhập hoặc mật khẩu không chính xác";
            ControllerUtilz.sendMessage(new ResponseMessage(status, message), res);
            return;
        }

        if (!accounts.get(0).getStatus()) {
            int status = HttpServletResponse.SC_FORBIDDEN;
            String message = "Tài khoản đã bị khóa, vui lòng liên hệ quản trị viên";
            ControllerUtilz.sendMessage(new ResponseMessage(status, message), res);
            return;
        }

        List<UserModel> people = userService.findByAccountUsername(accounts.get(0).getUsername());
        HttpSession session = req.getSession();
        if (!people.isEmpty()) {
            session.setAttribute("sessionUser", people.get(0));
        }
        session.setAttribute("sessionAccount", accounts.get(0));
    }

}
