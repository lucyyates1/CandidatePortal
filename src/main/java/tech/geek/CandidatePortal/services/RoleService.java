package tech.geek.CandidatePortal.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.geek.CandidatePortal.entity.Role;
import tech.geek.CandidatePortal.repo.RoleRepo;

@Service
public class RoleService {
    @Autowired
    private RoleRepo repository;

    public Role saveRole(Role Role){
        return repository.save(Role);
    }

    public List<Role> saveRoles(List<Role> roles){
        return repository.saveAll(roles);
    }

    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public Role getUnauthorizedUser()
    {
        List<Role> tempRoles= new ArrayList<>();
        for(Role temp : tempRoles)
            if(temp.getRole() == "Unauthorized User")
                return temp;
        return null;
    }

    public Role getRoleById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteRole(long id) {
        repository.deleteById(id);
    }
}