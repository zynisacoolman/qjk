package com.qijukeji.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/5/12.
 */

public class FindProgressBar extends ProgressBar {

    private String text;
    private Paint mPaint;

    public FindProgressBar(Context context) {
        super(context);
        initText();
    }

    public FindProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    public FindProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initText();
    }

    public synchronized void setProgress(int progress,int number) {
        super.setProgress(progress);
        setText(number);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    //初始化，画笔
    private void initText() {
        this.mPaint = new Paint();
        mPaint.setTextSize(12);
        this.mPaint.setColor(Color.WHITE);
    }

    //设置文字内容
    private void setText(int number) {
        int i = (number * 100) / this.getMax();
        this.text = "已报名：" + String.valueOf(i) + "人";
    }

}
