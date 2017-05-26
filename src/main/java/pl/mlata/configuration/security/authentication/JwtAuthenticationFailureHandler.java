package pl.mlata.configuration.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pl.mlata.exceptions.ErrorCode;
import pl.mlata.exceptions.ErrorResponse;
import pl.mlata.exceptions.JwtExpiredTokenException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;

    @Autowired
    public JwtAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse;


        if(e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password", e.getMessage(), ErrorCode.BAD_CREDENTIALS);
        }
        else if(e instanceof JwtExpiredTokenException) {
            errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Authorization token has expired.", e.getMessage(), ErrorCode.TOKEN_EXPIRED);
        }
        else {
            errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication error", e.getMessage(), ErrorCode.AUTHENTICATION);
        }

        mapper.writeValue(httpServletResponse.getWriter(), errorResponse);
    }
}
