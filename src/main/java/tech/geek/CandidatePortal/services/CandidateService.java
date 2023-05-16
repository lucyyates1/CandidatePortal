package tech.geek.CandidatePortal.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.geek.CandidatePortal.entity.Candidate;
import tech.geek.CandidatePortal.entity.Position;
import tech.geek.CandidatePortal.entity.PositionCertification;
import tech.geek.CandidatePortal.entity.PositionSkill;
import tech.geek.CandidatePortal.repo.CandidateRepo;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepo repository;

    //When Implemented with the Needl AI engine, will need a new function that will send a post request to store the uploaded file.
    public Candidate saveCandidate(Candidate candidate) { return repository.save(candidate);}

    public Candidate CreateCandidate(Position position, Candidate candidate)
    {
        //Prep JSON object to send to AI app for data processing
        JSONObject candidateRequest = new JSONObject();
        candidateRequest.put("position_id",position.getPosition_id());
        candidateRequest.put("place_of_performance",position.getPlace_of_performance());
        //candidateRequest.put("place_of_performance_priority",position.getPlace_performance_priority());
        candidateRequest.put("education",position.getEducation());
        candidateRequest.put("ed_required",position.isEducation_required());
        candidateRequest.put("experience",position.getExperience_required());

        Dictionary skillDict = new Hashtable();
        Set<PositionSkill> skillSet = position.getPosition_skills();
        for(PositionSkill ps : skillSet)
            skillDict.put(ps.getSkill().getName(),0);
        candidateRequest.put("skills",skillDict);

        Dictionary certs = new Hashtable();
        /*for(PositionCertification pc : position.getPosition_certifications())
            certs.put(pc.getCertification().getName(),"0");
        candidateRequest.put("certifications",certs); */

        Dictionary candidateDict = new Hashtable();
        candidateDict.put("uniqueID",candidate.getCandidate_id());
        candidateDict.put("resume_path",candidate.getResume_path());

        candidateRequest.put("candidates",candidateDict);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(candidateRequest.toString(),headers);
        String candidateString = restTemplate.postForObject("http://127.0.0.1:5000/candidateCreation",request,String.class,"");

        //Convert processed data into usable application data
        //JSONObject expected = new JSONObject("{candidates:{certifications:[],education:'',skills:'',uniqueID:''}}");
        JSONObject tempCandidate = new JSONObject(candidateString);
        // TODO: Do skills and certifications - currently score reprocesses data, not necessary in future
        // TODO: candidate.skills not built yet
        //Check skills - if present link, if absent create and link
        //Check certifications - if present link, if absent create and link
        candidate.setEducation((String) tempCandidate.getJSONObject("candidate").get("education"));
        return repository.save(candidate);
    }

    public List<Candidate> saveCandidates(List<Candidate> candidates){
        return repository.saveAll(candidates);
    }

    public List<Candidate> getAllCandidates() {
        return repository.findAll();
    }

    public Candidate getCandidateById(long id) {
        return repository.findById(id).orElse(null);
    }

    //Build in function for calling API
    public int getScoreForCandidate(Position position, Candidate candidate) //Get all candidates for relevant position
    {
        JSONObject scoringObject = new JSONObject();
        scoringObject.put("position_id",position.getPosition_id());
        scoringObject.put("place_of_performance",position.getPlace_of_performance());
        //scoringObject.put("place_of_performance_priority",position.getPlace_performance_priority());
        scoringObject.put("education",position.getEducation());
        scoringObject.put("ed_required",position.isEducation_required());
        scoringObject.put("experience",position.getExperience_required());

        Dictionary skillDict = new Hashtable();
        Set<PositionSkill> skillSet = position.getPosition_skills();
        for(PositionSkill ps : skillSet)
            skillDict.put(ps.getSkill().getName(),0);
        scoringObject.put("skills",skillDict);

        Dictionary certs = new Hashtable();
        /*for(PositionCertification pc : position.getPosition_certifications())
            certs.put(pc.getCertification().getName(),"0");
        scoringObject.put("certifications",certs); */

        Dictionary candidateDict = new Hashtable();
        candidateDict.put("uniqueID",candidate.getCandidate_id());
        candidateDict.put("resume_path",candidate.getResume_path());

        scoringObject.put("candidates",candidateDict);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(scoringObject.toString(),headers);
        String score = restTemplate.postForObject("http://127.0.0.1:5000/score",request,String.class,"");
        JSONObject temp = new JSONObject(score);
        int scoreReturn = (int) temp.get("score");
        /*PositionCandidateService pcService = new PositionCandidateService();
        pcService.savePositionCandidate(pcService.getPositionCandidateSpecific(position,candidate));*/
        return scoreReturn;
    }

}
