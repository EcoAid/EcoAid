package com.generation.ecoaid.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//Ativa a configuração de segurança web no Spring
public class BasicSecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter; // Injeta o filtro de autenticação JWT

    @Bean
    /**
     * Define um bean que retorna uma implementação de UserDetailsService.
     * 
     * @return uma instância de UserDetailsServiceImpl.
     */
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    /**
     * Define um bean que retorna um codificador de senhas BCrypt.
     * 
     * @return uma instância de BCryptPasswordEncoder.
     */
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    /**
     * Define um bean que retorna um provedor de autenticação.
     * 
     * @return uma instância de DaoAuthenticationProvider configurada.
     */
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    /**
     * Define um bean que retorna um gerenciador de autenticação.
     * 
     * @param authenticationConfiguration a configuração de autenticação do Spring.
     * @return uma instância de AuthenticationManager.
     * @throws Exception em caso de erro ao obter o gerenciador de autenticação.
     */
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .sessionManagement(management -> management
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .csrf(csrf -> csrf.disable())
                    .cors(withDefaults());

        http
            .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/usuarios/logar").permitAll() // Permite acesso sem autenticação ao endpoint de login
                    .requestMatchers("/usuarios/cadastrar").permitAll() // Permite acesso sem autenticação ao endpoint de cadastro
                    //Permitir os gets do endpoint de produtos
                    .requestMatchers(HttpMethod.GET, "/produto").permitAll()
                  //Permitir todos os gets do endpoint de produtos
                    .requestMatchers(HttpMethod.GET, "/produto/**").permitAll() // Permite acesso sem autenticação a todos os GETs do endpoint de produto
                    .requestMatchers("/error/**").permitAll() // Permite acesso sem autenticação ao endpoint de erro
                    .requestMatchers(HttpMethod.OPTIONS).permitAll() // Permite acesso sem autenticação aos métodos OPTIONS
                    .anyRequest().authenticated()) // Requer autenticação para qualquer outra requisição
            .authenticationProvider(authenticationProvider()) // Define o provedor de autenticação
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro de autenticação JWT antes do filtro padrão de autenticação por nome de usuário e senha
            .httpBasic(withDefaults()); // Habilita autenticação HTTP básica com configurações padrão

        return http.build();

    }

}

