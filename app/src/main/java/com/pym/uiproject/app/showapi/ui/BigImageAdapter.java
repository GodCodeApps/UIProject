package com.pym.uiproject.app.showapi.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pym.uiproject.BR;
import com.pym.uiproject.R;
import com.pym.uiproject.base.RxBus;
import com.pym.uiproject.base.StartFragmentEvent;
import com.pym.uiproject.base.StartFragmentWithPopEvent;
import com.pym.uiproject.databinding.ItemNewBinding;
import com.pym.uiproject.util.DisplayUtil;
import com.pym.uiproject.util.ImageLoader;

import java.util.List;

/**
 * 这里是萌萌哒注释君
 *
 * @author gavin.xiong 2017/5/17
 */
public class BigImageAdapter extends RecyclerView.Adapter<BigImageAdapter.NewViewHolder> {
    private List<NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> beanList;
    private final int maxHeight;
    private final int minHeight;
    private final int realWidth;

    public BigImageAdapter(List<NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> beanList) {
        this.beanList = beanList;
        realWidth = DisplayUtil.getScreenWidth() / 2 - DisplayUtil.dp2px(12);
        maxHeight = (int) (DisplayUtil.getScreenWidth() * 0.8);
        minHeight = (int) (DisplayUtil.getScreenWidth() * 0.25);
    }
    @Override
    public BigImageAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNewBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_new, parent, false);
        return new NewViewHolder(dataBinding.getRoot(), dataBinding);
    }

    @Override
    public void onBindViewHolder(BigImageAdapter.NewViewHolder holder, int position) {
        holder.dataBinding.setVariable(BR.item, beanList.get(position));
        holder.dataBinding.executePendingBindings();
        if (beanList.get(position).isHavePic()) {
            holder.dataBinding.imageView.getLayoutParams().width = realWidth;
            holder.dataBinding.imageView.getLayoutParams().height = (int) (realWidth * 1.3);
            Glide.with(holder.dataBinding.getRoot().getContext())
                    .load(beanList.get(position).getImageurls().get(0).getUrl())
                    .override(realWidth, (int) (realWidth * 1.2))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.dataBinding.imageView);
        } else {
            ImageLoader.loadImageUrl(holder.dataBinding.imageView,"");
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

    public class NewViewHolder extends RecyclerView.ViewHolder {
        private ItemNewBinding dataBinding;

        public NewViewHolder(View itemView, ItemNewBinding dataBinding) {
            super(itemView);
            this.dataBinding = dataBinding;
        }
    }
}
