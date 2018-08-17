package com.pym.uiproject.app.playvideo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.pym.uiproject.R;
import com.pym.uiproject.app.playvideo.PlayLiveDetailFragment;
import com.pym.uiproject.app.playvideo.model.VideoLiveList;
import com.pym.uiproject.base.RxBus;
import com.pym.uiproject.base.StartFragmentEvent;
import com.pym.uiproject.base.recycler.RecyclerHeaderFooterAdapter;
import com.pym.uiproject.base.recycler.RecyclerHolder;
import com.pym.uiproject.databinding.ItemVideoListBinding;
import com.pym.uiproject.util.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Peng YanMing on 2018\8\6 0006
 */
public class VideoLiveListAdapter extends RecyclerHeaderFooterAdapter<VideoLiveList.HomeDivsBean.HomePartitonBean, ItemVideoListBinding> {
    List<VideoLiveList.HomeDivsBean.HomePartitonBean> beanList;
    DecimalFormat format = new DecimalFormat("0.0");
    private String watchNum = "";

    public VideoLiveListAdapter(Context context, Fragment fragment, List<VideoLiveList.HomeDivsBean.HomePartitonBean> list) {
        super(context, list, R.layout.item_video_list);
        beanList = list;
    }

    @Override
    protected void onBind(RecyclerHolder<ItemVideoListBinding> holder, int position, VideoLiveList.HomeDivsBean.HomePartitonBean homePartitonBean) {
        VideoLiveList.HomeDivsBean.HomePartitonBean.RoomInfoBean room_info = homePartitonBean.getRoom_info();
        int anInt = Integer.parseInt(room_info.getLive_info().getWatching_count());
        if (anInt > 10000) {
            double aDouble = Double.parseDouble(anInt + "");
            double x = (aDouble / 10000);
            watchNum = format.format(x) + "万人观看";
        } else {
            watchNum = anInt + "人观看";
        }
        holder.binding.tvTitle.setText(room_info.getTitle());
        holder.binding.watchNumber.setText(watchNum);
        holder.binding.animationViewiew.setAnimation("xigualive_live_line.json", LottieAnimationView.CacheStrategy.Strong);
        holder.binding.animationViewiew.playAnimation();
        holder.binding.tvAuth.setText(room_info.getUser_info().getName() + "  " + room_info.getUser_info().getFans_count() + "粉丝");
        ImageLoader.loadCircleImage(holder.binding.imageView, room_info.getUser_info().getAvatar_url());
        ImageLoader.loadImage(holder.binding.imageViewItem, room_info.getLarge_image().getUrl().replace("list/190x124", "video1609"), Integer.parseInt(room_info.getLarge_image().getWidth()), Integer.parseInt(room_info.getLarge_image().getHeight()));
        holder.binding.imageViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", room_info);
                RxBus.get().post(new StartFragmentEvent(PlayLiveDetailFragment.newInstance(bundle)));
            }
        });
    }
}
