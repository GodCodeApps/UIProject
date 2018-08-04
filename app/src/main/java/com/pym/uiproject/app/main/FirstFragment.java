package com.pym.uiproject.app.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.google.gson.Gson;
import com.pym.uiproject.R;
import com.pym.uiproject.app.showapi.ShowApiRequest;
import com.pym.uiproject.app.showapi.ui.BigImageAdapter;
import com.pym.uiproject.app.showapi.ui.NewBean;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragFirstBinding;
import com.pym.uiproject.widget.AutoLoadRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Peng YanMing on 2018\8\4 0004
 */
public class FirstFragment extends BindingFragment<FragFirstBinding> {
    private List<NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> results;
    NewMutAdapter bigImageAdapter;
private static String channelId="";
    @Override
    protected int getLayoutId() {
        return R.layout.frag_first;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        results = new ArrayList<>();
        bigImageAdapter = new NewMutAdapter(results);
        binding.recycle.setAdapter(bigImageAdapter);
        new Thread(runnable).start();
        binding.recycle.setOnLoadListener(new AutoLoadRecyclerView.OnLoadListener() {
            @Override
            public void onLoad() {
                new Thread(runnable).start();
            }
        });
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(runnable).start();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NewBean newBean = (NewBean) msg.obj;
            List<NewBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist = newBean.getShowapi_res_body().getPagebean().getContentlist();
            results.clear();
            results.addAll(contentlist);
            bigImageAdapter.notifyDataSetChanged();
            Log.i("请求结果", "请求结果:" + newBean.toString());
            binding.refreshLayout.setRefreshing(false);
            binding.recycle.loading = false;
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final String res = new ShowApiRequest("http://route.showapi.com/109-35", "71701", "a8b6c586636144ed96b9579baaa3d724")
                    .addTextPara("channelId", channelId)
                    .addTextPara("channelName", "")
                    .addTextPara("title", "")
                    .addTextPara("page", "1")
                    .addTextPara("needContent", "0")
                    .addTextPara("needHtml", "0")
                    .addTextPara("needAllList", "0")
                    .addTextPara("maxResult", "100")
                    .addTextPara("id", "")
                    .post();
            Gson gson = new Gson();
            NewBean newBean = gson.fromJson(res, NewBean.class);
            Message msg = new Message();
            msg.obj = newBean;
            handler.sendMessage(msg);
        }
    };

    public static Fragment newInstance(String s) {
        channelId=s;
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }
}
