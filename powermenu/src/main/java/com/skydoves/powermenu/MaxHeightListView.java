package com.skydoves.powermenu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;

/**
 * @author EKwong
 *
 * 可以限制最大高度的ListView类
 */
public class MaxHeightListView extends ListView {

    /**
     * max height of this ListView
     */
    private int mMaxHeight;
    /**
     *
     */
    private static final int DEFAULT_MAX_HEIGHT = -1;

    public MaxHeightListView(Context context) {
        this(context,null, R.attr.MaxHeightStyle);
    }

    public MaxHeightListView(Context context, AttributeSet attrs) {
        this(context,attrs, R.attr.MaxHeightStyle);
    }

    public MaxHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, null, 0);
    }

    public MaxHeightListView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
        super(context, attrs, defStyle);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightListView, R.attr.MaxHeightStyle, 0);
        mMaxHeight = a.getColor(R.styleable.MaxHeightListView_MaxHeight, DEFAULT_MAX_HEIGHT);
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxHeight > -1) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(dipToPixels(mMaxHeight), MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int dipToPixels(int dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }

    public void setMaxHeight(int maxHeight){
        this.mMaxHeight = maxHeight;
    }
}
