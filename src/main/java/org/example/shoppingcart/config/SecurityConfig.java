package org.example.shoppingcart.config;

import org.example.shoppingcart.model.Role;
import org.example.shoppingcart.model.User;
import org.example.shoppingcart.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import java.util.Set;

/**
 * Security configuration.
 *
 * <p>User accounts and their roles are stored in the configured database.</p>
 *
 * @author EL GOUAL MOUAD
 * @version 1.0.0
 * @since 2026-10-06
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner seedDefaultUsers(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(User.builder()
                        .username("admin")
                        .password(encoder.encode("admin"))
                        .roles(Set.of(Role.ADMIN, Role.USER))
                        .build());
            }

            if (!userRepository.existsByUsername("user")) {
                userRepository.save(User.builder()
                        .username("user")
                        .password(encoder.encode("user"))
                        .roles(Set.of(Role.USER))
                        .build());
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        http
                .authorizeHttpRequests(auth -> auth
                        // Public static resources
                        .requestMatchers("/", "/index", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                        // Authentication endpoints
                        .requestMatchers("/login", "/logout","/error").permitAll()

                        // Cart is public (session-scoped) but Additiom is not. u need to be logged
                        .requestMatchers("/cart").permitAll()

                        // H2 console (dev only)
                        .requestMatchers(PathRequest.toH2Console()).authenticated()

                        // Public read-only product catalogue
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/search").permitAll()

                        // Admin area
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Product management: ADMIN only
                        .requestMatchers("/products/add", "/products/edit/**",
                                "/products/save", "/products/delete/**").hasRole("ADMIN")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((req, res, ex2) ->
                                res.sendRedirect(req.getContextPath() + "/products?accessDenied=true"))
                )
                .requestCache(cach -> cach.requestCache(requestCache))
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}