package com.view.caption;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.view.caption.bean.CaptionBean;

import java.lang.ref.WeakReference;

public class CaptionView extends RecyclerView {

    public static long TIME_AUTO_POLL = 10;
    private static final int VERTICA = 2;
    private static final int HORIZONTAL = 1;
    private AutoPollTask autoPollTask;
    private boolean running; //表示是否正在自动轮询
    private boolean canRun;//表示是否可以自动轮询
    private Context mContext;
    private CaptionViewAdapter mAdapter;

    private CaptionBean mData = new CaptionBean();

    private LinearLayoutManager mLayoutManager;

    public CaptionView(Context context) {
        super(context);
        mContext = context;
        init(null);
    }


    public CaptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initAttribute(attrs);
        autoPollTask = new AutoPollTask(this);
        mLayoutManager = new LinearLayoutManager(mContext);
        if (mData.getDirection() == VERTICA) {
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        } else {
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        }
        setLayoutManager(mLayoutManager);
    }

    @SuppressLint("ResourceAsColor")
    private void initAttribute(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CaptionView);
        mData.setTextColor(a.getColor(R.styleable.CaptionView_textColor, R.color.white));
        mData.setBorderColor(a.getColor(R.styleable.CaptionView_borderColor, R.color.black));
        mData.setDirection(a.getInt(R.styleable.CaptionView_direction, 1));
        mData.setTextSize(a.getInt(R.styleable.CaptionView_textSize, 18));
        mData.setFontRotation(a.getInt(R.styleable.CaptionView_fontRotation, 0));
        mData.setLoop(a.getInt(R.styleable.CaptionView_loop, -1));
        mData.setShowBorder(a.getBoolean(R.styleable.CaptionView_borderEnable, false));
        a.recycle();
    }


    public void setData(CaptionBean bean) {
        if (bean == null) {
            return;
        }
        mergeData(bean);
        mAdapter = new CaptionViewAdapter(mContext, mData);
        setAdapter(mAdapter);
    }

    public void setText(String content) {
        mData.setContent(content);
        mAdapter = new CaptionViewAdapter(mContext, mData);
        setAdapter(mAdapter);
    }

    //开启:如果正在运行,先停止->再开启
    public void start() {
        if (running)
            stop();
        canRun = true;
        running = true;
        postDelayed(autoPollTask, TIME_AUTO_POLL);
    }

    public void stop() {
        running = false;
        removeCallbacks(autoPollTask);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (canRun)
                    start();
                break;
        }
        return super.onTouchEvent(e);
    }

    class AutoPollTask implements Runnable {
        private final WeakReference<CaptionView> mReference;

        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(CaptionView reference) {
            this.mReference = new WeakReference<CaptionView>(reference);
        }

        @Override
        public void run() {
            CaptionView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                if (mData.getDirection() == VERTICA) {
                    recyclerView.scrollBy(2, mData.getSpeed());
                } else {
                    recyclerView.scrollBy(mData.getSpeed(), 2);
                }
                recyclerView.postDelayed(recyclerView.autoPollTask, TIME_AUTO_POLL);
            }
        }
    }

    private void mergeData(CaptionBean bean) {
        if (bean == null) {
            return;
        }
        mData.setBorderColor(bean.getBorderColor() == 0 ? R.color.black : bean.getBorderColor());
        mData.setTextColor(bean.getTextColor() == 0 ? R.color.white : bean.getTextColor());
        mData.setTextSize(bean.getTextSize() == 0 ? 14 : bean.getTextSize());
        mData.setShowBorder(bean.isShowBorder());
        mData.setFontRotation(bean.getFontRotation());
        mData.setDisplaySpace(bean.getDisplaySpace() == 0 ? 8 : bean.getDisplaySpace());
        mData.setSpeed(bean.getSpeed());
        mData.setDirection(bean.getDirection() == 0 ? 1 : bean.getDirection());
        mData.setContent(bean.getContent());
        mData.setFontPath(bean.getFontPath());
    }
}


