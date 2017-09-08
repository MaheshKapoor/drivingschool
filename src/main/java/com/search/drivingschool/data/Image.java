package com.search.drivingschool.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by abc on 9/8/2017.
 */
public class Image {

    @JsonProperty("cse_thumbnail")
    private List<Thumbnail> thumbnail;

    public List<Thumbnail> getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(List<Thumbnail> thumbnail) {
        this.thumbnail = thumbnail;
    }
}
