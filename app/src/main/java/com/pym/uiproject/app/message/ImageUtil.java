package com.pym.uiproject.app.message;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.pym.uiproject.util.ImageLoader;

/**
 * Peng YanMing on 2018\8\1 0001
 */
public class ImageUtil {
    @BindingAdapter({"headUrl"})
    public static void imageload(ImageView imageView, String url) {
        ImageLoader.loadImageUrl(imageView, url);
    }
//    @BindingAdapter({"itemImage"})
//    public static void imageUrl(ImageView imageView, NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.ImageurlsBean bean) {
//        ImageLoader.loadImageUrl(imageView, bean.getUrl());
//    }
}
