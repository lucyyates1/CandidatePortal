package tech.geek.CandidatePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.geek.CandidatePortal.entity.Application;

public interface ApplicationRepo extends JpaRepository<Application, Long> {
}
