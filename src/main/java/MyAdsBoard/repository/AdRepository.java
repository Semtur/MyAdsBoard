package MyAdsBoard.repository;

import MyAdsBoard.entity.Ad;
import MyAdsBoard.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    @Query("SELECT COUNT (a) FROM Ad a")
    Long countAds();
    @Query("SELECT a FROM Ad a WHERE a.rubric LIKE :rubric")
    List<Ad> findByRubric(@Param("rubric") String rubric);
    @Query("SELECT a FROM Ad a WHERE a.title LIKE :template")
    List<Ad> findByTitleTemplate(@Param("template") String template);
    @Query("SELECT a FROM Ad a WHERE a.userProfile = :userProfile")
    List<Ad> findByUser(@Param("userProfile") UserProfile userProfile);
    @Query("SELECT a FROM Ad a WHERE a.address LIKE :template")
    List<Ad> findByAddressTemplate(@Param("template") String template);
    @Query("SELECT a FROM Ad a WHERE (a.title LIKE :titleTemplate) AND (a.address LIKE :addressTemplate)")
    List<Ad> findByTitleAndAddressTemplate(@Param("titleTemplate") String titleTemplate,@Param("addressTemplate") String addressTemplate);
}
