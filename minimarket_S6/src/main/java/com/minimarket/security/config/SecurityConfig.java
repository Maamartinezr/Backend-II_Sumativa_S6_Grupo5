package com.minimarket.security.config;

import com.minimarket.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.minimarket.util.MinimarketConstants.ROL_ADMIN;
import static com.minimarket.util.MinimarketConstants.ROL_CAJERO;
import static com.minimarket.util.MinimarketConstants.ROL_EMPLEADO;
import static com.minimarket.util.MinimarketConstants.ROL_VENDEDOR;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF con la nueva sintaxis
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Permitir acceso pÃºblico
                        .requestMatchers(HttpMethod.POST, "/api/productos/**").hasAuthority(ROL_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAuthority(ROL_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAuthority(ROL_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/inventario/**").hasAnyAuthority(ROL_ADMIN, ROL_CAJERO, ROL_EMPLEADO, ROL_VENDEDOR)
                        .requestMatchers(HttpMethod.PUT, "/api/inventario/**").hasAnyAuthority(ROL_ADMIN, ROL_CAJERO, ROL_EMPLEADO, ROL_VENDEDOR)
                        .requestMatchers(HttpMethod.DELETE, "/api/inventario/**").hasAnyAuthority(ROL_ADMIN, ROL_CAJERO, ROL_EMPLEADO, ROL_VENDEDOR)
                        .requestMatchers(HttpMethod.POST, "/api/ventas/**").hasAnyAuthority(ROL_CAJERO, ROL_EMPLEADO, ROL_VENDEDOR)
                        .anyRequest().authenticated() // Requiere autenticaciÃ³n para el resto
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/public/hola", true) // Redirigir despuÃ©s del login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/public/hola")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ConfiguraciÃ³n de encriptaciÃ³n de contraseÃ±as
    }
}
