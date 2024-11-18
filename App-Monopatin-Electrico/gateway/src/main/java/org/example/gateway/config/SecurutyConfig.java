package org.example.gateway.config;

import jakarta.servlet.http.HttpServletRequest;
import org.example.gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurutyConfig {
    private final TokenProvider tokenProvider;

    public SecurutyConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
//encriptamos la contraseña del usuario, evita que la guardemos en texto plano
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //revisar quien tiene autorizacion, es de mas generica a menos generica
    @Bean
    public SecurityFilterChain fiilterchain(final HttpSecurity http) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable);
        http.
                sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .securityMatcher("/**") //url que requieren autenticacion
                .authorizeHttpRequest(authz -> authz
                        .requestMatchers(HttpMethod.POST, "/authenticate").permitAll() //url que no requieren autenticacion, aca solo POST
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers("/monopatin/**").hasAuthority(AuthotityConstant._MANTENIMIENTO)//no importa el verbo
                        .requestMatchers("monopatin/**").hasAuthority(AuthotityConstant._ADMIN)
                        .anyRequest().authenticated() //los demas roles deben estar autenticados
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JwtFilter(this.tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
