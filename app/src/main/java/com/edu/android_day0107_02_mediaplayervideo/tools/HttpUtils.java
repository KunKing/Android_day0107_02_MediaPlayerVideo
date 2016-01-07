package com.edu.android_day0107_02_mediaplayervideo.tools;

import com.edu.android_day0107_02_mediaplayervideo.dao.Service;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Ming on 2016/1/7.
 */
public class HttpUtils {

    private static Service service;
    static {
        service = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Service.class);
    }
    public static Service getService() {
        return service;
    }
}
