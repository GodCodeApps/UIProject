package com.pym.uiproject.util.play;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;

import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.log.PLog;
import com.pym.uiproject.app.playvideo.VideoLiveList;
import com.pym.uiproject.app.playvideo.VideoLiveListAdapter;
import com.pym.uiproject.util.DisplayUtil;

/**
 * Created by Taurus on 2018/4/15.
 */

public class ListPlayLogic {

    private Context mContext;
    private RecyclerView mRecycler;
    private VideoLiveListAdapter mAdapter;

    private int mScreenH;

    private int mPlayPosition = -1;
    private int mVerticalRecyclerStart;

    public ListPlayLogic(Context context, RecyclerView recycler, VideoLiveListAdapter adapter) {
        this.mContext = context;
        this.mRecycler = recycler;
        this.mAdapter = adapter;
        init();
    }

    private void init() {
        mScreenH = DisplayUtil.getScreenHeight();
        mRecycler.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                int[] location = new int[2];
                mRecycler.getLocationOnScreen(location);
                mVerticalRecyclerStart = location[1];
                mRecycler.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemVisibleRectHeight = getItemVisibleRectHeight(mPlayPosition);
                    if (itemVisibleRectHeight <= 0) {
                        PLog.d("ListPlayLogic", "onScrollStateChanged stop");
                        AssistPlayer.get().stop();
                        mAdapter.notifyItemChanged(mPlayPosition);
                        mPlayPosition = -1;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    public void updatePlayPosition(int position) {
        notifyPrePosition();
        this.mPlayPosition = position;
    }

    private void notifyPrePosition() {
        if (mPlayPosition >= 0)
            mAdapter.notifyItemChanged(mPlayPosition);
    }

    public void attachPlay() {
        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                VideoLiveListAdapter.VideoHolder itemHolder = getItemHolder(mPlayPosition);
                if (itemHolder != null) {
                    PLog.d("ListPlayLogic", "attachPlay...");
                    AssistPlayer.get().play(itemHolder.videoListBinding.layoutContainer, null);
                }
            }
        });
    }

    public void playPosition(int position) {
        VideoLiveList.HomeDivsBean.HomePartitonBean item = getItem(position);
        DataSource dataSource = new DataSource(item.getRoom_info().getLive_info().getStream_url().getPullUrl().getFULL_HD1().getHls());
//        dataSource.setTitle(item.getDisplayName());
        VideoLiveListAdapter.VideoHolder holder = getItemHolder(position);
        if (holder != null) {
            PLog.d("ListPlayLogic", "playPosition : position = " + position);
            AssistPlayer.get().play( holder.videoListBinding.layoutContainer, dataSource);
        }
    }

    private VideoLiveList.HomeDivsBean.HomePartitonBean getItem(int position) {
        return mAdapter.getItem(position);
    }

    private VideoLiveListAdapter.VideoHolder getItemHolder(int position) {
        RecyclerView.ViewHolder viewHolder = mRecycler.findViewHolderForLayoutPosition(position);
        if (viewHolder != null && viewHolder instanceof VideoLiveListAdapter.VideoHolder) {
            return ((VideoLiveListAdapter.VideoHolder) viewHolder);
        }
        return null;
    }

    /**
     * 获取Item中渲染视图的可见高度
     *
     * @param position
     * @return
     */
    private int getItemVisibleRectHeight(int position) {
        VideoLiveListAdapter.VideoHolder itemHolder = getItemHolder(position);
        if (itemHolder == null)
            return 0;
        int[] location = new int[2];
        itemHolder.videoListBinding.layoutBox.getLocationOnScreen(location);
        int height =  itemHolder.videoListBinding.layoutBox.getHeight();

        int visibleRect;
        if (location[1] <= mVerticalRecyclerStart) {
            visibleRect = location[1] - mVerticalRecyclerStart + height;
        } else {
            if (location[1] + height >= mScreenH) {
                visibleRect = mScreenH - location[1];
            } else {
                visibleRect = height;
            }
        }
        return visibleRect;
    }

    /**
     * 获取两个索引条目中渲染视图可见高度最大的条目
     *
     * @param position1
     * @param position2
     * @return
     */
    private int getVisibleRectMaxPosition(int position1, int position2) {
        VideoLiveListAdapter.VideoHolder itemHolder1 = getItemHolder(position1);
        VideoLiveListAdapter.VideoHolder itemHolder2 = getItemHolder(position2);
        if (itemHolder1 == null && itemHolder2 == null) {
            return RecyclerView.NO_POSITION;
        }
        if (itemHolder1 == null) {
            return position2;
        }
        if (itemHolder2 == null) {
            return position1;
        }
        int visibleRect1 = getItemVisibleRectHeight(position1);
        int visibleRect2 = getItemVisibleRectHeight(position2);
        return visibleRect1 >= visibleRect2 ? position1 : position2;
    }

    /**
     * 判断给定的索引条目，渲染视图的可见高度是否满足播放条件.
     *
     * @param position
     * @return
     */
    private boolean isVisibleRectAvailablePlay(int position) {
        VideoLiveListAdapter.VideoHolder itemHolder = getItemHolder(position);
        if (itemHolder == null)
            return false;
        int height =  itemHolder.videoListBinding.layoutBox.getHeight();
        return getItemVisibleRectHeight(position) > (height / 2);
    }

    private boolean isCompleteVisibleRect(int position) {
        VideoLiveListAdapter.VideoHolder itemHolder = getItemHolder(position);
        if (itemHolder == null)
            return false;
        int height =  itemHolder.videoListBinding.layoutBox.getHeight();
        return getItemVisibleRectHeight(position) == height;
    }


}
