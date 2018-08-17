package com.pym.uiproject.app.playvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.kk.taurus.playerbase.entity.DataSource;
import com.pym.uiproject.R;
import com.pym.uiproject.app.playvideo.model.VideoLiveList;
import com.pym.uiproject.base.BaseViewModel;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragPlayLiveDetailBinding;
import com.pym.uiproject.util.DisplayUtil;
import com.pym.uiproject.util.ImageLoader;
import com.pym.uiproject.util.play.AssistPlayer;

/**
 * Peng YanMing on 2018\8\7 0007
 */
public class PlayLiveDetailFragment extends BindingFragment<FragPlayLiveDetailBinding,BaseViewModel> {

    private static  VideoLiveList.HomeDivsBean.HomePartitonBean.RoomInfoBean infoBean;

    public static PlayLiveDetailFragment newInstance(Bundle bundle) {
        infoBean = (VideoLiveList.HomeDivsBean.HomePartitonBean.RoomInfoBean) bundle.getSerializable("info");
        PlayLiveDetailFragment detailFragment = new PlayLiveDetailFragment();
        return detailFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_play_live_detail;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onPause() {
        super.onPause();
//        AssistPlayer.get().pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        AssistPlayer.get().resume();
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        DataSource dataSource = new DataSource(infoBean.getLive_info().getStream_url().getPullUrl().getFULL_HD1().getHls());
        AssistPlayer.get().play(mBinding.frameVideo, dataSource);
        if(infoBean.getLive_info().getOrientation()==0){
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            mBinding.frameVideo.setLayoutParams(layoutParams);
            ImageLoader.loadCircleImage(mBinding.ivHeadImage,infoBean.getUser_info().getAvatar_url());
            mBinding.tvName.setText(infoBean.getUser_info().getName());
            mBinding.tvFans.setText(infoBean.getUser_info().getFans_count()+"粉丝");
            mBinding.tvWatch.setText("观看人数:"+infoBean.getLive_info().getWatching_count());

        }else{
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(220));
            mBinding.frameVideo.setLayoutParams(layoutParams);
        }
        mBinding.animationViewiew1.setImageAssetsFolder("digganimation/images");
        mBinding.animationViewiew1.setAnimation("digganimation/data1.json");
        mBinding.animationViewiew1.setScale(0.4f);
        mBinding.animationViewiew1.setSpeed(0.5f);
        mBinding.animationViewiew1.playAnimation();
        mBinding.rlLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                binding.animationViewiew.setX(v.getWidth());
//                binding.animationViewiew.setY(v.getHeight());
                mBinding.animationViewiew1.playAnimation();
                return false;
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        AssistPlayer.get().destroy();
    }

    @Override
    protected void bindViewModel(@Nullable Bundle savedInstanceState) {

    }
}
