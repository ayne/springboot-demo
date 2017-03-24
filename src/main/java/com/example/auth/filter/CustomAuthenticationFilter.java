package com.example.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by charmanesantiago on 29/02/2016.
 */

public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private String secretKey;

    public CustomAuthenticationFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        response.addHeader("Access-Control-Allow-Origin", "*");

        //needed for AJAX which send OPTIONS first and expects a status ok before proceeding to actual call.
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            // CORS "pre-flight" request
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, X-Authorization");
            return;
        }

        //custom authorization header named X-Authorization. You can name this whatever you want
        String xAuth = request.getHeader("X-Authorization");

        //require each request to have X-Authorization
        if (xAuth == null || !xAuth.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        //the token we expect is a valid token generated from /login using JWT
        final String token = xAuth.substring(7); // The part after "Bearer "


        //validate that the token is a valid one by verifying claims (from JWT)
        //if the token is invalid it will throw JwtException
        try {
            final Claims claims = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
            request.setAttribute("claims", claims);
            SecurityContextHolder.getContext().setAuthentication(null);
        } catch (final SignatureException e) { //you can delete SignatureException since it's just a subclass or a type of JwtException
            throw new ServletException("Invalid token");
        } catch (final JwtException e) {
            throw new ServletException("Invalid token");
        }

        filterChain.doFilter(request, response);
    }

}