package com.view.caption;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

public class StrokeTextView extends AppCompatTextView {

    private TextPaint mPaint;
    private int mTextColor;
    private int mTextSize;
    private int mBorderColor;
    private boolean mIsShowBorder = false; // 默认不采用描边

    public StrokeTextView(Context context, int outerColor, int innnerColor) {
        super(context);
        mPaint = this.getPaint();
        this.mTextColor = innnerColor;
        this.mBorderColor = outerColor;

        // TODO Auto-generated constructor stub
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = this.getPaint();
        //获取自定义的XML属性名称
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CaptionView);
        //获取对应的属性值
        this.mTextColor = a.getColor(R.styleable.CaptionView_textColor, 0xffffff);
        this.mBorderColor = a.getColor(R.styleable.CaptionView_borderColor, 0x000000);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle, int outerColor, int innnerColor) {
        super(context, attrs, defStyle);
        mPaint = this.getPaint();
        this.mTextColor = innnerColor;
        this.mBorderColor = outerColor;
        // TODO Auto-generated constructor stub
    }


    public void setBorderColor(int color) {
        mBorderColor = color;
        invalidate();
    }

    public void setBorderEnable(boolean enable) {
        mIsShowBorder = enable;
        invalidate();
    }


    public void setTextColor(int color) {
        mTextColor = color;
        invalidate();
    }

    /**
     *
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsShowBorder) {
            // 描外层
            // super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            setTextColorUseReflection(mBorderColor);
            mPaint.setStrokeWidth(5); // 描边宽度
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
            mPaint.setFakeBoldText(true); // 外层text采用粗体
            mPaint.setShadowLayer(1, 0, 0, 0); // 字体的阴影效果，可以忽略
            super.onDraw(canvas);

            // 描内层，恢复原先的画笔

            // super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            setTextColorUseReflection(mTextColor);
            mPaint.setStrokeWidth(0);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setFakeBoldText(false);
            mPaint.setShadowLayer(0, 0, 0, 0);

        }
        super.onDraw(canvas);
    }

    /**
     * 使用反射的方法进行字体颜色的设置
     *
     * @param color
     */
    private void setTextColorUseReflection(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mPaint.setColor(color);
    }

}
