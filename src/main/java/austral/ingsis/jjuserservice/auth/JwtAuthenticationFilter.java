package austral.ingsis.jjuserservice.auth;

import austral.ingsis.jjuserservice.dto.LoginDto;
import austral.ingsis.jjuserservice.model.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto dto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(),
                            dto.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        Date exp = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
        Claims claims = Jwts.claims().setSubject(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername());
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SecurityConstants.KEY.getBytes()).setExpiration(exp).compact();

        Cookie cookie = new Cookie(SecurityConstants.COOKIE_NAME, token);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true); add when https is used
        cookie.setMaxAge(SecurityConstants.EXPIRATION_TIME.intValue());
        cookie.setPath("/");
        response.addCookie(cookie);

//        response.getWriter().write(token); needed??
        response.getWriter().flush();
        response.getWriter().close();
    }
}
