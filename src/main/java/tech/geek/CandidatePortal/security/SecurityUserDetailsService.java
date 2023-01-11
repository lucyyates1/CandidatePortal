package tech.geek.CandidatePortal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.geek.CandidatePortal.entity.User;
import tech.geek.CandidatePortal.repo.UserRepo;

import java.util.Optional;

@Service
public class SecurityUserDetailsService implements UserDetailsService
{
    @Autowired
    UserRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        // load user from DB
        Optional<User> user = repository.findByUsername(username);
        // check if user exists
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
        // Create a MyUserDetails object with the User object's information and return it to be authenticated
        return user.map(SecurityUserDetails::new).get();
    }
}