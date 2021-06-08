package austral.ingsis.jjuserservice.service;

import austral.ingsis.jjuserservice.dto.FollowerDto;
import austral.ingsis.jjuserservice.dto.UserDto;
import austral.ingsis.jjuserservice.model.Follower;
import austral.ingsis.jjuserservice.repository.FollowersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowersService {

    private final FollowersRepository followersRepository;
    private final UserService userService;


    public FollowersService(FollowersRepository followersRepository, UserService userService) {
        this.followersRepository = followersRepository;
        this.userService = userService;
    }

    public FollowerDto followUser(Follower follower) {
        return this.followersRepository.save(follower).toFollowerDto();
    }

    public List<UserDto> getFollowedUsersForUser(Long id) {
       return this.followersRepository.findAll().stream()
                .filter(follower -> follower.getUserId().equals(id))
                .map(follower -> this.userService.getUserById(follower.getUserId()))
                .collect(Collectors.toList());
    }
}
