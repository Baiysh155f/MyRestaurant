package myrestaurant.jwt;

import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import myrestaurant.jwt.services.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails()
                );
            }
        }

//                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
//                        "Invalid JWT token Bearer Header");
//            } else {
//                try {
//                    String email = jwtUtil.validateTokenAndRetrieveClaim(jwt);
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//
//                    UsernamePasswordAuthenticationToken
//                            authenticationToken = new
//                            UsernamePasswordAuthenticationToken(userDetails,
//                            userDetails.getPassword(),
//                            userDetails.getAuthorities());
//                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    }
//
//                } catch (JWTCreationException e) {
//                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token Bearer Header");
//        }
//    }
//}
        filterChain.doFilter(request, response);
    }
}
