package com.search.drivingschool.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abc on 9/8/2017.
 */
public class NextPage {
    @JsonProperty("totalResults")
    private String totalResults;
    @JsonProperty("count")
    private String countPerPage;
    @JsonProperty("startIndex")
    private String startIndex;
    @JsonProperty("hq")
    private String suburb;

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getCountPerPage() {
        return countPerPage;
    }

    public void setCountPerPage(String countPerPage) {
        this.countPerPage = countPerPage;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
}
