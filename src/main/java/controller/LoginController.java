//package controller;
//
//import java.io.IOException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import services.LoginService;
//
//
//@WebServlet(name = "LoginController", urlPatterns = "/login")
//public class LoginController extends HttpServlet {
//    private LoginService loginService = new LoginService();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String email = "";
//        String password = "";
//
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("ckEmail".equals(cookie.getName())) {
//                    email = URLDecoder.decode(cookie.getValue(), "UTF-8");
//                }
//                if ("ckPassword".equals(cookie.getName())) {
//                    password = URLDecoder.decode(cookie.getValue(), "UTF-8");
//                }
//            }
//        }
//
//        req.setAttribute("email", email);
//        req.setAttribute("password", password);
//        req.getRequestDispatcher("login.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//        String remember = req.getParameter("remember");
//
//        boolean isSuccess = loginService.checkLogin(email, password, remember, resp, req);
//
//        if (!isSuccess) {
//            req.setAttribute("errorMessage", "Thông tin đăng nhập không chính xác.");
//            req.getRequestDispatcher("login.jsp").forward(req, resp);
//            return;
//        }
//
//        if ("on".equals(remember)) {
//            Cookie emailCookie = new Cookie("ckEmail", URLEncoder.encode(email, "UTF-8"));
//            Cookie passwordCookie = new Cookie("ckPassword", URLEncoder.encode(password, "UTF-8"));
//            resp.addCookie(emailCookie);
//            resp.addCookie(passwordCookie);
//        }
//
//        resp.sendRedirect(req.getContextPath() + "/users");
//    }
//}
//


package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.LoginService;

@SuppressWarnings("serial")
@WebServlet(name = "LoginController", urlPatterns = { "/login", "/logout" })
public class LoginController extends HttpServlet {

	private LoginService loginService = new LoginService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();

		if (requestURI.endsWith("/logout")) {
			loginService.logout(req, resp); // Đăng xuất và xóa cookie
			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			req.getRequestDispatcher("login.jsp").forward(req, resp); // Hiển thị trang đăng nhập
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		boolean isSuccess = loginService.checkLogin(email, password, remember, req, resp);
		if (isSuccess) {
			resp.sendRedirect(req.getContextPath() + "/index"); // Chuyển đến trang chính
		} else {
			req.setAttribute("errorMessage", "Invalid email or password!");
			req.getRequestDispatcher("login.jsp").forward(req, resp); // Quay lại trang đăng nhập
		}
	}
}

