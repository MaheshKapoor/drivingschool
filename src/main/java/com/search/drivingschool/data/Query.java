package com.search.drivingschool.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by abc on 9/8/2017.
 */
public class Query {

    @JsonProperty("nextPage")
    private List<NextPage> nextPage;

    @JsonProperty("request")
    private List<CurrentPage> currentPaget;

    public List<NextPage> getNextPage() {
        return nextPage;
    }

    public void setNextPage(List<NextPage> nextPage) {
        this.nextPage = nextPage;
    }
}
