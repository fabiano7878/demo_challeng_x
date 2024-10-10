package br.com.demo.person.config;

import br.com.demo.person.repository.UserRepository;
import br.com.demo.person.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recovery(request);
        if(token != null){
          var login = tokenService.validateToken(token);
          UserDetails users = userRepository.findByLogin(login);
          var authentication = new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recovery(HttpServletRequest request) {
        var authHeader = request.getHeader("Autorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
