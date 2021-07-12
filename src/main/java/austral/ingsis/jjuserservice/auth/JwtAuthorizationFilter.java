package austral.ingsis.jjuserservice.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<String> tokenFromCookies = this.getTokenFromCookies(request.getCookies());

        if(tokenFromCookies.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authToken = this.parseToken(tokenFromCookies.get());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken parseToken(String token) {
        if (token != null) {
            Claims user = Jwts.parser()
                    .setSigningKey(SecurityConstants.KEY.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            } else {
                return null;
            }

        }
        return null;
    }

    private Optional<String> getTokenFromCookies(Cookie[] cookies) {
        if (cookies == null)
            return Optional.empty();
        return Arrays.stream(cookies)
                .filter(c -> SecurityConstants.COOKIE_NAME.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

}
