package pl.mlata.configuration.security.authentication;

import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import pl.mlata.service.TokenAuthenticationService;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public JwtAuthenticationProvider(TokenAuthenticationService tokenAuthenticationService) {
        Assert.notNull(tokenAuthenticationService);
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtTokenAuthentication userPreAuth = (JwtTokenAuthentication) authentication;
        Authentication userAuth = tokenAuthenticationService.getAuthentication((String) userPreAuth.getCredentials());
        return userAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtTokenAuthentication.class.isAssignableFrom(authentication));
    }
}
