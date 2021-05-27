package austral.ingsis.jjuserservice.auth;

import austral.ingsis.jjuserservice.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private final String LOGIN_URL = "/v1/login";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(this.LOGIN_URL.equals(request.getServletPath()) && HttpMethod.POST.matches(request.getMethod())) {
            LoginDto loginDto = MAPPER.readValue(request.getInputStream(), LoginDto.class);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        }

        filterChain.doFilter(request, response);
    }
}
