package in.hrm.config;

import in.hrm.handler.CustomAccessDeniedHandler;
import in.hrm.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .pathMatchers(
                                "/common/auth/login",
                                "/common/auth/register",
                                "/common/auth/refresh-token",
                                "/common/auth/changePassword/**",
                                "/common/employeedetails/**",
                                "/common/message/**"
                        ).permitAll()


                        .pathMatchers("/common/user/getAllLogedInUsers/**",
                                "/common/user/countOfLogedInUsers/**",
                                "/common/user/logoutALoggedInUser/**")
                        .hasAnyAuthority("ROLE_USER","ROLE_ADMIN","ROLE_SUPER_ADMIN")

                        .pathMatchers("/common/auth/logout",
                                "/common/user/usertokenresponse/**",
                                "/hrops/attendance/**","/hrops/asset/**",
                                "/hrops/asset-allotment/**",
                                "/hrops/attendanceApproval/**",
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
