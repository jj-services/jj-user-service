package austral.ingsis.jjuserservice.service;

import austral.ingsis.jjuserservice.dto.UpdateUserDto;
import austral.ingsis.jjuserservice.dto.UserDto;
import austral.ingsis.jjuserservice.exception.UserNotFoundException;
import austral.ingsis.jjuserservice.model.UserDao;
import austral.ingsis.jjuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager; //might not be needed
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = encoder;

    }

    public List<UserDto> getAllUsers() {
        List<UserDao> users = this.userRepository.findAll();
        //TODO communicate with post service to append all posts to object
        return users.stream().map(UserDao::toUserDto).collect(Collectors.toList());
    }

    public UserDto saveUser(UserDao user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user).toUserDto();
    }

    public void deleteUser(Long id) {
        Optional<UserDao> user = this.userRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User of id: " + id  + "not found.");
        }
        this.userRepository.deleteById(id);
    }

    public UserDto updateUser(UpdateUserDto dto) {
        Optional<UserDao> user = this.userRepository.findById(dto.getId());

        if(user.isEmpty()) {
          throw new UserNotFoundException("User of id: " + dto.getId() + "not found.");
        }

        UserDao toSave = user.get();

        toSave.setEmail(dto.getEmail());
        toSave.setFirstName(dto.getFirstName());
        toSave.setLastName(dto.getLastName());

        if(dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            toSave.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }else {
            toSave.setPassword(user.get().getPassword());
        }

        return this.userRepository.save(toSave).toUserDto();
    }

    public UserDto getUserById(Long id) {

        Optional<UserDao> optUser = this.userRepository.findById(id);

       if(optUser.isEmpty()) {
           throw new UserNotFoundException("User of id: " + id.toString() + "not found.");
       }

       return optUser.get().toUserDto();
    }

    public UserDto getUserByUsername(String username) {
        UserDao user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
      return user.toUserDto();
    }
}
