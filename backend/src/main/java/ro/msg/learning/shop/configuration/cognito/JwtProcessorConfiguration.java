package ro.msg.learning.shop.configuration.cognito;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

import static com.nimbusds.jose.JWSAlgorithm.RS256;

@Configuration
public class JwtProcessorConfiguration {
    @Bean
    public ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor()
            throws MalformedURLException {
        ResourceRetriever resourceRetriever = new DefaultResourceRetriever(2000,
                                                                           2000);
        URL jwkURL = new URL(
                "https://cognito-idp.eu-west-1.amazonaws.com/eu-west-1_LBgyq455E/.well-known/jwks.json");
        JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(jwkURL,
                                                                  resourceRetriever);
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(
                RS256, keySource);
        jwtProcessor.setJWSKeySelector(keySelector);
        return jwtProcessor;
    }
}
