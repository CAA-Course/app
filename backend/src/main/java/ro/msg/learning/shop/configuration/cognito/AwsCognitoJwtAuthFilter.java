package ro.msg.learning.shop.configuration.cognito;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class AwsCognitoJwtAuthFilter extends GenericFilter {

    private final transient AwsCognitoIdTokenProcessor cognitoIdTokenProcessor;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        Authentication authentication;
        try {
            authentication = this.cognitoIdTokenProcessor.authenticate(
                    (HttpServletRequest) request);
            if (authentication != null) {
                SecurityContextHolder.getContext()
                                     .setAuthentication(authentication);
            }
        } catch (Exception var6) {
            log.error("Cognito ID Token processing error", var6);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
