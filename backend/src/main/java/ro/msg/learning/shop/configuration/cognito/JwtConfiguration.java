package ro.msg.learning.shop.configuration.cognito;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "ro.msg.learning.shop.aws")
public class JwtConfiguration {
    private String userPoolId;
    private String identityPoolId;
    private String jwkUrl;
    private String region = "eu-west-1";
    private String userNameField = "cognito:email";
    private int connectionTimeout = 2000;
    private int readTimeout = 2000;
    private String httpHeader = "Authorization";

    public String getJwkUrl() {
        return this.jwkUrl != null && !this.jwkUrl.isEmpty() ? this.jwkUrl : String.format(
                "https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json",
                this.region, this.userPoolId);
    }

    public String getCognitoIdentityPoolUrl() {
        return String.format("https://cognito-idp.%s.amazonaws.com/%s",
                             this.region, this.userPoolId);
    }
}
