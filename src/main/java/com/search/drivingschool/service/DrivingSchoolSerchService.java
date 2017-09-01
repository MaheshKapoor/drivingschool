package com.search.drivingschool.service;

import com.search.drivingschool.command.DrivingSchoolCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrivingSchoolSerchService {

    @Autowired
    private DrivingSchoolCommand drivingSchoolCommand;

    @RequestMapping(value="/suburb/{suburb}", method=RequestMethod.GET)
    public String getDetails(@PathVariable("suburb") final String suburb) throws Exception {
        String response = null;
        if(suburb != null) {
            response = drivingSchoolCommand.getConsolidatedDetail(suburb);
        }
        return response;
    }

}