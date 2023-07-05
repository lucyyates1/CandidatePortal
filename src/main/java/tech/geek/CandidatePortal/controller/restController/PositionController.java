package tech.geek.CandidatePortal.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.geek.CandidatePortal.entity.Position;
import tech.geek.CandidatePortal.services.ClientService;
import tech.geek.CandidatePortal.services.PositionService;
import tech.geek.CandidatePortal.services.UserGroupService;

@RestController
public class PositionController {
    @Autowired
    private PositionService positionService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserGroupService userGroupService;

    @PostMapping("/positions")
    public ResponseEntity<Position> createPosition(@RequestBody Position position){
        position.setClient(clientService.getClientById(1));
        position.setUserGroup(userGroupService.getUserGroupById(1));

        Position _position = positionService.savePosition(position);
        return new ResponseEntity<>(_position, HttpStatus.CREATED);
    }
}
