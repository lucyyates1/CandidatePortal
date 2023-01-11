package tech.geek.CandidatePortal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    UserRepo repository;

    public User saveUser(User user){
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteUser(long id) {
        repository.deleteById(id);
    }
}
