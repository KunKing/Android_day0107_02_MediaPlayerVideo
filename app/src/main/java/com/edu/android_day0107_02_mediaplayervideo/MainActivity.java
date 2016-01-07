package com.edu.android_day0107_02_mediaplayervideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.edu.android_day0107_02_mediaplayervideo.adapters.ItemAdapter;
import com.edu.android_day0107_02_mediaplayervideo.entities.ItemEntity;
import com.edu.android_day0107_02_mediaplayervideo.tools.HttpUtils;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<ItemEntity> {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new ItemAdapter(this);
        recyclerView.setAdapter(adapter);
        HttpUtils.getService().getList(1).enqueue(this);
    }

    @Override
    public void onResponse(Response<ItemEntity> response, Retrofit retrofit) {

        Log.d(TAG, "onResponse: "+response.body().getItems());
        adapter.addAll(response.body().getItems());
    }

    @Override
    public void onFailure(Throwable t) {

        t.printStackTrace();
        Toast.makeText(this, "显示错误", Toast.LENGTH_SHORT).show();

    }
}
