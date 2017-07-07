package com.example.mrye.littlezhihurb.bean;

import java.util.List;

/**
 * 今天之前的知乎日报消息，只包括一部分：StoriesBean
 */

public class BeforeZhiHuStory {


    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }


}
