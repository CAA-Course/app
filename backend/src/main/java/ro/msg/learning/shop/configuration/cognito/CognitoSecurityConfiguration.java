package ro.msg.learning.shop.configuration.cognito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("with-cognito")
public class CognitoSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AwsCognitoJwtAuthFilter awsCognitoJwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
            .cacheControl();
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/api/**")
            .authenticated()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(awsCognitoJwtAuthenticationFilter,
                             UsernamePasswordAuthenticationFilter.class);
    }
}
