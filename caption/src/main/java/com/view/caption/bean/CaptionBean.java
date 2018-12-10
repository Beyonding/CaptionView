package com.view.caption.bean;

import com.view.caption.R;

import java.io.Serializable;

/**
 * Created by guopu on 2018/12/7.
 */

public class CaptionBean implements Serializable {

    private static final int VERTICA = 2;
    private static final int HORIZONTAL = 1;

    private int direction = HORIZONTAL;
    private String content;
    private int textColor = R.color.white;
    private int borderColor = R.color.black;
    private int speed = 2;
    private String fontPath;
    private int loop = -1;
    private int displaySpace = 8;
    private boolean showBorder;
    private int textSize=12;
    private int fontRotation;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public int getDisplaySpace() {
        return displaySpace;
    }

    public void setDisplaySpace(int displaySpace) {
        this.displaySpace = displaySpace;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getFontRotation() {
        return fontRotation;
    }

    public void setFontRotation(int fontRotation) {
        this.fontRotation = fontRotation;
    }
}
