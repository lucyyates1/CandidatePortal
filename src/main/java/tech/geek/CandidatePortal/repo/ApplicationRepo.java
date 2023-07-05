package tech.geek.CandidatePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.geek.CandidatePortal.entity.Application;
import tech.geek.CandidatePortal.entity.Position;
import tech.geek.CandidatePortal.entity.PositionCandidate;

import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application, Long> {

    @Query(value = "SELECT * FROM application WHERE filled_date IS NOT NULL", nativeQuery = true)
    List<Application> getAllFilledPositionCandidates();

    List<Application> getApplicationsByPosition(Position position);
}
