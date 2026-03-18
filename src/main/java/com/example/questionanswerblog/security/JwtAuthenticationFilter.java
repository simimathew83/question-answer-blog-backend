package com.example.questionanswerblog.security;

import com.example.questionanswerblog.model.User;
import com.example.questionanswerblog.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Spring manages this filter
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, @Lazy AuthService authService){ // <- @Lazy breaks circular reference
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if((authHeader!=null) && (authHeader.startsWith("Bearer "))){
            String token = authHeader.substring(7);
            String userEmail;

            try{
                // Extract username/email from token
                userEmail = jwtUtil.extractSubject(token);

                // Proceed only if user is not already authenticated
                if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null){

                    //System.out.println("Useremail: " + userEmail);
                    User user = authService.findByEmail(userEmail);

                    // Validate the token
                    if(jwtUtil.validateToken(token,user.getEmail())){
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(user.getEmail(),null,null);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        System.out.println("Authentication set: " +
                                SecurityContextHolder.getContext().getAuthentication());
                    }else {
                        // Token is invalid
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Invalid JWT token\"}");
                        return;
                    }

                }
            }catch(ExpiredJwtException ex){
                // Token is expired
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Jwt token expired\"}");
                return;
            }catch(JwtException ex){
                // Any other JWT parsing/validation exception
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Jwt token invalid\"}");
                return;
            }catch(RuntimeException ex){
                // User not found or other runtime exception
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
                return;
            }
        }

        // Continue with next filter in the chain
        filterChain.doFilter(request,response);

    }
}
