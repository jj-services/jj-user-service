package austral.ingsis.jjuserservice.repository;

import austral.ingsis.jjuserservice.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowersRepository extends JpaRepository<Follower, Long> {
//    List<Follower> getFollowersForUser(Long id);

}
