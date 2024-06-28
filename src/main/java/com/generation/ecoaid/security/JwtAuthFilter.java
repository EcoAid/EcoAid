package com.generation.ecoaid.security;

import org.springframework.stereotype.Component;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//Indica que esta classe é um componente gerenciado pelo Spring
public class JwtAuthFilter extends OncePerRequestFilter {

	//Injeção das dependencias do userDetailsService.
    @Autowired
    private JwtService jwtService;

    //Injeção das dependencias do userDetailsService.
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //Metodo para filtrar o header colocado no Insomnia para ficar entendivel para o sistema.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Pega os dados no header do Insomnia.
    	String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try{
            // Verifica se o header contém um token JWT válido
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }
            // Verifica se o nome de usuário foi extraído e se o contexto de segurança não está autenticado
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Valida o token JWT
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Define o contexto de segurança do Spring com o token de autenticação
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
            // Continua a cadeia de filtros
            filterChain.doFilter(request, response);

        }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException 
                | SignatureException | ResponseStatusException e){
            // Define o status da resposta HTTP como FORBIDDEN em caso de exceções relacionadas ao JWT
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
    }
}

