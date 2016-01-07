package com.edu.android_day0107_02_mediaplayervideo.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edu.android_day0107_02_mediaplayervideo.R;
import com.edu.android_day0107_02_mediaplayervideo.entities.ItemEntity;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ming on 2016/1/7.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private Context context;
    private List<ItemEntity.ItemsEntity> list;

    private MediaPlayer mediaPlayer;

    public ItemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.draweeView.setOnClickListener(this);
        holder.draweeView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        // 重置 listView 在这边写就好了
        resetHolder(holder);

        ItemEntity.ItemsEntity entity = list.get(position);
        holder.textContent.setText(entity.getContent());
        holder.draweeView.setImageURI(Uri.parse(entity.getPic_url()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends ItemEntity.ItemsEntity> collection) {
        int size = collection.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    // 记录上一次的播放的状态
    private ViewHolder lastHolder;
    @Override
    public void onClick(View v) {
        if (lastHolder != null) {
            resetHolder(lastHolder);
        }
        ViewHolder position = (ViewHolder) v.getTag();
        try {
            // 重置
            mediaPlayer.reset();
            // 如果用 listView  给 holder 设置 一个 position 变量
            mediaPlayer.setDataSource(list.get(position.getAdapterPosition()).getLow_url());
            mediaPlayer.setDisplay(position.surfaceView.getHolder());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            position.item_play.setVisibility(View.INVISIBLE);
            position.item_progress.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 记录 最后显示
        lastHolder = position;

    }

    // 离开屏幕
    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (lastHolder != null && lastHolder.equals(holder)) {
            // 正在播放的视频 滚出了屏幕
            mediaPlayer.reset();
            // 释放 holder
            lastHolder = null;
        }
    }

    private void resetHolder(ViewHolder holder) {
        holder.draweeView.setVisibility(View.VISIBLE);
        holder.item_play.setVisibility(View.VISIBLE);
        holder.item_progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        lastHolder.draweeView.setVisibility(View.INVISIBLE);
        lastHolder.item_progress.setVisibility(View.INVISIBLE);
    }

    // 播放结束
    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.seekTo(0);
        mp.start();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private SurfaceView surfaceView;
        private TextView textContent;
        private SimpleDraweeView draweeView;
        private ImageView item_play;
        private ProgressBar item_progress;

        public ViewHolder(View itemView) {
            super(itemView);
            surfaceView = (SurfaceView) itemView.findViewById(R.id.sv);
            textContent = (TextView) itemView.findViewById(R.id.text_content);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.pic_url);
            item_play = (ImageView) itemView.findViewById(R.id.item_play);
            item_progress = (ProgressBar) itemView.findViewById(R.id.item_progress);
            // 获取图片的进度
            draweeView.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
            // 设置缩放比例
            draweeView.setAspectRatio(1);
        }
    }
}
