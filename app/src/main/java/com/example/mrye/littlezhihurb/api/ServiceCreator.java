package com.example.mrye.littlezhihurb.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建ApiService
 */

public class ServiceCreator {
    private Retrofit mRetrofit;

    /***/
    public ServiceCreator() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.API_BASE)//baseUrl的参数必须以"/"结尾
                    .addConverterFactory(GsonConverterFactory.create())
                    /*.addCallAdapterFactory(RxJavaCallAdapterFactory.create())*/
                    .build();
        }

    }
    private static class Holder{
        private static final ServiceCreator instance=new ServiceCreator();
    }

    public static ServiceCreator getInstance(){
        return Holder.instance;
    }

    public ApiService createService(){
        return mRetrofit.create(ApiService.class);
    }

}
