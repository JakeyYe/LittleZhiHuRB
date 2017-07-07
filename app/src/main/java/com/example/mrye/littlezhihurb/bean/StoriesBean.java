package com.example.mrye.littlezhihurb.bean;

import java.util.List;

/**
 * 最新一天的知乎日报的数据中的另一部分数据集：StoriesBean
 */

public class StoriesBean {

    private String title;
    private String ga_prefix;
    private boolean multipic;
    private int type;
    private int id;       //后面根据这个id获取这条id所代表的一条完整的消息数据zhiHuItemData
    private List<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
