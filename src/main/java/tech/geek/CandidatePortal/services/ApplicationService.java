package tech.geek.CandidatePortal.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.geek.CandidatePortal.entity.Application;
import tech.geek.CandidatePortal.entity.Position;
import tech.geek.CandidatePortal.entity.PositionCandidate;
import tech.geek.CandidatePortal.entity.PositionSkill;
import tech.geek.CandidatePortal.repo.ApplicationRepo;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepo repository;

    //When Implemented with the Needl AI engine, will need a new function that will send a post request to store the uploaded file.
    public Application saveApplication(Application application) { return repository.save(application);}

    //Not Implemented Yet
    public Application CreateCandidate(Position position, Application application)
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
        candidateDict.put("uniqueID", application.getApplication_id());
        candidateDict.put("resume_path", application.getResume_path());

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
        application.setEducation((String) tempCandidate.getJSONObject("candidate").get("education"));
        return repository.save(application);
    }

    public List<Application> saveApplications(List<Application> applications){
        return repository.saveAll(applications);
    }

    public List<Application> getAllApplications() {
        return repository.findAll();
    }

    public Application getApplicationById(long id) {
        return repository.findById(id).orElse(null);
    }

    //Build in function for calling API
    public int getScoreForCandidate(Position position, Application application) //Get all candidates for relevant position
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
        candidateDict.put("uniqueID", application.getApplication_id());
        candidateDict.put("resume_path", application.getResume_path());

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

    public List<Application> getAllFilledPositionCandidates() {
        return repository.getAllFilledPositionCandidates();
    }

    public List<Application> getApplicationsByPosition(Position position) {
        return repository.getApplicationsByPosition(position);
    }
}
