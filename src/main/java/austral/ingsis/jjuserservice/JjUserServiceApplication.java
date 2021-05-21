package austral.ingsis.jjuserservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;


@SpringBootApplication
public class JjUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjUserServiceApplication.class, args);
    }

// might not be needed
//    @EnableConfigurationProperties
//    @EnableWebSecurity
//    @Configuration
//    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//        @Autowired
//        private JwtAuthorizationFilter jwtAuthorizationFilter;
//
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.csrf().disable()
////                    .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
//                    .authorizeRequests()
//                    .antMatchers(POST, "/login").permitAll()
//                    .anyRequest().hasAnyAuthority();
//        }
//    }

}
