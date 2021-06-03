package austral.ingsis.jjuserservice.controller;

import austral.ingsis.jjuserservice.dto.CreateUserDto;
import austral.ingsis.jjuserservice.dto.UserDto;
import austral.ingsis.jjuserservice.model.UserDao;
import austral.ingsis.jjuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        UserDto userDto = this.userService.saveUser(this.mapDtoToModel(createUserDto));
        //TODO see how to do 500 response with try catch
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = this.userService.getAllUsers();
        //TODO response headers
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

//    @GetMapping(value = "/users/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id ) {
//        UserDto user = this.userService.getUserById();
//        //TODO response headers
//        //TODO add not found
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }



    private UserDao mapDtoToModel(CreateUserDto createUserDto) {
       UserDao user = new UserDao();
       user.setEmail(createUserDto.getEmail());
       user.setUsername(createUserDto.getUsername());
       user.setPassword(createUserDto.getPassword());
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        return user;
    }
}
