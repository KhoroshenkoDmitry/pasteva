package dev.asteri.pasteva.config;

import dev.asteri.pasteva.filter.JwtFilter;
import dev.asteri.pasteva.handler.CustomAccessDeniedHandler;
import dev.asteri.pasteva.handler.CustomLogoutHandler;
import dev.asteri.pasteva.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final UserService userService;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final CustomLogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.securityContext(context -> context
                .requireExplicitSave(false)
        );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/signin", "/signup", "/v1/notes/public/**",
                                    "/v1/users/public/**", "/refresh_token/**", "/", "/v1/users/update",
                                    "/error**", "/v3/**", "/swagger-ui/**", "/webjars/**")
                            .permitAll();
                    auth.requestMatchers(EndpointRequest.to("health", "info")).permitAll()
                            .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll();
                    auth.requestMatchers("/admin/**").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .userDetailsService(userService)
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(accessDeniedHandler);
                    e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .logout(log -> {
                    log.logoutUrl("/logout");
                    log.addLogoutHandler(logoutHandler);
                    log.logoutSuccessHandler((request, response, authentication) ->
                            SecurityContextHolder.clearContext());
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}