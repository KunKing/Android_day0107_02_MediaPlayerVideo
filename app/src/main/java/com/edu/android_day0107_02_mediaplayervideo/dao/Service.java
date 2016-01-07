package com.edu.android_day0107_02_mediaplayervideo.dao;

import com.edu.android_day0107_02_mediaplayervideo.entities.ItemEntity;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Ming on 2016/1/7.
 */
public interface Service {

    @GET("article/list/video")
    Call<ItemEntity> getList(@Query("page") int page);

}
