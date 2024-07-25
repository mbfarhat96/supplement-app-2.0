package com.mohsintech.supplement_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/*
    This JWT filter class is executed before the UsernamePasswordAuthenticationFilter. Its job is simple, just verify
    whether the JWT is valid. And if its valid, then store the user in a security context so validation doesn't
    occur everytime a user makes a POST/PUT request.

    In order to completely understand what's going on in this filter please look at the process below and visit the JWT
    Service class

    Process:
        User sends request with token --> JwtFilter called --> JwtFilter calls JwtService --> JwtService validates token
 */

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final ApplicationContext context;

    @Autowired
    public JwtFilter(JWTService jwtService, ApplicationContext context){
        this.jwtService = jwtService;
        this.context = context;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaders = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeaders != null && authHeaders.startsWith("Bearer ")){
            token = authHeaders.substring(7);
            username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);

                if (jwtService.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
