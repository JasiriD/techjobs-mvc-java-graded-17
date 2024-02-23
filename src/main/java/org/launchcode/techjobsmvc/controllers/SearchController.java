package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

    //Using postmapping because the thing in search.html said post, honestly dont truly understand that
    @PostMapping(value = "results")
    //displaySearchResults function. Takes a model parameter, along with searchType and searchTerm from search.html
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam(required = false) String searchTerm){
        //Passing ListController.columnChoices to view. I got this code from stackverflow
        model.addAttribute("columns", columnChoices);
        ArrayList<Job> jobs;
        //if user enters all or leaves box empty, call findall,
        if (searchType.equals("all") && searchTerm.isEmpty() || (searchType.isEmpty()) && searchTerm.isEmpty()){
            jobs = JobData.findAll();
            //Adding this to output as I interperet
            model.addAttribute("title", "All Jobs: ");
        } else if (searchType.equals("all") || (searchType.isEmpty())){
            jobs = JobData.findByValue(searchTerm);
            model.addAttribute("title", "All Jobs With: "+ searchTerm);
        }
        else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("title", "Jobs with " + searchType + ": " + searchTerm);
        }
        model.addAttribute("jobs", jobs);

        return "search";
    }
}

