package udemx.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.logging.Logger;

@Configuration
public class AdminConfig {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    public UserDetailsService userDetailsService() {

        Logger logger = Logger.getLogger(AdminConfig.class.getName());
        logger.info("Admin username: {}" + adminUsername);
        logger.info("Admin password: {}"+ adminPassword);
        UserDetails admin = User.builder()
                .username(adminUsername)
                .password("{noop}" + adminPassword) // "{noop}" no encrypt
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}
