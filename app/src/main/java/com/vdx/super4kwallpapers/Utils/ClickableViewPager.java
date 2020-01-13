package com.vdx.super4kwallpapers.Utils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class ClickableViewPager extends ViewPager {

    @Nullable
    private OnClickListener onClickListener;

    public ClickableViewPager(final Context context) {
        this(context, null);
    }

    public ClickableViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        final GestureDetector onSingleTapConfirmedGestureDetector =
                new GestureDetector(context, new OnSingleTapConfirmedGestureListener(this));
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onSingleTapConfirmedGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }

    @Override
    public void setOnClickListener(@Nullable final View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private class OnSingleTapConfirmedGestureListener extends GestureDetector.SimpleOnGestureListener {

        @NonNull
        private final View view;

        public OnSingleTapConfirmedGestureListener(@NonNull final View view) {
            this.view = view;
        }

        @Override
        public boolean onSingleTapConfirmed(final MotionEvent e) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            return true;
        }
    }
}