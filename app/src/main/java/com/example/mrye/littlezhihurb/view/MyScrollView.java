package com.example.mrye.littlezhihurb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义可检测上下滑动的ScrollView
 */

public class MyScrollView extends ScrollView {

    private static final String TAG = MyScrollView.class.getSimpleName();
    public OnScrollListener listener;

    public void setListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            if (t > 0 && oldt > 0) {
                if (t - oldt >= 0) {
                    //上滑
                    listener.onScrollUp();
                }
                if (oldt - t >= 0) {
                    //下滑
                    listener.onScrollDown();

                }
            }

        }
    }

    public interface OnScrollListener {
        void onScrollUp();

        void onScrollDown();
    }
}
