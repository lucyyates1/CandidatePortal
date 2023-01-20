package tech.geek.CandidatePortal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.groove.geeksi.entity.Candidate;
import tech.groove.geeksi.entity.Position;
import tech.groove.geeksi.repo.PositionRepo;
import tech.groove.geeksi.repo.SkillRepo;

@Service
public class PositionService {
    @Autowired
    private PositionRepo repository;

    @Autowired
    private SkillRepo skillrepository;

    public Position savePosition(Position position){
        return repository.save(position);
    }

    public List<Position> savePositions(List<Position> positions){
        return repository.saveAll(positions);
    }

    public List<Position> getAllPositions() { return repository.findAll(); }

    public List<Position> getRecentPositions() { return repository.getRecentPositions(); }

    public Position getPositionById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Position> getAllTemplatePositions()
    {
        return repository.findByTemplate(Boolean.TRUE);
    }

    public List<Position> getAllNonTemplatePositions() { return repository.findByTemplate(Boolean.FALSE); }

    public List<Position> getAllPositionsAndSkills()
    {
        List<Position> positions = repository.findByTemplate(Boolean.TRUE);

        return repository.findByTemplate(Boolean.TRUE);
    }

    public Position findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletePosition(long id) {
        repository.deleteById(id);
    }
}
