package tech.geek.CandidatePortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.geek.CandidatePortal.entity.Candidate;
import tech.geek.CandidatePortal.entity.Position;
import tech.geek.CandidatePortal.entity.PositionCandidate;
import tech.geek.CandidatePortal.repo.PositionCandidateRepo;

import java.util.List;

@Service
public class PositionCandidateService {

    @Autowired
    PositionCandidateRepo repository;

    public PositionCandidate savePositionCandidate(PositionCandidate positionCandidate){
        return repository.save(positionCandidate);
    }

    public List<PositionCandidate> savePositionCandidates(List<PositionCandidate> positionCandidates){
        return repository.saveAll(positionCandidates);
    }

    public List<PositionCandidate> getAllPositionCandidates() {
        return repository.findAll();
    }

    //public List<PositionCandidate> getPositionCandidatesByPosition(Position position) { return repository.getPositionCandidatesByPosition(position); }

    public PositionCandidate getPositionCandidateSpecific(Position position, Candidate candidate)
    {
        List<PositionCandidate> PosCans = repository.findAll();
        PositionCandidate posCan = new PositionCandidate();
        for(PositionCandidate pc : PosCans)
            if(pc.getCandidate().getCandidate_id() == candidate.getCandidate_id())
                if(pc.getPosition().getPosition_id() == position.getPosition_id())
                    posCan = pc;
        return posCan;
    }

    public PositionCandidate getPositionCandidateById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<PositionCandidate> getAllFilledPositionCandidates() { return repository.getAllFilledPositionCandidates(); }

    public void deletePositionCandidate(long id) {
        repository.deleteById(id);
    }
}
