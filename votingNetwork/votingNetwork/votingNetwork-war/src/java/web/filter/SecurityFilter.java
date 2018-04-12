/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.filter;

import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lowru
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"}, servletNames = {"FacesServlet"})
public class SecurityFilter implements Filter {

    private static final String CONTEXT_ROOT = "/votingNetwork-war";

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig;

    public SecurityFilter() {
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SecurityFilter()");
        }
        StringBuffer sb = new StringBuffer("SecurityFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();

        if (!httpServletRequest.getRequestURI().startsWith(httpServletRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
            httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            httpServletResponse.setDateHeader("Expires", 0); // Proxies.
        }

        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");

        if (httpSession.getAttribute("voted") == null) {
            httpSession.setAttribute("voted", false);
        }
        Boolean voted = (Boolean) httpSession.getAttribute("voted");

        if (isLogin && requestServletPath.equals("/index.xhtml")) {
            System.err.println("true");
            httpServletResponse.sendRedirect(CONTEXT_ROOT + "/uniqueCode.xhtml");
        }

        if (!excludeLoginCheck(requestServletPath)) {
            if (isLogin) {
                if (voted) {
                    if(requestServletPath.equals("/done.xhtml")){
                        chain.doFilter(request, response);
                    }else{
                    httpServletResponse.sendRedirect(CONTEXT_ROOT + "/done.xhtml");
                    }
                    return;
                } else {
                    chain.doFilter(request, response);
                    return;
                }
            } else {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/index.xhtml");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    private Boolean excludeLoginCheck(String path) {
        //Ask KC for update
        if (path.startsWith("/javax.faces.resource")
                || path.startsWith("/images")
                || path.equals("/index.xhtml")) {
            return true;
        } else {
            return false;
        }
    }

}
