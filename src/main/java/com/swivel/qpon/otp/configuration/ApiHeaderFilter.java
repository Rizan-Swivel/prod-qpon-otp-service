package com.swivel.qpon.otp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ApiHeaderFilter
 */
@WebFilter(urlPatterns = "/api/*")
public class ApiHeaderFilter extends GenericFilterBean {

    private static final String APPLICATION_HEADER = "app-key";
    private final String applicationKey;

    public ApiHeaderFilter(@Value("${spring.application.app-key}") String applicationKey) {
        this.applicationKey = applicationKey;
    }

    /**
     * Validate the mandatory field app-key
     *
     * @param servletRequest  servletRequest
     * @param servletResponse servletResponse
     * @param filterChain     filterChain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String appKey = request.getHeader(APPLICATION_HEADER);

        if (appKey == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing app key");
        }
        if (appKey != null && !appKey.equals(applicationKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid app key");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
