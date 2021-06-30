package austral.ingsis.jjuserservice.repository;

import austral.ingsis.jjuserservice.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {
    Optional<UserDao> getUserById(Long id);
    Optional<UserDao> findByUsername(String username);
    List<UserDao> findByUsernamePattern(String username);

}
