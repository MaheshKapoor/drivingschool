package com.search.drivingschool.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abc on 9/9/2017.
 */
public class CurrentPage {

    @JsonProperty("startIndex")
    private String startIndex;

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }
}
