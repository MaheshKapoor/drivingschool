package com.search.drivingschool.command;

import com.google.gson.Gson;
import com.search.drivingschool.config.DrivingSchoolConfiguration;
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

    private static final String RESPONSE = "/mocks/googleResponse/GoogleResponse_Parramatta.json" ;

    public Response getConsolidatedDetail(String suburb){
        Response customSearchResponse = null;
        try {
            customSearchResponse = getCustomSearchData(suburb);
            logger.info("response"+customSearchResponse);
        }catch(Exception ex){
            logger.error("Error while calling Custom Search API", ex);
        }
        return customSearchResponse;
    }

    public Response getCustomSearchData(String suburb) throws IOException, DownstreamFailureException{
        Response response=null;
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
            if(false){
                logger.info("flow for mock");
                Reader reader = new InputStreamReader(DrivingSchoolCommand.class.getResourceAsStream(RESPONSE));
                logger.info("Reader :"+ reader);
                //gson
                Gson gson = new Gson();

                response = gson.fromJson(reader, Response.class);
                logger.info("response"+response);
            }else {
                response = restAPIService.get(googleCSEUrl, params, Response.class);
                if(response != null) {
                    logger.info("Downstream response is not Null");
                }else{
                    logger.info("Downstream response is NULL");
                }
            }
        }catch (HttpClientErrorException hce) {
            logAndThrowHttpClientErrorException(hce);
        }catch (Exception e) {
            throw new DownstreamFailureException("500", "Downstream Failure " + e);
        }

        return response;
    }

    public String getDBData(){

        return "Success";
    }



    private void logAndThrowHttpClientErrorException(HttpClientErrorException hce) {
        if (hce.getStatusCode() != null && hce.getStatusCode().value() == HTTP_STATUS_NOT_FOUND) {
            logger.error("There was no matching resource for the given input::" + hce);
        } else {
            logger.error("An HTTPClient error occurred while calling the API.", hce);
            throw hce;
        }
    }
}
