package tech.geek.CandidatePortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.geek.CandidatePortal.entity.Candidate;
import tech.geek.CandidatePortal.repo.CandidateRepo;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepo repo;


    public List<Candidate> getAllCandidates() {
        return repo.findAll();
    }
}
