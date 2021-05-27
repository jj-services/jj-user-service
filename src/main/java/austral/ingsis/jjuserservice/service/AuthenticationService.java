package austral.ingsis.jjuserservice.service;

import austral.ingsis.jjuserservice.dto.LoginDto;
import austral.ingsis.jjuserservice.dto.UserDto;
import austral.ingsis.jjuserservice.model.User;
import austral.ingsis.jjuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

@Service
public class AuthenticationService {

    @Value("${auth.cookie.hmac-key:secret-key}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Transactional
    public UserDto authenticate(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).get();

          //TODO optional handling
          if (passwordEncoder.matches(CharBuffer.wrap(loginDto.getPassword()), user.getPassword())) {
              return new UserDto(user.getId(),
                      user.getFirstName(),
                      user.getLastName(),
                      user.getUsername());
          }

          //TODO take this out
        throw new RuntimeException("Invalid password");
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername());
    }


    public String createToken(UserDto user) {
        return user.getId() + "&" + user.getLogin() + "&" + calculateHmac(user);
    }

    public UserDto findByToken(String token){
        String[] parts =  token.split("&");
        Long userId = Long.valueOf(parts[0]);
        String username = parts[1];
        String hmac = parts[2];
        UserDto userDto = findByUsername(username);
        if(!hmac.equals(calculateHmac(userDto)) || !userId.equals(userDto.getId())) {
            throw new RuntimeException("Invalid cookie value");
        }

        return userDto;
    }

    private String calculateHmac(UserDto user) {
        byte[] secretKeyBytes = Objects.requireNonNull(secretKey).getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = Objects.requireNonNull(user.getId() + "&" + user.getLogin()).getBytes(StandardCharsets.UTF_8);

        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(hmacBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}
