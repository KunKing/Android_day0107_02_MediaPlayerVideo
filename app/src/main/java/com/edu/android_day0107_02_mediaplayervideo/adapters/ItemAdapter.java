package com.edu.android_day0107_02_mediaplayervideo.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.android_day0107_02_mediaplayervideo.R;
import com.edu.android_day0107_02_mediaplayervideo.entities.ItemEntity;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ming on 2016/1/7.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private Context context;
    private List<ItemEntity.ItemsEntity> list;

    public ItemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemEntity.ItemsEntity entity = list.get(position);
        holder.textContent.setText(entity.getContent());
        holder.draweeView.setImageURI(Uri.parse(entity.getPic_url()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends ItemEntity.ItemsEntity> collection){
        int size = collection.size();
        list.addAll(collection);
        notifyItemRangeInserted(size,collection.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private SurfaceView surfaceView;
        private TextView textContent;
        private SimpleDraweeView draweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            surfaceView = (SurfaceView) itemView.findViewById(R.id.sv);
            textContent = (TextView) itemView.findViewById(R.id.text_content);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.pic_url);
            // 获取图片的进度
            draweeView.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
            // 设置缩放比例
            draweeView.setAspectRatio(1);
        }
    }
}
