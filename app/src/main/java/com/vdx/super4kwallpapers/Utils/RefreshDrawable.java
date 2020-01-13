package com.vdx.super4kwallpapers.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.TypedValue;

import com.baoyz.widget.PullRefreshLayout;

public class RefreshDrawable extends com.baoyz.widget.RefreshDrawable {

    private RectF mBounds;
    private float mWidth;

    private float mHeight;
    private float mCenterX;
    private float mCenterY;
    private float mPercent;
    private final float mMaxAngle = (float) (180f * .85);
    private final float mRadius = dp2px(16);
    private final float mLineLength = (float) (Math.PI / 180 * mMaxAngle * mRadius);
    private final float mLineWidth = dp2px(4);
    private final float mArrowLength = (int) (mLineLength * .15);
    private final float mArrowAngle = (float) (Math.PI / 180 * 25);
    private final float mArrowXSpace = (int) (mArrowLength * Math.sin(mArrowAngle));
    private final float mArrowYSpace = (int) (mArrowLength * Math.cos(mArrowAngle));
    private final Paint mPaint = new Paint();
    private int mOffset;
    private boolean mRunning;
    private float mDegrees;
    private int ofsetvalue = 3;

    public RefreshDrawable(Context context, PullRefreshLayout layout) {
        super(context, layout);

        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mHeight = getRefreshLayout().getFinalOffset();
        mWidth = mHeight;
        mBounds = new RectF(bounds.width() / 2 - mWidth / 2, bounds.top - mHeight / 2, bounds.width() / 2 + mWidth / 2, bounds.top + mHeight / 2);
        mCenterX = mBounds.centerX();
        mCenterY = mBounds.centerY();
    }

    @Override
    public void setPercent(float percent) {
        mPercent = percent;
        invalidateSelf();
    }

    @Override
    public void setColorSchemeColors(int[] colorSchemeColors) {
        if (colorSchemeColors != null && colorSchemeColors.length > 0) {
            mPaint.setColor(colorSchemeColors[0]);
        }
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mOffset += offset + ofsetvalue;
        invalidateSelf();
    }

    @Override
    public void start() {
        mRunning = true;
        mDegrees = 0;
        invalidateSelf();
    }

    @Override
    public void stop() {
        mRunning = false;
        ofsetvalue = 0;
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.save();

        canvas.translate(0, mOffset / 2);
        canvas.clipRect(mBounds);

        if (mOffset > mHeight && !isRunning()) {
            canvas.rotate((mOffset - mHeight) / mHeight * 360, mCenterX, mCenterY);
        }

        if (isRunning()) {
            canvas.rotate(mDegrees, mCenterX, mCenterY);
            mDegrees = mDegrees < 360 ? mDegrees + 10 : 0;
            invalidateSelf();
        }

        if (mPercent <= .5f) {

            float percent = mPercent / .5f;

            // left
            float leftX = mCenterX - mRadius;
            float leftY = mCenterY + mLineLength - mLineLength * percent;

            canvas.drawLine(leftX, leftY, leftX, leftY + mLineLength, mPaint);

            // left arrow
            canvas.drawLine(leftX, leftY, leftX - mArrowXSpace, leftY + mArrowYSpace, mPaint);

            // right
            float rightX = mCenterX + mRadius;
            float rightY = mCenterY - mLineLength + mLineLength * percent;

            canvas.drawLine(rightX, rightY, rightX, rightY - mLineLength, mPaint);

            // right arrow
            canvas.drawLine(rightX, rightY, rightX + mArrowXSpace, rightY - mArrowYSpace, mPaint);

        } else {

            float percent = (mPercent - .5f) / .5f;
            // left
            float leftX = mCenterX - mRadius;
            float leftY = mCenterY;

            canvas.drawLine(leftX, leftY, leftX, leftY + mLineLength - mLineLength * percent, mPaint);

            RectF oval = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);

            canvas.drawArc(oval, 180, mMaxAngle * percent, false, mPaint);

            // right
            float rightX = mCenterX + mRadius;
            float rightY = mCenterY;

            canvas.drawLine(rightX, rightY, rightX, rightY - mLineLength + mLineLength * percent, mPaint);

            canvas.drawArc(oval, 0, mMaxAngle * percent, false, mPaint);

            // arrow
            canvas.save();

//            canvas.translate(mCenterX, mCenterY);
            canvas.rotate(mMaxAngle * percent, mCenterX, mCenterY);
//            canvas.translate(-mCenterX, -mCenterY);

            // left arrow
            canvas.drawLine(leftX, leftY, leftX - mArrowXSpace, leftY + mArrowYSpace, mPaint);

            // right arrow
            canvas.drawLine(rightX, rightY, rightX + mArrowXSpace, rightY - mArrowYSpace, mPaint);

            canvas.restore();
        }

        canvas.restore();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
