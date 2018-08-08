package com.pym.uiproject.util.cover;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.kk.taurus.playerbase.receiver.BaseCover;
import com.pym.uiproject.R;
import com.pym.uiproject.databinding.LayoutCloseCoverBinding;
import com.pym.uiproject.util.play.DataInter;

public class CloseCover extends BaseCover {

    private LayoutCloseCoverBinding coverBinding;

    public CloseCover(Context context) {
        super(context);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
    }
    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();
    }

    @Override
    public View onCreateCoverView(Context context) {
        coverBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_close_cover, null, false);
        coverBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_CLOSE, null);
            }
        });
        return coverBinding.getRoot();
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public int getCoverLevel() {
        return levelMedium(10);
    }
}
