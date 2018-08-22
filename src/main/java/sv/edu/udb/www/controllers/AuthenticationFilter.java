/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leonardo
 */
// urlPatterns = {"/*"}
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/company/*", "/employee/*", "/client/*", "/admin/*", "/login.jsp", "/register.jsp"})
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession _s = req.getSession(true);

        if ( _s.getAttribute("logged") == null) {   // checking whether the session exists
            this.context.log("Unauthorized access request");
            _s.invalidate();
            
            String[] actualView = req.getRequestURI().split("/");
            
            if(Arrays.asList(actualView).contains("company") || Arrays.asList(actualView).contains("admin")
               || Arrays.asList(actualView).contains("client") || Arrays.asList(actualView).contains("employee")){
                res.sendRedirect(req.getContextPath() + "/login.jsp");
            }else{
                chain.doFilter(request, response);
            }

            /*boolean flag = false;

            if(!req.getRequestURI().equals(req.getContextPath() + "/login.jsp")){
                flag = true;
            }
            
            String url = req.getRequestURL().toString();
            String queryString = req.getQueryString();
            
            if (queryString == null) queryString = "";
            if (url == null) url = "";
            
            if (queryString.contains("op=confirmation") && url.contains("user.do") && queryString.contains("id") ) {
                flag = false;
            }
            
            if(flag){
                res.sendRedirect(req.getContextPath() + "/login.jsp");
            }else{
                chain.doFilter(request, response);
            }*/
        } else {
//            this.context.log("Yei");
            String[] actualView = req.getRequestURI().split("/");

            if ((Boolean) _s.getAttribute("logged")) {
                if (!Arrays.asList(actualView).contains(_s.getAttribute("type").toString())) {
                    res.sendRedirect("/Cuponera/" + _s.getAttribute("type").toString() + "/index.jsp");
                } else {
                    chain.doFilter(request, response);
                }
            } 
        }
    }// Fin doFilter()

    @Override
    public void destroy() {

    }
}
