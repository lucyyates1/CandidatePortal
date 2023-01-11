package tech.geek.CandidatePortal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.geek.CandidatePortal.entity.UserGroup;

@Repository
public interface UserGroupRepo extends JpaRepository<UserGroup,Long> { }