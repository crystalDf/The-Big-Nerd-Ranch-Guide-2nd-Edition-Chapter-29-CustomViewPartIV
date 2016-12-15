package com.star.customviewpartiv;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CustomTitleView extends View {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private Rect mBounds;
    private Paint mPaint;

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomTitleView, defStyleAttr, 0);

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColors:
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize = typedArray.getDimensionPixelSize(
                            attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_SP, 16,
                                    getResources().getDisplayMetrics()
                            )
                    );
                    break;
            }
        }

        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);

        mBounds = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBounds);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = "hello world";
                requestLayout();
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBounds);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {

            if (mBounds != null) {
                width = getPaddingLeft() + mBounds.width() + getPaddingRight();
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {

            if (mBounds != null) {
                height = getPaddingTop() + mBounds.height() + getPaddingBottom();
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText,
                (getWidth() - mBounds.width()) / 2,
                (getHeight() + mBounds.height()) / 2, mPaint);
    }

}
