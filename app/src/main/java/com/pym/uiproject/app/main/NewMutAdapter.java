package com.pym.uiproject.app.main;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pym.uiproject.R;
import com.pym.uiproject.app.showapi.ui.NewBean;
import com.pym.uiproject.app.showapi.ui.NewDetialFragment;
import com.pym.uiproject.base.RxBus;
import com.pym.uiproject.base.StartFragmentEvent;
import com.pym.uiproject.databinding.ItemNewBinding;
import com.pym.uiproject.util.DisplayUtil;
import com.pym.uiproject.util.ImageLoader;

import java.util.List;

/**
 * Peng YanMing on 2018\8\4 0004
 */
public class NewMutAdapter extends RecyclerView.Adapter<NewMutAdapter.MyNewViewHolder> {
    private List<NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> beanList;
    private final int realWidth;

    public NewMutAdapter(List<NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list) {
        beanList = list;
        realWidth = DisplayUtil.getScreenWidth();
    }

    @Override
    public MyNewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_new_mut, parent, false);
        return new MyNewViewHolder(dataBinding.getRoot(), dataBinding);
    }

    @Override
    public void onBindViewHolder(MyNewViewHolder holder, int position) {
        holder.dataBinding.setVariable(BR.news, beanList.get(position));
        holder.dataBinding.executePendingBindings();
        if (beanList.get(position).isHavePic()) {
            holder.dataBinding.getRoot().findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            Glide.with(holder.dataBinding.getRoot().getContext())
                    .load(beanList.get(position).getImageurls().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into((ImageView) holder.dataBinding.getRoot().findViewById(R.id.imageView));
        } else {
            holder.dataBinding.getRoot().findViewById(R.id.imageView).setVisibility(View.GONE);
            ImageLoader.loadImageUrl(holder.dataBinding.getRoot().findViewById(R.id.imageView), "");
        }
        holder.dataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("html", beanList.get(position).getLink());
                RxBus.get().post(new StartFragmentEvent(NewDetialFragment.newInstance(bundle)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class MyNewViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding dataBinding;

        public MyNewViewHolder(View itemView, ViewDataBinding dataBinding) {
            super(itemView);
            this.dataBinding = dataBinding;
        }
    }
}
