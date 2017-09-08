package com.search.drivingschool.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abc on 9/8/2017.
 */
public class Thumbnail {
    @JsonProperty("src")
    private String imgURL;

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
