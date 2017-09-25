package com.search.drivingschool.service;

import com.google.gson.JsonObject;
import com.search.drivingschool.command.DrivingSchoolCommand;
import com.search.drivingschool.data.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

/**
 * Created by abc on 8/31/2017.
 */

@RestController
public class DrivingSchoolSearchService {

    @Autowired
    private DrivingSchoolCommand drivingSchoolCommand;

    @RequestMapping(value="/drivingschool", method= RequestMethod.GET)
    public @ResponseBody Response getDetails(@QueryParam("suburb") final String suburb,
                                             @QueryParam("startIndex") final String startIndex) throws Exception {
        Response response = null;
        //String suburb ="Parramatta";
        if(suburb != null) {
            response = drivingSchoolCommand.getConsolidatedDetail(suburb, startIndex);
        }
        return response;
    }

    @RequestMapping(value="/featurePartner", method= RequestMethod.GET)
    public @ResponseBody String getfeaturePartner(@QueryParam("country") final String country) throws Exception {

        JsonObject response = null;
        //String suburb ="Parramatta";
        //if(country != null) {
            response = drivingSchoolCommand.getConsolidatedfeaturePartner(country);

        //}
        return response.toString();
    }
}
