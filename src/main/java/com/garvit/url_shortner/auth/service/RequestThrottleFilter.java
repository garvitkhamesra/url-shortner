package com.garvit.url_shortner.auth.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by dot on 25/11/19 1:18 AM
 * Package: com.garvit.url_shortner.auth.service
 */

@Slf4j
@Component
public class RequestThrottleFilter implements Filter {

    @Value("${request.allowed}")
    private int MAX_REQUESTS_PER_SECOND;

    private LoadingCache<String, Integer> requestCountsPerIpAddress;

    public RequestThrottleFilter(){
        super();
        requestCountsPerIpAddress = CacheBuilder.newBuilder().
                expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        log.info("request {}", httpServletRequest.getHeader("Authorization"));
        log.info("request {}", httpServletRequest.getHeader("authorization"));
        String clientIpAddress = getClientIP((HttpServletRequest) servletRequest);
        if(isMaximumRequestsPerSecondExceeded(clientIpAddress))
            httpServletResponse.sendError(429,"Too many requests");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isMaximumRequestsPerSecondExceeded(String clientIpAddress){
        int requests = 0;
        try {
            requests = requestCountsPerIpAddress.get(clientIpAddress);
            if(requests > MAX_REQUESTS_PER_SECOND)
                return true;
        } catch (ExecutionException e) {
            requests = 0;
        }
        requests++;
        requestCountsPerIpAddress.put(clientIpAddress, requests);
        return false;
    }

    public String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public void destroy() { }
}