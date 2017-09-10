package com.search.drivingschool.command;

import com.search.drivingschool.config.DrivingSchoolConfiguration;
import com.search.drivingschool.exception.DownstreamFailureException;
import com.search.drivingschool.util.RESTInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.search.drivingschool.config.DrivingSchoolConstants.ServiceCommand.HTTP_STATUS_NOT_FOUND;

/**
 * Created by d818414 on 22/07/2017.
 */
@Service
public class DrivingSchoolCommand {

    @Autowired
    private RESTInvoker restAPIService;

    @Autowired
    private DrivingSchoolConfiguration config;

    private static final Logger logger = LoggerFactory.getLogger(DrivingSchoolCommand.class);
    public String getConsolidatedDetail(String suburb){
        String customSearchResponse = null;
        try {
            customSearchResponse = getCustomSearchData(suburb);
        }catch(Exception ex){
            logger.error("Error while calling Custom Search API", ex);
        }
        return customSearchResponse;
    }

    public String getCustomSearchData(String suburb) throws IOException, DownstreamFailureException{
        String response=null;
        String googleCSEUrl = config.getDownStreamCSEURL();
        String defaultQueryParam = config.getDefaultCSEQueryParam();
        String defaultCSEQueryParam = config.getCustomSearchQueryParam();
        String customSearchAPIKey = config.getCustomSearchAPIKey();

        Map<String, Object> params = new HashMap<String, Object>();

        if(suburb != null){
            params.put("hq",suburb);
        }

        if(defaultQueryParam != null){
            params.put("q", defaultQueryParam);
        }

        if(defaultCSEQueryParam != null){
            params.put("cx", defaultCSEQueryParam);
        }

        if(customSearchAPIKey != null){
            params.put("key", customSearchAPIKey);
        }

        try{
            response = restAPIService.get(googleCSEUrl, params, String.class);

        }catch (HttpClientErrorException hce) {
            logAndThrowHttpClientErrorException(hce);
        }catch (Exception e) {
            throw new DownstreamFailureException("500", "Downstream Failure");
        }

        return response;
    }

    public String getDBData(){

        return "Success";
    }




}
