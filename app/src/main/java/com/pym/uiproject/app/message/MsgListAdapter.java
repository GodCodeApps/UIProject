package com.pym.uiproject.app.message;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.pym.uiproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Peng YanMing on 2018\7\31 0031
 */
public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.MsgViewHolder> {

    private List<News.BodyBean.ComponentsBean.DataBean> newsList;

    public MsgListAdapter(List<News.BodyBean.ComponentsBean.DataBean>  lists) {
        this.newsList = lists;
    }

    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_msg_list, parent,false);
        return new MsgViewHolder(dataBinding.getRoot(), dataBinding);
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        holder.dataBinding.setVariable(BR.item4, newsList.get(position));
        holder.dataBinding.setVariable(BR.item5, newsList.get(position).getPics());
        holder.dataBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MsgViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding dataBinding;


        public MsgViewHolder(View itemView, ViewDataBinding dataBinding) {
            super(itemView);
            this.dataBinding = dataBinding;


            dataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelationAssist mAssist = new RelationAssist( dataBinding.getRoot().getContext());
                    mAssist.getSuperContainer().setBackgroundColor(Color.BLACK);
                    DataSource dataSource = new DataSource();
                    dataSource.setData("http://mgcdn.vod.mgspcloud.migucloud.com//vi1//564.10PyUMr50lUJ4tQjfJCPS.32.B48FqG.mp4?");
                    dataSource.setTitle("神奇的珊瑚");
                    mAssist.setDataSource(dataSource);
                    mAssist.attachContainer(dataBinding.getRoot().findViewById(R.id.frameVideo));
                    mAssist.play();
                }
            });
        }

        public ViewDataBinding getDataBinding() {
            return dataBinding;
        }
    }
}
