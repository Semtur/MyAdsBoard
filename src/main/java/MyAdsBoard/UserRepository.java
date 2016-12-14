package MyAdsBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
    @Query("SELECT u FROM UserProfile u where u.email = :email")
    UserProfile findByEmail(@Param("email") String email);
}
