package pl.mlata.configuration.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import pl.mlata.persistance.model.User;
import pl.mlata.service.UserService;

/**
 * Created by Mateusz on 18.05.2017.
 */

@Component
public class JwtLoginAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public JwtLoginAuthenticationProvider(BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "Authentication data cannot be empty.");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userService.getByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Username or Password not valid.");
        }

        if (StringUtils.isEmpty(user.getRoles()))
            throw new InsufficientAuthenticationException("User has no roles assigned.");

        return new UsernamePasswordAuthenticationToken(user.getUsername(), null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
