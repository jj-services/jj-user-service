package austral.ingsis.jjuserservice.config;

import austral.ingsis.jjuserservice.auth.CookieAuthenticationFilter;
import austral.ingsis.jjuserservice.auth.UserAuthenticationEntryPoint;
import austral.ingsis.jjuserservice.auth.UsernamePasswordAuthFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new UsernamePasswordAuthFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().logout().deleteCookies(CookieAuthenticationFilter.COOKIE_NAME)
                .and().authorizeRequests()
                .antMatchers()
                .authenticated();
    }
}
