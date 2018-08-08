package com.pym.uiproject.app.main;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.pym.uiproject.databinding.ItemNewMutBinding;
/**
 * Peng YanMing on 2018\8\4 0004
 */
public class NewMutAdapter extends RecyclerView.Adapter<NewMutAdapter.MyNewViewHolder> {
    private List<ContentGson> beanList;
    public NewMutAdapter(List<ContentGson> list) {
        beanList = list;
    }

    @Override
    public MyNewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNewMutBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_new_mut, parent, false);
        return new MyNewViewHolder(dataBinding.getRoot(), dataBinding);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void onBindViewHolder(MyNewViewHolder holder, int position) {
        holder.dataBinding.setVariable(BR.news, beanList.get(position));
        holder.dataBinding.executePendingBindings();
        ContentGson contentGson = beanList.get(position);
        if (contentGson.isHas_image()) {
            holder.dataBinding.imageView.setVisibility(View.VISIBLE);
            Glide.with(holder.dataBinding.getRoot().getContext())
                    .load(contentGson.getMiddle_image().getUrl_list().get(0).getUrl().replace("list/190x124","video1609"))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into( holder.dataBinding.imageView);
        } else {
            holder.dataBinding.getRoot().findViewById(R.id.imageView).setVisibility(View.GONE);
            ImageLoader.loadImageUrl( holder.dataBinding.imageView, "");
        }
        ImageLoader.loadCircleImage(holder.dataBinding.ivHeadImage, contentGson.getUser_info().getAvatar_url());
        holder.dataBinding.tvName.setText(contentGson.getUser_info().getName());
        holder.dataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("html", contentGson.getUrl());
                RxBus.get().post(new StartFragmentEvent(NewDetialFragment.newInstance(bundle)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class MyNewViewHolder extends RecyclerView.ViewHolder {
        public ItemNewMutBinding dataBinding;

        public MyNewViewHolder(View itemView, ItemNewMutBinding dataBinding) {
            super(itemView);
            this.dataBinding = dataBinding;
        }
    }
}
