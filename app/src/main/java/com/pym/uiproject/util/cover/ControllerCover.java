package com.pym.uiproject.util.cover;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.BundlePool;
import com.kk.taurus.playerbase.event.EventKey;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.log.PLog;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.player.OnTimerUpdateListener;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.kk.taurus.playerbase.receiver.IReceiverGroup;
import com.kk.taurus.playerbase.touch.OnTouchGestureListener;
import com.kk.taurus.playerbase.utils.TimeUtil;
import com.pym.uiproject.R;
import com.pym.uiproject.databinding.LayoutControllerCoverBinding;
import com.pym.uiproject.util.play.DataInter;
/**
 * Created by Taurus on 2018/4/15.
 */

public class ControllerCover extends BaseCover implements OnTimerUpdateListener, OnTouchGestureListener {

    private final int MSG_CODE_DELAY_HIDDEN_CONTROLLER = 101;



    private int mBufferPercentage;

    private int mSeekProgress = -1;

    private boolean mTimerUpdateProgressEnable = true;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CODE_DELAY_HIDDEN_CONTROLLER:
                    PLog.d(getTag().toString(), "msg_delay_hidden...");
                    setControllerState(false);
                    break;
            }
        }
    };

    private boolean mGestureEnable = true;

    private String mTimeFormat;

    private boolean mControllerTopEnable;
    private ObjectAnimator mBottomAnimator;
    private ObjectAnimator mTopAnimator;
    private LayoutControllerCoverBinding coverBinding;

    public ControllerCover(Context context) {
        super(context);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
coverBinding.coverPlayerControllerSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        getGroupValue().registerOnGroupValueUpdateListener(mOnGroupValueUpdateListener);

    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        DataSource dataSource = getGroupValue().get(DataInter.Key.KEY_DATA_SOURCE);
        setTitle(dataSource);

        boolean topEnable = getGroupValue().getBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, false);
        mControllerTopEnable = topEnable;
        if(!topEnable){
            setTopContainerState(false);
        }

        boolean screenSwitchEnable = getGroupValue().getBoolean(DataInter.Key.KEY_CONTROLLER_SCREEN_SWITCH_ENABLE, true);
        setScreenSwitchEnable(screenSwitchEnable);
    }

    @Override
    protected void onCoverDetachedToWindow() {
        super.onCoverDetachedToWindow();
        coverBinding.coverPlayerControllerTopContainer.setVisibility(View.GONE);
        coverBinding.coverPlayerControllerBottomContainer.setVisibility(View.GONE);
        removeDelayHiddenMessage();
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();

        cancelTopAnimation();
        cancelBottomAnimation();

        getGroupValue().unregisterOnGroupValueUpdateListener(mOnGroupValueUpdateListener);
        removeDelayHiddenMessage();
        mHandler.removeCallbacks(mSeekEventRunnable);
        onViewClick(coverBinding.coverPlayerControllerImageViewBackIcon);
        onViewClick(coverBinding.coverPlayerControllerImageViewPlayState);
        onViewClick(coverBinding.coverPlayerControllerImageViewSwitchScreen);
    }

    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.cover_player_controller_image_view_back_icon:
                notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_BACK, null);
                break;
            case R.id.cover_player_controller_image_view_play_state:
                boolean selected =  coverBinding.coverPlayerControllerImageViewPlayState.isSelected();
                if(selected){
                    requestResume(null);
                }else{
                    requestPause(null);
                }
                coverBinding.coverPlayerControllerImageViewPlayState.setSelected(!selected);
                break;
            case R.id.cover_player_controller_image_view_switch_screen:
                notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN, null);
                break;
        }
    }

    private IReceiverGroup.OnGroupValueUpdateListener mOnGroupValueUpdateListener =
            new IReceiverGroup.OnGroupValueUpdateListener() {
        @Override
        public String[] filterKeys() {
            return new String[]{
                    DataInter.Key.KEY_COMPLETE_SHOW,
                    DataInter.Key.KEY_TIMER_UPDATE_ENABLE,
                    DataInter.Key.KEY_DATA_SOURCE,
                    DataInter.Key.KEY_IS_LANDSCAPE,
                    DataInter.Key.KEY_CONTROLLER_TOP_ENABLE};
        }

        @Override
        public void onValueUpdate(String key, Object value) {
            if(key.equals(DataInter.Key.KEY_COMPLETE_SHOW)){
                boolean show = (boolean) value;
                if(show){
                    setControllerState(false);
                }
                setGestureEnable(!show);
            }else if(key.equals(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE)){
                mControllerTopEnable = (boolean) value;
                if(!mControllerTopEnable){
                    setTopContainerState(false);
                }
            }else if(key.equals(DataInter.Key.KEY_IS_LANDSCAPE)){
                setSwitchScreenIcon((Boolean) value);
            }else if(key.equals(DataInter.Key.KEY_TIMER_UPDATE_ENABLE)){
                mTimerUpdateProgressEnable = (boolean) value;
            }else if(key.equals(DataInter.Key.KEY_DATA_SOURCE)){
                DataSource dataSource = (DataSource) value;
                setTitle(dataSource);
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser)
                updateUI(progress, seekBar.getMax());
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            sendSeekEvent(seekBar.getProgress());
        }
    };

    private void sendSeekEvent(int progress){
        mTimerUpdateProgressEnable = false;
        mSeekProgress = progress;
        mHandler.removeCallbacks(mSeekEventRunnable);
        mHandler.postDelayed(mSeekEventRunnable, 300);
    }

    private Runnable mSeekEventRunnable = new Runnable() {
        @Override
        public void run() {
            if(mSeekProgress < 0)
                return;
            Bundle bundle = BundlePool.obtain();
            bundle.putInt(EventKey.INT_DATA, mSeekProgress);
            requestSeek(bundle);
        }
    };

    private void setTitle(DataSource dataSource){
        if(dataSource!=null){
            String title = dataSource.getTitle();
            if(!TextUtils.isEmpty(title)){
                setTitle(title);
                return;
            }
            String data = dataSource.getData();
            if(!TextUtils.isEmpty(data)){
                setTitle(data);
            }
        }
    }

    private void setTitle(String text){
        coverBinding.coverPlayerControllerTextViewVideoTitle.setText(text);
    }

    private void setSwitchScreenIcon(boolean isFullScreen){
        coverBinding.coverPlayerControllerImageViewSwitchScreen.setImageResource(isFullScreen?R.mipmap.icon_exit_full_screen:R.mipmap.icon_full_screen);
    }

    private void setScreenSwitchEnable(boolean screenSwitchEnable) {
        coverBinding.coverPlayerControllerImageViewSwitchScreen.setVisibility(screenSwitchEnable?View.VISIBLE:View.GONE);
    }

    private void setGestureEnable(boolean gestureEnable) {
        this.mGestureEnable = gestureEnable;
    }

    private void cancelTopAnimation(){
        if(mTopAnimator!=null){
            mTopAnimator.cancel();
            mTopAnimator.removeAllListeners();
            mTopAnimator.removeAllUpdateListeners();
        }
    }

    private void setTopContainerState(final boolean state){
        if(mControllerTopEnable){
            coverBinding.coverPlayerControllerTopContainer.clearAnimation();
            cancelTopAnimation();
            mTopAnimator = ObjectAnimator.ofFloat(coverBinding.coverPlayerControllerTopContainer,
                            "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
            mTopAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if(state){
                        coverBinding.coverPlayerControllerTopContainer.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if(!state){
                        coverBinding.coverPlayerControllerTopContainer.setVisibility(View.GONE);
                    }
                }
            });
            mTopAnimator.start();
        }else{
            coverBinding.coverPlayerControllerTopContainer.setVisibility(View.GONE);
        }
    }

    private void cancelBottomAnimation(){
        if(mBottomAnimator!=null){
            mBottomAnimator.cancel();
            mBottomAnimator.removeAllListeners();
            mBottomAnimator.removeAllUpdateListeners();
        }
    }

    private void setBottomContainerState(final boolean state){
        coverBinding.coverPlayerControllerBottomContainer.clearAnimation();
        cancelBottomAnimation();
        mBottomAnimator = ObjectAnimator.ofFloat(coverBinding.coverPlayerControllerBottomContainer,
                "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
        mBottomAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if(state){
                    coverBinding.coverPlayerControllerBottomContainer.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(!state){
                    coverBinding.coverPlayerControllerBottomContainer.setVisibility(View.GONE);
                }
            }
        });
        mBottomAnimator.start();
        if(state){
            PLog.d(getTag().toString(), "requestNotifyTimer...");
            requestNotifyTimer();
        }else{
            PLog.d(getTag().toString(), "requestStopTimer...");
            requestStopTimer();
        }
    }

    private void setControllerState(boolean state){
        if(state){
            sendDelayHiddenMessage();
        }else{
            removeDelayHiddenMessage();
        }
        setTopContainerState(state);
        setBottomContainerState(state);
    }

    private boolean isControllerShow(){
        return coverBinding.coverPlayerControllerBottomContainer.getVisibility()==View.VISIBLE;
    }

    private void toggleController(){
        if(isControllerShow()){
            setControllerState(false);
        }else{
            setControllerState(true);
        }
    }

    private void sendDelayHiddenMessage(){
        removeDelayHiddenMessage();
        mHandler.sendEmptyMessageDelayed(MSG_CODE_DELAY_HIDDEN_CONTROLLER, 5000);
    }

    private void removeDelayHiddenMessage(){
        mHandler.removeMessages(MSG_CODE_DELAY_HIDDEN_CONTROLLER);
    }

    private void setCurrTime(int curr){
        coverBinding.coverPlayerControllerTextViewCurrTime.setText(TimeUtil.getTime(mTimeFormat, curr));
    }

    private void setTotalTime(int duration){
        coverBinding.coverPlayerControllerTextViewTotalTime.setText(TimeUtil.getTime(mTimeFormat, duration));
    }

    private void setSeekProgress(int curr, int duration){
        coverBinding.coverPlayerControllerSeekBar.setMax(duration);
        coverBinding.coverPlayerControllerSeekBar.setProgress(curr);
        float secondProgress = mBufferPercentage * 1.0f/100 * duration;
        setSecondProgress((int) secondProgress);
    }

    private void setSecondProgress(int secondProgress){
        coverBinding.coverPlayerControllerSeekBar.setSecondaryProgress(secondProgress);
    }

    @Override
    public void onTimerUpdate(int curr, int duration, int bufferPercentage) {
        if(!mTimerUpdateProgressEnable)
            return;
        if(mTimeFormat==null){
            mTimeFormat = TimeUtil.getFormat(duration);
        }
        mBufferPercentage = bufferPercentage;
        updateUI(curr, duration);
    }

    private void updateUI(int curr, int duration) {
        setSeekProgress(curr, duration);
        setCurrTime(curr);
        setTotalTime(duration);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
                mBufferPercentage = 0;
                mTimeFormat = null;
                updateUI(0, 0);
                DataSource data = (DataSource) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
                getGroupValue().putObject(DataInter.Key.KEY_DATA_SOURCE, data);
                setTitle(data);
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE:
                int status = bundle.getInt(EventKey.INT_DATA);
                if(status== IPlayer.STATE_PAUSED){
                    coverBinding.coverPlayerControllerImageViewPlayState.setSelected(true);
                }else if(status== IPlayer.STATE_STARTED){
                    coverBinding.coverPlayerControllerImageViewPlayState.setSelected(false);
                }
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_COMPLETE:
                mTimerUpdateProgressEnable = true;
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public Bundle onPrivateEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case DataInter.PrivateEvent.EVENT_CODE_UPDATE_SEEK:
                if(bundle!=null){
                    int curr = bundle.getInt(EventKey.INT_ARG1);
                    int duration = bundle.getInt(EventKey.INT_ARG2);
                    updateUI(curr, duration);
                }
                break;
        }
        return null;
    }

    @Override
    public View onCreateCoverView(Context context) {
        coverBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_controller_cover, null, false);
        return coverBinding.getRoot();
    }

    @Override
    public int getCoverLevel() {
        return levelLow(1);
    }

    @Override
    public void onSingleTapUp(MotionEvent event) {
        if(!mGestureEnable)
            return;
        toggleController();
    }

    @Override
    public void onDoubleTap(MotionEvent event) {
    }

    @Override
    public void onDown(MotionEvent event) {
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(!mGestureEnable)
            return;
    }

    @Override
    public void onEndGesture() {
    }
}
