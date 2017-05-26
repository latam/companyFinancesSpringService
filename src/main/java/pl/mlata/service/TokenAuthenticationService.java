package pl.mlata.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.mlata.configuration.security.authentication.JwtSettings;
import pl.mlata.configuration.security.authentication.JwtTokenAuthentication;
import pl.mlata.exceptions.JwtExpiredTokenException;
import pl.mlata.persistance.model.User;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TokenAuthenticationService {
    private JwtSettings jwtSettings;
    private UserService userService;

    @Autowired
    public TokenAuthenticationService(JwtSettings jwtSettings, UserService userService) {
        Assert.notNull(jwtSettings);
        Assert.notNull(userService);
        this.jwtSettings = jwtSettings;
        this.userService = userService;
    }

    public void addAuthentication(HttpServletResponse response, String username) {

        if (StringUtils.isBlank(username))
            throw new IllegalArgumentException("Username cannot be empty.");

        Date currentTime = Date.from(
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        Date expirationDate = Date.from(
                LocalDateTime.now().plusMinutes(jwtSettings.getExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant());


        String jwtBody = Jwts.builder()
                .setSubject(username)
                .setIssuer(jwtSettings.getIssuer())
                .setIssuedAt(currentTime)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getSigningKey())
                .compact();

        String tokenHeader = jwtSettings.getPrefix() + jwtBody;
        response.addHeader(jwtSettings.getHeader(), tokenHeader);
    }

    public Authentication getAuthentication(String token) throws AuthenticationException {
        String username;
        try {
            username = Jwts.parser()
                    .setSigningKey(jwtSettings.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
        catch (JwtException e) {
            if(e instanceof ExpiredJwtException) {
                throw new JwtExpiredTokenException(e.getMessage(), token);
            }
            throw e;
        }

        if (username == null && StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Username is empty.");
        }

        User user = userService.getByUsername(username);

        List<GrantedAuthority> authorities = Stream.of(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        return new JwtTokenAuthentication(authorities, user);
    }
}
