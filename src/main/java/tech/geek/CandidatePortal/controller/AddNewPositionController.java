package tech.geek.CandidatePortal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import tech.geek.CandidatePortal.controller.controllerHelper.SQLDateEditor;
import tech.geek.CandidatePortal.entity.Position;
import tech.geek.CandidatePortal.services.PositionService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AddNewPositionController {

    @Autowired
    private PositionService positionService;

    @InitBinder
    public void dateFormatter(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new SQLDateEditor());
    }

    @GetMapping("/addPosition")
    public String addPosition(Model model) {
        // Get all template positions
        List<Position> templatePositionsList = positionService.getAllTemplatePositions();
        model.addAttribute("templatePositionsList", templatePositionsList);

        // Serialize the positions into JSON
        List<String> templatePositionsListJSON = serializePositions(templatePositionsList);

        model.addAttribute("templatePositionsListJSON", templatePositionsListJSON);
        Position position = new Position();
        model.addAttribute("newposition", position);

        return "add_new_position";
    }

    // Helper function that serializes the position data
    private List<String> serializePositions(List<Position> templatePositionsList){
        // Setting up Jackson Object mapper for JSON serialization
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Serializing Position Objects
        List<String> templatePositionsListJSON = new ArrayList<String>();
        for (int i = 0; i < templatePositionsList.size(); i++) {
            try {
                templatePositionsListJSON.add(mapper.writeValueAsString(templatePositionsList.get(i)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return templatePositionsListJSON;
    }
}
