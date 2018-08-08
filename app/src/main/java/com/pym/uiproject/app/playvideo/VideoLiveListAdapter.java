package com.pym.uiproject.app.playvideo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.model.LottieComposition;
import com.android.databinding.library.baseAdapters.BR;
import com.pym.uiproject.R;
import com.pym.uiproject.base.RxBus;
import com.pym.uiproject.base.StartFragmentEvent;
import com.pym.uiproject.databinding.ItemVideoListBinding;
import com.pym.uiproject.util.DisplayUtil;
import com.pym.uiproject.util.ImageLoader;
import com.pym.uiproject.util.play.ListPlayLogic;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Peng YanMing on 2018\8\6 0006
 */
public class VideoLiveListAdapter extends RecyclerView.Adapter<VideoLiveListAdapter.VideoHolder> {
    List<VideoLiveList.HomeDivsBean.HomePartitonBean> beanList;
    DecimalFormat format = new DecimalFormat("0.00");
    private final int mScreenUseW;
    private final ListPlayLogic mListPlayLogic;
    private Context context;
    private String watchNum = "";

    public VideoLiveListAdapter(Context context, RecyclerView recyclerView, List<VideoLiveList.HomeDivsBean.HomePartitonBean> list) {
        beanList = list;
        context = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mScreenUseW = displayMetrics.widthPixels - DisplayUtil.dp2px(6 * 2);
        mListPlayLogic = new ListPlayLogic(context, recyclerView, this);
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVideoListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_video_list, parent, false);
        return new VideoHolder(binding);
    }

    public VideoLiveList.HomeDivsBean.HomePartitonBean getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {

        VideoLiveList.HomeDivsBean.HomePartitonBean.RoomInfoBean room_info = beanList.get(position).getRoom_info();
        holder.videoListBinding.setVariable(BR.item, room_info);
        holder.videoListBinding.executePendingBindings();

//        ViewCompat.setElevation(holder.videoListBinding.layoutBox, DisplayUtil.dp2px(3));
//        updateWH(holder);
        int anInt = Integer.parseInt(room_info.getLive_info().getWatching_count());
        if (anInt > 10000) {
            double aDouble = Double.parseDouble(anInt + "");
            double x = (aDouble / 10000);
            watchNum = format.format(x) + "万人观看";
        } else {
            watchNum = anInt + "人观看";
        }
        holder.videoListBinding.watchNumber.setText(watchNum);
        holder.videoListBinding.animationViewiew.setAnimation("xigualive_live_line.json", LottieAnimationView.CacheStrategy.Strong);
        holder.videoListBinding.animationViewiew.playAnimation();
        holder.videoListBinding.tvAuth.setText(room_info.getUser_info().getName() + "     " + room_info.getUser_info().getFans_count() + "粉丝");
        ImageLoader.loadCircleImage(holder.videoListBinding.imageView, room_info.getUser_info().getAvatar_url());
        ImageLoader.loadImage(holder.videoListBinding.imageViewItem, room_info.getLarge_image().getUrl(), Integer.parseInt(room_info.getLarge_image().getWidth()), Integer.parseInt(room_info.getLarge_image().getHeight()));
        holder.videoListBinding.imageViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("info",room_info);
                RxBus.get().post(new StartFragmentEvent(PlayLiveDetailFragment.newInstance(bundle)));
            }
        });


    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        public ItemVideoListBinding videoListBinding;

        public VideoHolder(ItemVideoListBinding binding) {
            super(binding.getRoot());
            videoListBinding = binding;
            videoListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listListener != null) {
                        listListener.onItemListener();
                    }
                }
            });
        }
    }

    public void updatePlayPosition(int position) {
        mListPlayLogic.updatePlayPosition(position);
    }

    private void updateWH(VideoHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.videoListBinding.imageViewItem.getLayoutParams();
        layoutParams.width = mScreenUseW;
        layoutParams.height = mScreenUseW * 9 / 16;
        holder.videoListBinding.imageViewItem.setLayoutParams(layoutParams);
    }

    public ListPlayLogic getListPlayLogic() {
        return mListPlayLogic;
    }

    public OnListListener listListener;

    public interface OnListListener {
        void onItemListener();
    }

    public void setItemClickListener(OnListListener itemClickListener) {
        listListener = itemClickListener;

    }
}
