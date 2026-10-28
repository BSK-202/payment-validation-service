package com.hulkhiretech.payment.security;

import java.util.Collections;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hulkhiretech.payment.service.interfaces.HmacSHA256Service;
import com.hulkhiretech.payment.util.JsonUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HmacFilter extends OncePerRequestFilter {

    private final HmacSHA256Service hmacSHA256Service;

    private final JsonUtil jsonUtil ;

    public HmacFilter(HmacSHA256Service hmacSHA256Service, JsonUtil jsonUtil) {
        this.hmacSHA256Service = hmacSHA256Service;
        this.jsonUtil = jsonUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws java.io.IOException, ServletException {

        WrappedRequest wrappedRequest = new WrappedRequest(request);

        log.info("Starting HMAC signature validation in HmacFilter for request: {} {}", wrappedRequest.getMethod(),
                wrappedRequest.getRequestURI());

        String incomingRequest = wrappedRequest.getHeader("HMAC-Signature");
        log.info("Incoming HMAC-Signature: {}", incomingRequest);

        log.info("hmacSHA256Service instance: {}", hmacSHA256Service);

        String requetBody = wrappedRequest.getBody();
        log.info("Request body as JSON: {}", requetBody);

        String reqAsJson = jsonUtil.prepareFormattedJson(requetBody);
        log.info("Formatted request body as JSON: {}", reqAsJson);

        hmacSHA256Service.verifySignature(reqAsJson, incomingRequest);

        // boolean isValid = (incomingRequest != null && !incomingRequest.isEmpty());
        // log.info("HMAC signature validation result: {}", isValid);// todo

        // if (!isValid) {
        // log.warn("HMAC signature validation failed. Access denied.");
        // throw new AccessDeniedException("Access denied");
        // }

        log.info("HMAC signature validation passed. Proceeding with the request.");

        setAuthenticationInSecurityContext(incomingRequest);

        filterChain.doFilter(wrappedRequest, response);

        log.info("Completed processing the request in HmacFilter.");

        return;
    }

    private void setAuthenticationInSecurityContext(String incomingRequest) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // Authentication authentication =
        // new TestingAuthenticationToken("username", "password", "ROLE_USER");

        HmacAuthenticationToken authentication = new HmacAuthenticationToken(
                "MERCHANT",
                null,
                Collections.emptyList());

        authentication.setDetails(incomingRequest);

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        log.info("Authentication set in SecurityContextHolder: {}", authentication);
    }

    

}
