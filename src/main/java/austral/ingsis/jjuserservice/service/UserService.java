package austral.ingsis.jjuserservice.service;

import austral.ingsis.jjuserservice.dto.UserDto;
import austral.ingsis.jjuserservice.model.User;
import austral.ingsis.jjuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager; //might not be needed
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = encoder;

    }

    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        //TODO communicate with post service to append all posts to object
        return users.stream().map(User::toUserDto).collect(Collectors.toList());
    }

    public UserDto saveUser(User user) {
        return this.userRepository.save(user).toUserDto();
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    //update

    //login

}
