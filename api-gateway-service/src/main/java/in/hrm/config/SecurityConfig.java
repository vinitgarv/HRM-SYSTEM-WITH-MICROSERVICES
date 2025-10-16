package in.hrm.config;

import in.hrm.handler.CustomAccessDeniedHandler;
import in.hrm.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerSecurityContextRepository securityContextRepository;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(ReactiveAuthenticationManager authenticationManager,
                          ServerSecurityContextRepository securityContextRepository, CustomAuthenticationEntryPoint authenticationEntryPoint,
                          CustomAccessDeniedHandler accessDeniedHandler) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers(
                                "/common/auth/login",
                                "/common/auth/refresh-token",
                                "/common/auth/change-password/**",
                                "/common/employeedetails/**",
                                "/common/message/**",
                                "/common/auth/forgot-password/**",
                                "/common/auth/reset-password/**",
                                "/common/upload/**",
                                "/common/auth/register/super-admin"
                              //  "/common/auth/register"
                        ).permitAll()

                        .pathMatchers("/common/auth/register")
                        .hasAnyAuthority("ROLE_ADMIN","ROLE_SUPER_ADMIN")

                        .pathMatchers("/common/user/log-in-users/**",
                                "/common/user/total-log-in-users/**",
                                "/common/user/logout/**",
                                "/hrops/employee-leave/**",
                                "/payroll/expense/**")
                        .hasAnyAuthority("ROLE_EMPLOYEE","ROLE_ADMIN","ROLE_SUPER_ADMIN")

                        .pathMatchers("/common/auth/logout",
                                "/common/user/token-response/**",
                                "/hrops/attendance/**",
                                "/hrops/asset/**",
                                "/hrops/time-sheet",
                                "/hrops/asset-allotment/**",
                                "/hrops/attendance-approval/**",
                                "/hrops/overtime/**",
                                "/hrops/holiday/**")
                        .hasAuthority("ROLE_ADMIN")
                        // everything else must be  v
                        .anyExchange().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .build();
    }
}
