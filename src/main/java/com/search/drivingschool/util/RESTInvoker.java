package com.search.drivingschool.util;

import com.search.drivingschool.config.DrivingSchoolConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

/**
 * Created by d818414 on 22/07/2017.
 */
@Service
public class RESTInvoker {

    @Autowired
    private DrivingSchoolConfiguration config;

    private static final Logger logger = LoggerFactory.getLogger(RESTInvoker.class);
    private static final RestTemplate restTemplate = new RestTemplate();


    public <T> T get(String apiUrl, Map<String, Object> queryParams, Class<T> responseType) {
        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        return exchange(apiUrl, HttpMethod.GET, queryParams,  httpEntity, responseType);
    }

    public HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //httpHeaders.set(TRANSACTION_ID, UUID.randomUUID().toString());

        return httpHeaders;
    }


    private <T> T exchange(String apiUrl, HttpMethod method, Map<String, Object> queryParams,
                           HttpEntity<Object> httpEntity, Class<T> responseType) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        if ((queryParams != null) && (!queryParams.isEmpty())) {
            for (String key: queryParams.keySet()) {
                uriBuilder.queryParam(key, queryParams.get(key));
            }
        }

        try {
            logger.info("RESTInvoker URL :"+uriBuilder.build().toUri());
            ResponseEntity<T> response = restTemplate.exchange(
                    uriBuilder.build().toUri(),
                    method,
                    httpEntity,
                    responseType);

            if (response != null) {
                logger.info("RESTInvoker :"+response);
                //202 response with empty body is a valid response, in this case returning null indicates error. So..
                if (response.getStatusCode() == HttpStatus.ACCEPTED && response.getBody() == null) {
                    return responseType.newInstance();
                }
                return response.getBody();
            }

        } catch (Exception e) {
            logger.error("Exception while making " + method + " request to " + uriBuilder.build().toUri(), e);
        }

        return null;
    }
}
