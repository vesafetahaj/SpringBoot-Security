package com.vesa.securityTeddy.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*This class is probably an implementation of Spring Security's AuthenticationEntryPoint interface. It defines what
happens when an unauthenticated user attempts to access a resource that requires authentication. For instance,
instead of redirecting to a login page (as in form-based authentication), this entry point might return a 401 Unauthorized
response, possibly with a message indicating that authentication is required.*/

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
