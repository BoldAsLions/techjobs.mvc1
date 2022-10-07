package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        List<Job> jobs = new ArrayList<>();
        jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        if (searchType == "All") {
            if (searchType.toLowerCase() == "all" || searchTerm == null) {
                jobs = JobData.findAll();
            }
        }else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);

        }
            model.addAttribute("jobs", jobs);
            model.addAttribute("type", searchType);
            model.addAttribute("term", searchTerm);
            model.addAttribute("columns", ListController.columnChoices);
            model.addAttribute("title", "search for '" + searchTerm + "'");
            return "search";


        }

}
