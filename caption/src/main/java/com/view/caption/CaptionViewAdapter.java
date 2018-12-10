package com.view.caption;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.view.caption.bean.CaptionBean;

import java.util.ArrayList;
import java.util.List;

public class CaptionViewAdapter extends RecyclerView.Adapter<CaptionViewAdapter.BaseViewHolder> {
    private final CaptionBean mData;
    private Context mContext;
    private Typeface mTypeface;
    private List<String> mContentList = new ArrayList<>();

    public CaptionViewAdapter(Context context, CaptionBean data) {
        this.mData = data;
        mContext = context;
        initData(data);
    }

    private void initData(CaptionBean data) {
        if (data == null) {
            return;
        }
        String content = data.getContent();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        for (int i = 0; i < content.length(); i++) {
            mContentList.add(content.substring(i, i + 1));
        }
        for (int i = 0; i < data.getDisplaySpace(); i++) {
            mContentList.add(" ");
        }
        if (!TextUtils.isEmpty(data.getFontPath())) {
            //得到AssetManager
            AssetManager mgr = mContext.getAssets();
            //根据路径得到Typeface
            mTypeface = Typeface.createFromAsset(mgr, data.getFontPath());
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auto_poll, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        String data = mContentList.get(position % mContentList.size());
        holder.textView.setText(data);

    }

    @Override
    public int getItemCount() {
        if (mContentList == null || mContentList.size() <= 0) {
            return 0;
        }
        return Integer.MAX_VALUE;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        StrokeTextView textView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
            if (mTypeface != null) {
                textView.setTypeface(mTypeface);
            }
            textView.setTextColor(mContext.getResources().getColor(mData.getTextColor()));
            textView.setBorderEnable(true);
            textView.setBorderColor(mContext.getResources().getColor(mData.getBorderColor()));
            textView.setTextSize(mData.getTextSize());
            textView.setRotation(mData.getFontRotation());
        }
    }
}