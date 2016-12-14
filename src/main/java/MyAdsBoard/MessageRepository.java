package MyAdsBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.ad = :ad")
    List<Message> findByAd(@Param("ad") Ad ad);
    @Query("SELECT m FROM Message m WHERE m.toEmail = :email")
    List<Message> findInMessagesByUser(@Param("email") String email);
    @Query("SELECT m FROM Message m WHERE m.fromEmail = :email")
    List<Message> findOutMessagesByUser(@Param("email") String email);
}
