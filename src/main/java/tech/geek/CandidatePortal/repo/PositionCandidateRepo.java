package tech.geek.CandidatePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.geek.CandidatePortal.entity.PositionCandidate;

import java.util.List;

public interface PositionCandidateRepo extends JpaRepository<PositionCandidate, Long> {


    @Query(value = "SELECT * FROM position_candidate WHERE filled_date IS NOT NULL", nativeQuery = true)
    List<PositionCandidate> getAllFilledPositionCandidates();
}
