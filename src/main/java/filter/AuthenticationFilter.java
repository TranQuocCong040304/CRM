////package filter;
////
////import java.io.IOException;
////
////import javax.servlet.FilterChain;
////import javax.servlet.ServletException;
////import javax.servlet.annotation.WebFilter;
////import javax.servlet.http.Cookie;
////import javax.servlet.http.HttpFilter;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////
//////urlPatterns: đường dẫn sẽ kích hoạt filter
////@WebFilter(filterName = "authenFilter", urlPatterns = {"/*"})
////public class AuthenticationFilter extends HttpFilter {
////	
////	
////	
////	
////	
////	@Override
////	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
////			throws IOException, ServletException {
////			
////		System.out.println("Filter actived");
////		
////		
////		String email = "";
////		String password = "";
////		Cookie[] cookie = req.getCookies();
////		for(Cookie item : cookie) {
////			String name = item.getName();
////			String value = item.getValue();
////			if(name.equals("email")) {
////				email = value;
////			}
////			if(name.equals("password")) {
////				password = value;
////			}
////		}
////		req.setAttribute("email", email);
////		req.setAttribute("password", password);
////		
////		req.getRequestDispatcher("login.jsp").forward(req, res);
////		
////		//cho đi tiếp
////		chain.doFilter(req, res);
////		req.getRequestDispatcher("/users").forward(req, res);
//////		if(email.equals("nguyenvana@gmail.com") && password.equals("123456")) {
//////			//cho đi tiếp
//////			chain.doFilter(req, res);	
//////			req.getRequestDispatcher("/users").forward(req, res);
//////			
//////		}
////		
////		//cho đi tiếp
////		chain.doFilter(req, res);
////		
////	}
////}
//
//
//package filter;
//
//import java.io.IOException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Cookie;
//
//@WebFilter(filterName = "authenFilter", urlPatterns = { "/users" })
//public class AuthenticationFilter extends HttpFilter {
//
//	/*
//	 * *
//	 * khi người dùng gọi link / users
//	 * b1: Ktra xem người dùng đã đăng nhặp hay chưa. Nếu đã ĐN thì cho thấy nội dung còn chưa đăng nhập đá ra login(cookies)
//	 * b2: Đối với link/ users chỉ có user có quyền là admin thì mới vô đc còn không thì về trang dashboard
//	 * (cookies)
//	 */
//	
//    @Override
//    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
//            throws IOException, ServletException {
//
//        boolean isAuthenticated = false;
//        Cookie[] cookies = req.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {       
//                if ("authen".equals(cookie.getName()) && "ROLE_ADMIN".equals(cookie.getValue())) {
//                    isAuthenticated = true; 
//                    break;
//                }
//            }
//        }
//
//        if (!isAuthenticated) {     
//            res.sendRedirect(req.getContextPath() + "/login");
//        } else {
//            chain.doFilter(req, res);
//        }
//    }
//}
//
//
//


package filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

@WebFilter(filterName = "authenFilter", urlPatterns = { "/users" })
public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        boolean isAuthenticated = false;
        Cookie[] cookies = req.getCookies();

        // Kiểm tra cookie nếu người dùng đã đăng nhập và có quyền ADMIN
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authen".equals(cookie.getName()) && "ROLE_ADMIN".equals(cookie.getValue())) {
                    isAuthenticated = true;
                    break;
                }
            }
        }

        // Nếu chưa đăng nhập hoặc không phải là ADMIN, chuyển hướng về trang login
        if (!isAuthenticated) {
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            // Nếu đã xác thực, cho phép tiếp tục truy cập
            chain.doFilter(req, res);
        }
    }
}

