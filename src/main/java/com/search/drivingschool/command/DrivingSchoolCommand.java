package com.search.drivingschool.command;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.search.drivingschool.Handler.DrivingCustomSearchHandler;
import com.search.drivingschool.Handler.DrivingDatabaseHandler;
import com.search.drivingschool.config.DrivingSchoolConfiguration;
import com.search.drivingschool.data.Items;
import com.search.drivingschool.data.Response;
import com.search.drivingschool.exception.DownstreamFailureException;
import com.search.drivingschool.util.RESTInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.*;

import static com.search.drivingschool.config.DrivingSchoolConstants.ServiceCommand.HTTP_STATUS_NOT_FOUND;

/**
 * Created by d818414 on 22/07/2017.
 */
@Service
public class DrivingSchoolCommand {

    @Autowired
    private DrivingSchoolConfiguration config;

    @Autowired
    private DrivingCustomSearchHandler drivingCustomSearchHandler;

    @Autowired
    private DrivingDatabaseHandler drivingDatabaseHandler;

    private static final Logger logger = LoggerFactory.getLogger(DrivingSchoolCommand.class);

    public Response getConsolidatedDetail(String suburb, String startIndex){
        Response customSearchResponse = null;
        try {

            //Write -Mongodb
//            drivingDatabaseHandler.addDataResult();


            //Read -Google API
            customSearchResponse = drivingCustomSearchHandler.getCustomSearchData(suburb, startIndex);
            //Read -Mongodb
            if(config.isMongodbToggleEnabled()){
                List dbList = drivingDatabaseHandler.getDatabaseResult(suburb);
                if(dbList.size() >0)
                customSearchResponse.getItems().addAll(0, dbList);
            }

            logger.info("response"+customSearchResponse);
        }catch(Exception ex){
            logger.error("Error while calling Custom Search API", ex);
        }
        return customSearchResponse;
    }

    public JsonObject getConsolidatedfeaturePartner(String suburb){
        JsonObject featurePartnerResponse = null;
        try {
            //Read -feature Partner
            if(true){
                 featurePartnerResponse = drivingDatabaseHandler.getFeaturePartner(suburb);
            }
            logger.info("featurePartnerResponse"+featurePartnerResponse);
        }catch(Exception ex){
            logger.error("Error while calling feature Partner API", ex);
        }
        return featurePartnerResponse;
    }



}
