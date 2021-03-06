package com.jwt.jwtTest.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// Manage de token receive.
public class JwtValidationFilter extends BasicAuthenticationFilter {
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException 
    {
        String attribute = request.getHeader(HEADER);
        
        if (attribute == null || !attribute.startsWith(PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = attribute.replace(PREFIX, "");

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String user = JWT.require(Algorithm.HMAC512(CustomAutheticationFilter.TOKEN_SENHA)).build().verify(token).getSubject();

        if(user == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
}
