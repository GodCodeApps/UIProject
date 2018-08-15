package com.pym.uiproject.app.showapi.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.pym.uiproject.R;
import com.pym.uiproject.app.message.Result;
import com.pym.uiproject.app.showapi.ShowApiRequest;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragNewMainBinding;
import com.pym.uiproject.widget.AutoLoadRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Peng YanMing on 2018\8\3 0003
 */
public class NewFragment extends BindingFragment<FragNewMainBinding> {

    private List<NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> results;
    BigImageAdapter bigImageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_new_main;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        results = new ArrayList<>();

        binding.animationViewiew.setImageAssetsFolder("digganimation/images");
        binding.animationViewiew.setAnimation("digganimation/data1.json");
        binding.animationViewiew.setScale(0.4f);
        binding.animationViewiew.playAnimation();
        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = v.getX();
                float y = v.getY();
                float pivotX = v.getPivotX();
                float pivotY = v.getPivotY();
                Log.e("onTouch", "x=" + x + "y=" + y);
                Log.e("onTouch", "pivotX=" + pivotX + "pivotY=" + pivotY);


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    LottieAnimationView animationView=new LottieAnimationView(binding.getRoot().getContext());
                    animationView.setImageAssetsFolder("digganimation/images");
                    animationView.setAnimation("digganimation/data1.json");
                    animationView.setScale(0.4f);
                    animationView.setX(event.getX()-60);
                    animationView.setY(event.getY()-60);
                    animationView.playAnimation();
                }
                return true;
            }
        });
//        bigImageAdapter = new BigImageAdapter(results);
//        binding.recycler.setAdapter(bigImageAdapter);
//        new Thread(runnable).start();
//        binding.recycler.setOnLoadListener(new AutoLoadRecyclerView.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                new Thread(runnable).start();
//            }
//        });
//        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Thread(runnable).start();
//            }
//        });

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NewBean newBean = (NewBean) msg.obj;
            List<NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = newBean.getShowapi_res_body().getPagebean().getContentlist();
            if (contentlist != null && contentlist.size() > 0) {
                results.clear();
                results.addAll(contentlist);
                bigImageAdapter.notifyDataSetChanged();
                Log.i("请求结果", "请求结果:" + newBean.toString());
                binding.refreshLayout.setRefreshing(false);
                binding.recycler.loading = false;
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            final String res = new ShowApiRequest("http://route.showapi.com/109-35", "71701", "a8b6c586636144ed96b9579baaa3d724")
//                    .addTextPara("channelId", "")
//                    .addTextPara("channelName", "")
//                    .addTextPara("title", "电影最新")
//                    .addTextPara("page", "1")
//                    .addTextPara("needContent", "0")
//                    .addTextPara("needHtml", "0")
//                    .addTextPara("needAllList", "0")
//                    .addTextPara("maxResult", "100")
//                    .addTextPara("id", "")
//                    .post();
//            Gson gson = new Gson();
//            NewBean newBean = gson.fromJson(res, NewBean.class);
//            NewBean.ShowapiResBodyBean resBody = newBean.getShowapi_res_body();
//            if(resBody!=null&&resBody.getPagebean()!=null) {
//                Message msg = new Message();
//                msg.obj = newBean;
//                handler.sendMessage(msg);
//            }
        }
    };
}
