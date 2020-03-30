package com.spider.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author
 */
@Slf4j
public class SessionFilter implements Filter {
    private String excludedPages;

    private String[] excludedPageArray;

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        return;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.info("doFilter");
        boolean isExcludedPage = false;
        for (String page : excludedPageArray) {
            if (((HttpServletRequest) request).getServletPath().equals(page)) {
                isExcludedPage = true;
                break;
            }
        }
        if (isExcludedPage) {
            chain.doFilter(request, response);
        } else {//
            HttpSession session = ((HttpServletRequest) request).getSession();
            if (session == null || session.getAttribute("") == null) {
                ((HttpServletResponse) response).sendRedirect("login.html");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        excludedPages = fConfig.getInitParameter("excludedPages");
        if (null != excludedPages && excludedPages.length() > 0) {
            excludedPageArray = excludedPages.split(",");
        }
        return;
    }
}
