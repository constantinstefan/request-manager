package fii.request.manager.security.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;

    private CustomerDetailsService customerDetailsService;

    @Autowired
    JwtAuthenticationFilter(JwtTokenService jwtTokenService,
                            CustomerDetailsService customerDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null
                || (! authorizationHeader.startsWith("Bearer")) ) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String headerToken = authorizationHeader.substring(7);
            String subject = jwtTokenService.validateTokenAndRetrieveSubject(headerToken);
            UserDetails userDetails = customerDetailsService.loadUserByUsername(subject);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(token);
            }

            filterChain.doFilter(request, response);
        }
        catch(Exception e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
