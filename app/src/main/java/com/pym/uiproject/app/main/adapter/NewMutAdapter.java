package com.pym.uiproject.app.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.pym.uiproject.R;
import com.pym.uiproject.app.main.NewDetialFragment;
import com.pym.uiproject.app.main.model.ContentGson;
import com.pym.uiproject.base.RxBus;
import com.pym.uiproject.base.StartFragmentEvent;
import com.pym.uiproject.base.recycler.RecyclerHeaderFooterAdapter;
import com.pym.uiproject.base.recycler.RecyclerHolder;
import com.pym.uiproject.databinding.ItemNewMutBinding;
import com.pym.uiproject.util.ImageLoader;

import java.util.List;

/**
 * Peng YanMing on 2018\8\4 0004
 */
public class NewMutAdapter extends RecyclerHeaderFooterAdapter<ContentGson, ItemNewMutBinding> {
    private Fragment mFragment;
    public NewMutAdapter(Context context, Fragment fragment, List<ContentGson> list) {
        super(context, list, R.layout.item_new_mut);
        mFragment = fragment;
    }

    @Override
    protected void onBind(RecyclerHolder<ItemNewMutBinding> holder, int position, ContentGson contentGson) {
        if (contentGson.isHas_image()) {
            holder.binding.imageView.setVisibility(View.VISIBLE);
            ImageLoader.loadImageUrl(holder.binding.imageView, contentGson.getMiddle_image().getUrl_list().get(0).getUrl().replace("list/190x124", "video1609"));
        } else {
            holder.binding.imageView.setVisibility(View.GONE);
            ImageLoader.loadImageUrl(holder.binding.imageView, "");
        }
        ImageLoader.loadCircleImage(holder.binding.ivHeadImage, contentGson.getUser_info().getAvatar_url());
        holder.binding.tvName.setText(contentGson.getUser_info().getName());
        holder.binding.Title.setText(contentGson.getTitle());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("html", contentGson.getUrl());
                RxBus.get().post(new StartFragmentEvent(NewDetialFragment.newInstance(bundle)));
            }
        });

    }

}
