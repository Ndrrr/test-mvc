package athletetrainingprogram.repository;

import athletetrainingprogram.model.Athlete;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    @Query("select a from Athlete a where lower(a.name) like lower(:name)")
    List<Athlete> findAllByName(@Param("name") String name, Pageable pageable);

}
