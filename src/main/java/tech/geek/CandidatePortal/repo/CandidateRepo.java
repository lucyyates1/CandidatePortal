package tech.geek.CandidatePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.geek.CandidatePortal.entity.Candidate;

public interface CandidateRepo extends JpaRepository<Candidate, Long> {
}
