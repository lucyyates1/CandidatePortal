package tech.geek.CandidatePortal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tech.geek.CandidatePortal.entity.Position;

@Repository
public interface PositionRepo extends JpaRepository<Position, Long> {

    List<Position> findByTemplate(Boolean template);

    @Query(value = "SELECT * FROM position WHERE template IS false ORDER BY date DESC LIMIT 3", nativeQuery = true)
    List<Position> getRecentPositions();

}
