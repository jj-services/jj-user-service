package austral.ingsis.jjuserservice.controller;

import austral.ingsis.jjuserservice.auth.JwtTokenUtil;
import austral.ingsis.jjuserservice.dto.ChatUserDto;
import austral.ingsis.jjuserservice.dto.FollowerDto;
import austral.ingsis.jjuserservice.dto.UserDto;
import austral.ingsis.jjuserservice.exception.FollowerNotFoundException;
import austral.ingsis.jjuserservice.exception.UserNotFoundException;
import austral.ingsis.jjuserservice.model.Follower;
import austral.ingsis.jjuserservice.service.FollowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController()
@RequestMapping("/api/followers")
public class FollowersController {

    private final FollowersService followersService;
    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();


    @Autowired
    public FollowersController(FollowersService followersService) {
        this.followersService = followersService;
    }

    @PostMapping(value = "")
    public ResponseEntity<FollowerDto> followUser(@RequestBody FollowerDto followerDto) {
        FollowerDto resultDto = this.followersService.followUser(this.mapDtoToModel(followerDto));
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

    //returns users followed by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<UserDto>> followedUsersForUser(@PathVariable Long id) {
        List<UserDto> followedUsersForUser = this.followersService.getFollowedUsersForUser(id);
        return new ResponseEntity<>(followedUsersForUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowerDto followerDto) {
        try {
            this.followersService.unfollowUser(this.mapDtoToModel(followerDto));
            return new ResponseEntity<>("User of id: " + followerDto.getUserId() +
                    "unfollowed user of id: " + followerDto.getFollowingId(), HttpStatus.OK);
        } catch (FollowerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/contacts")
    public ResponseEntity<ChatUserDto> getContactsForUser(@CookieValue("auth_by_cookie") @NotNull String cookie) {
        String username = this.jwtTokenUtil.getUsernameFromToken(cookie);

        try {
            ChatUserDto chatUserDto = this.followersService.getMeAndFollowedContacts(username);
            return new ResponseEntity<>(chatUserDto,HttpStatus.OK);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    private Follower mapDtoToModel(FollowerDto followerDto) {
        Follower follower = new Follower();
        follower.setUserId(followerDto.getUserId());
        follower.setFollowingId(followerDto.getFollowingId());
        return follower;
    }

}
