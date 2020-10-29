package ro.msg.learning.shop.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ro.msg.learning.shop.service.CustomerDetailsService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Profile("with-form")
public class FormBasedSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomerDetailsService customerDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("Using form based auth");
        httpSecurity.csrf()
                    .disable()
                    .headers()
                    .frameOptions()
                    .disable()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/**")
                    .authenticated()
                    .antMatchers("/**")
                    .permitAll()
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/auth/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureHandler((req, res, e) -> sendError(res, 401))
                    .successHandler((req, res, a) -> {
                        res.setContentType("application/json");
                        ObjectMapper objectMapper = new ObjectMapper();
                        res.getWriter()
                           .write(objectMapper.writeValueAsString(
                                   a.getPrincipal()));
                        res.setStatus(200);
                    })
                    .and()
                    .exceptionHandling()
                    .accessDeniedHandler((req, res, e) -> sendError(res, 403))
                    .authenticationEntryPoint(
                            (req, res, e) -> sendError(res, 401))
                    .and()
                    .logout()
                    .logoutUrl("/auth/logout")
                    .logoutSuccessHandler((req, res, a) -> res.setStatus(200));
    }

    private void sendError(HttpServletResponse response, int code) {
        response.setStatus(code);
        response.setHeader("Content-Type", "text/html");
        try (PrintWriter writer = response.getWriter()) {
            writer.print("<h1>Unable to process request!</h1>");
        } catch (IOException exception) {
            log.error("Unable to write login error to response.", exception);
        }
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(customerDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(
                new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
