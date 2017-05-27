package pl.mlata.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import pl.mlata.configuration.security.authentication.JwtAuthenticationProvider;
import pl.mlata.configuration.security.authentication.JwtLoginAuthenticationProvider;
import pl.mlata.configuration.security.authentication.JwtLoginFilter;
import pl.mlata.configuration.security.authentication.JwtTokenAuthenticationProcessingFilter;
import pl.mlata.configuration.security.authentication.extractor.TokenExtractor;
import pl.mlata.service.TokenAuthenticationService;
import pl.mlata.service.UserService;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private JwtLoginAuthenticationProvider jwtLoginAuthenticationProvider;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private TokenExtractor tokenExtractor;
    @Autowired
    private CorsFilter corsFilter;

    private final String loginEntryPoint = "/auth/login";
    private final String registrationEntryPoint = "/auth/registration";

    protected JwtLoginFilter buildJwtLoginFilter() throws Exception {
        JwtLoginFilter filter = new JwtLoginFilter(loginEntryPoint, this.authenticationManager(), failureHandler, tokenAuthenticationService);
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(loginEntryPoint, registrationEntryPoint);
        SkipRequestPathMatcher matcher = new SkipRequestPathMatcher(pathsToSkip, "/**");
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(matcher, failureHandler, tokenExtractor);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.authenticationProvider(jwtLoginAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(loginEntryPoint, registrationEntryPoint, "/h2-console/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and()
                .addFilterBefore(corsFilter, JwtLoginFilter.class)
                .addFilterBefore(
                        buildJwtLoginFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        buildJwtAuthenticationProcessingFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }
}
