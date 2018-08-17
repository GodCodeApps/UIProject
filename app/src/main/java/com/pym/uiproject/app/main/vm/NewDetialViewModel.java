package com.pym.uiproject.app.main.vm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.pym.uiproject.R;
import com.pym.uiproject.app.main.NewDetialFragment;
import com.pym.uiproject.base.FragViewModel;
import com.pym.uiproject.databinding.FragNewDetialBinding;

/**
 * Peng YanMing on 2018\8\16 0016
 */
public class NewDetialViewModel extends FragViewModel<NewDetialFragment,FragNewDetialBinding> {
    private String mHtml="";
    private TextView titleToor;

    public NewDetialViewModel(Context context, NewDetialFragment fragment, FragNewDetialBinding binding,String html) {
        super(context, fragment, binding);
        mHtml=html;
    }

    @Override
    public void afterCreate() {
        init();
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        ImageView Black = mBinding.get().getRoot().findViewById(R.id.Black);
        titleToor = mBinding.get().getRoot().findViewById(R.id.Title);
        Black.setVisibility(View.VISIBLE);
        Black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mFragment.get().pop();
            }
        });
        mBinding.get().webView.loadUrl(mHtml);
        mBinding.get().webView.addJavascriptInterface(this,"android");//添加js监听 这样html就能调用客户端
        mBinding.get().webView.setWebViewClient(webViewClient);
        mBinding.get().webView.setWebChromeClient(webChromeClient);
        WebSettings webSettings= mBinding.get().webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }
    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient=new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            mBinding.get().progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            mBinding.get().progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    private WebChromeClient webChromeClient=new WebChromeClient(){
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleToor.setText(title+"");
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mBinding.get().progressbar.setProgress(newProgress);
        }
    };
}
