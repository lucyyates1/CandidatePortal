package tech.geek.CandidatePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.geek.CandidatePortal.entity.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> { }
