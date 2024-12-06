//package controller;
//
//import java.io.IOException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.Iterator;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet(name = "DemoCookieController", urlPatterns = "/demo-cookie")
//public class DemoCookieController extends HttpServlet {
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////
////		Cookie demoCk = new Cookie("demo", URLEncoder.encode("Hello Cookie","UTF-8"));
////		resp.addCookie(demoCk);
//
//		Cookie[] cookies = req.getCookies();
//		String valueCookieDemo = "";
//
//		for (Cookie item : cookies) {
//			String name = item.getName();
//			String value = URLDecoder.decode(item.getValue(), "UTF-8");
//			if (name.equals("demo")) {
//				valueCookieDemo = value;
//			}
//		}
//		System.out.println("demo" + valueCookieDemo);
//	}
//}

package controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DemoCookieController", urlPatterns = "/demo-cookie")
public class DemoCookieController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra cookie và lấy giá trị
        Cookie[] cookies = req.getCookies();
        String valueCookieDemo = "Không tìm thấy cookie demo";

        if (cookies != null) {
            for (Cookie item : cookies) {
                String name = item.getName();
                String value = URLDecoder.decode(item.getValue(), "UTF-8");
                if (name.equals("demo")) {
                    valueCookieDemo = value;
                    break;
                }
            }
        }

        // Phản hồi lại client
        resp.setContentType("text/html");
        resp.getWriter().println("<h3>Giá trị cookie 'demo': " + valueCookieDemo + "</h3>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Thiết lập cookie mới
        String demoValue = "Hello Cookie";
        Cookie demoCk = new Cookie("demo", URLEncoder.encode(demoValue, "UTF-8"));
        demoCk.setMaxAge(60 * 60); // Cookie tồn tại trong 1 giờ
        resp.addCookie(demoCk);

        // Phản hồi lại client
        resp.setContentType("text/jsp");
        resp.getWriter().println("<h3>Cookie 'demo' đã được thiết lập với giá trị: " + demoValue + "</h3>");
    }
}

