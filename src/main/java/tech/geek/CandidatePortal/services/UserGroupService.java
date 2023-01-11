package tech.geek.CandidatePortal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.geek.CandidatePortal.entity.UserGroup;
import tech.geek.CandidatePortal.repo.UserGroupRepo;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupRepo repository;

    public UserGroup saveUserGroup(UserGroup userGroup){
        return repository.save(userGroup);
    }

    public List<UserGroup> saveUserGroups(List<UserGroup> userGroups){
        return repository.saveAll(userGroups);
    }

    public List<UserGroup> getAllUserGroup() {
        return repository.findAll();
    }

    public UserGroup getUserGroupById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteUserGroup(long id) {
        repository.deleteById(id);
    }
}