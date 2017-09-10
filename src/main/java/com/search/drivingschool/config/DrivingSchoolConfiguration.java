package com.search.drivingschool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by d818414 on 22/07/2017.
 */
@Configuration
public class DrivingSchoolConfiguration {

    @Value("${Google.CSE.API.URL}")
    private String downStreamCSEURL;

    @Value("${Google.CSE.Default.Query}")
    private String defaultCSEQueryParam;

    @Value("${Google.CSE.Key}")
    private String customSearchQueryParam;

    @Value("${Google.CSE.API.KEY}")
    private String customSearchAPIKey;

    @Value("${Mongo.db.connection.url}")
    private String mongodbConnectionURL;

    @Value("${Mongo.db.toggle}")
    private boolean mongodbToggleEnabled;

    public String getCustomSearchAPIKey() {
        return customSearchAPIKey;
    }

    public void setCustomSearchAPIKey(String customSearchAPIKey) {
        this.customSearchAPIKey = customSearchAPIKey;
    }

    public String getCustomSearchQueryParam() {
        return customSearchQueryParam;
    }

    public void setCustomSearchQueryParam(String customSearchQueryParam) {
        this.customSearchQueryParam = customSearchQueryParam;
    }

    public String getDefaultCSEQueryParam() {
        return defaultCSEQueryParam;
    }

    public void setDefaultCSEQueryParam(String defaultCSEQueryParam) {
        this.defaultCSEQueryParam = defaultCSEQueryParam;
    }

    public String getDownStreamCSEURL() {
        return downStreamCSEURL;
    }

    public void setDownStreamCSEURL(String downStreamCSEURL) {
        this.downStreamCSEURL = downStreamCSEURL;
    }

    public String getMongodbConnectionURL() {
        return mongodbConnectionURL;
    }

    public void setMongodbConnectionURL(String mongodbConnectionURL) {
        this.mongodbConnectionURL = mongodbConnectionURL;
    }

    public boolean isMongodbToggleEnabled() {
        return mongodbToggleEnabled;
    }

    public void setMongodbToggleEnabled(boolean mongodbToggleEnabled) {
        this.mongodbToggleEnabled = mongodbToggleEnabled;
    }
}
