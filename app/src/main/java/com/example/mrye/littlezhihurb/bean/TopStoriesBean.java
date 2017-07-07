package com.example.mrye.littlezhihurb.bean;

/**
 * 最新的知乎日报数据中的横向滑动页的数据集
 */

public class TopStoriesBean {

    private String image;
    private int type;
    private int id;  //后面根据这个id获取这条id所代表的一条完整的消息数据zhiHuItemData
    private String ga_prefix;
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

