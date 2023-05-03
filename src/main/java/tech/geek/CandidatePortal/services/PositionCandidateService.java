package tech.geek.CandidatePortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.geek.CandidatePortal.entity.PositionCandidate;
import tech.geek.CandidatePortal.repo.PositionCandidateRepo;

import java.util.List;

@Service
public class PositionCandidateService {

    @Autowired
    PositionCandidateRepo repo;


    public List<PositionCandidate> getAllFilledPositionCandidates() {
        return repo.getAllFilledPositionCandidates();
    }
}
