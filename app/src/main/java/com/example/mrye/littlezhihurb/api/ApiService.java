package com.example.mrye.littlezhihurb.api;

import com.example.mrye.littlezhihurb.bean.BeforeZhiHuStory;
import com.example.mrye.littlezhihurb.bean.LatestZhiHuStory;
import com.example.mrye.littlezhihurb.bean.ZhiHuItemData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit api，构建请求
 */

public interface ApiService {

    String API_BASE="http://news-at.zhihu.com/api/4/";

    @GET("news/latest")
    Call<LatestZhiHuStory> getLatestZhiHuStory();
    //http://news-at.zhihu.com/api/4/news/latest

    @GET("news/{id}")
    Call<ZhiHuItemData> getZhiHUItemData(@Path("id") int id);
    //http://news-at.zhihu.com/api/4/news/id

    @GET("news/before/{date}")
    Call<BeforeZhiHuStory> getBeforeZhiHuStory(@Path("date") String date);
    //http://news-at.zhihu.com/api/4/news/before/date

}
