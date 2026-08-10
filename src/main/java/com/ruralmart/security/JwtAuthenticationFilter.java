package com.ruralmart.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        // FIX: extractUsername()/isTokenValid() call into the JJWT parser,
        // which throws (ExpiredJwtException, MalformedJwtException,
        // SignatureException, etc - all JwtException) for any bad token.
        // This filter runs on EVERY request, including permitAll ones like
        // /api/auth/register - so an expired/garbage token previously
        // crashed the whole filter chain with an uncaught exception before
        // Spring Security ever got to evaluate whether the endpoint even
        // requires auth, surfacing as a raw 401/403/500 on public endpoints.
        // Catching it here and just skipping authentication is the correct
        // behavior: permitAll endpoints proceed normally, and protected
        // endpoints correctly fall through to Spring Security's normal
        // "not authenticated" handling instead of an unhandled crash.
        try {
            String email = jwtService.extractUsername(jwt);

            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }
        } catch (JwtException | IllegalArgumentException | org.springframework.security.core.userdetails.UsernameNotFoundException ex) {
            // Invalid/expired/malformed token, or a token for a user that
            // no longer exists - treat as unauthenticated rather than
            // crashing the request. Protected endpoints will still
            // correctly reject with 401 since no Authentication was set.
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

}