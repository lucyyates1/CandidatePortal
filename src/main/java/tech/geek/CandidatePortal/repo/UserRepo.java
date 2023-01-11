package tech.geek.CandidatePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.geek.CandidatePortal.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long>
{
    Optional<User> findByUsername(String username);
}
