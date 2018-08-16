package com.pym.uiproject.app.main;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.pym.uiproject.R;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragNewDetialBinding;

/**
 * Peng YanMing on 2018\8\3 0003
 */
public class NewDetialFragment extends BindingFragment<FragNewDetialBinding> {

    private TextView titleToor;

    public static NewDetialFragment newInstance(Bundle bundle) {
        NewDetialFragment fragment = new NewDetialFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        init();
        subscribeEvent();
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        Bundle bundle = getArguments();
        String html = bundle.getString("html");
        ImageView Black = binding.getRoot().findViewById(R.id.Black);
        titleToor = binding.getRoot().findViewById(R.id.Title);
        Black.setVisibility(View.VISIBLE);
        Black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        binding.webView.loadUrl(html);
        binding.webView.addJavascriptInterface(this,"android");//添加js监听 这样html就能调用客户端
        binding.webView.setWebViewClient(webViewClient);
        binding.webView.setWebChromeClient(webChromeClient);
        WebSettings webSettings= binding.webView.getSettings();
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
       binding.progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
       binding.progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    private WebChromeClient webChromeClient=new WebChromeClient(){
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
//            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
//            localBuilder.setMessage(message).setPositiveButton("确定",null);
//            localBuilder.setCancelable(false);
//            localBuilder.create().show();
//
//            //注意:
//            //必须要这一句代码:result.confirm()表示:
//            //处理结果为确定状态同时唤醒WebCore线程
//            //否则不能继续点击按钮
//            result.confirm();
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
            binding.progressbar.setProgress(newProgress);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.webView.destroy();
    }

    private void subscribeEvent() {
//        RxBus.get().toObservable(NewDetialFragment.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(mCompositeDisposable::add)
//                .subscribe(event -> {
//                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_new_detial;
    }
}
