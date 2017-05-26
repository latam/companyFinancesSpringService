package pl.mlata.aop.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import pl.mlata.global.ProfileConstants;

@Configuration
@EnableAspectJAutoProxy
public class AspectLoggerConfiguration {
    @Bean
    @Profile(ProfileConstants.Development)
    public AspectLogger aspectLogger(Environment env) {
        return new AspectLogger(env);
    }
}
