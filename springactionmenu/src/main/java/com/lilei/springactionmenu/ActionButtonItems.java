package com.lilei.springactionmenu;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

/**
 * Created by lilei on 2016/11/6.
 */

public class ActionButtonItems extends View implements ValueAnimator.AnimatorUpdateListener{

    private int circleRadius;
    private int dimens;
    private int expandDirect;
    private Bitmap mBitmap;
    private Bitmap mOnBitmap;
    private boolean isSwitchButton;
    private int normalColor;
    private int pressColor;
    private int tag;

    private Paint mPaint;
    private Path mPath;
    private float factor;
    private float offSet;
    private int extra;
    private boolean isPress;
    private boolean isOpen;

    private OnActionItemClickListener itemClickListener;


    public ActionButtonItems(Context context,int tag, int circleRadius, int dimens, int expandDirect, int iconId, int normalColor, int pressColor) {

        this(context, tag, circleRadius, dimens, expandDirect, normalColor, pressColor, false);
        mBitmap = ((BitmapDrawable)getResources().getDrawable(iconId)).getBitmap();
    }

    public ActionButtonItems(Context context,int tag, int circleRadius, int dimens,
                             int expandDirect, int normalColor, int pressColor, boolean isSwitchButton) {

        this(context);
        this.tag = tag;
        this.circleRadius = circleRadius;
        this.dimens = dimens;
        this.expandDirect = expandDirect;
        this.normalColor = normalColor;
        this.pressColor = pressColor;
        this.isSwitchButton = isSwitchButton;
        if (!isSwitchButton) {
            setAlpha(0);
            setVisibility(View.GONE);
        }
    }

    public ActionButtonItems(Context context) {
        this(context, null);
    }

    public ActionButtonItems(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionButtonItems(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
        mPaint.setColor(normalColor);

        mPath = new Path();
        extra = (int)(circleRadius * 2 * factor / 5);
        offSet = circleRadius * 2 / 3.6f;
        factor = 0;
        isPress = false;
        isOpen = false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();

        if (isPress) {
            mPaint.setColor(pressColor);
        } else {
            mPaint.setColor(normalColor);
        }
        extra = (int)(circleRadius * 2 * factor / 5);

        int xA = 0,xB = 0,xC = 0,xD = 0,yA = 0,yB = 0,yC = 0,yD = 0;
        Rect mRect = null;
        switch (expandDirect) {
            case ActionMenu.expandDirectTop:
                xA = circleRadius * 3 / 2;
                xB = circleRadius * 5 / 2 - extra;
                xC = circleRadius * 3 / 2;
                xD = circleRadius / 2 + extra;
                yA = 0;
                yB = circleRadius;
                yC = circleRadius * 2 + extra;
                yD = circleRadius;

                if (isSwitchButton) {
                    yA += dimens - extra;
                    yB += dimens;
                    yC += dimens - extra;
                    yD += dimens;
                    mRect = new Rect(circleRadius, circleRadius / 2 + dimens  - extra, circleRadius * 2, circleRadius * 3/2  + dimens);
                } else {
                    mRect = new Rect(circleRadius, circleRadius / 2, circleRadius * 2, circleRadius * 3/2 + extra);
                }
                break;
            case ActionMenu.expandDirectDown:
                xA = circleRadius * 3 / 2;
                xB = circleRadius * 5 / 2 - extra;
                xC = circleRadius * 3 / 2;
                xD = circleRadius / 2 + extra;
                yA = 0 - extra + dimens;
                yB = circleRadius + dimens;
                yC = circleRadius * 2 + dimens;
                yD = circleRadius + dimens;

                if (isSwitchButton) {
                    yA -= dimens - extra;
                    yB -= dimens;
                    yC -= dimens - extra;
                    yD -= dimens;
                    mRect = new Rect(circleRadius, circleRadius / 2, circleRadius * 2, circleRadius * 3/2 + extra);
                } else {
                    mRect = new Rect(circleRadius, circleRadius / 2 + dimens - extra, circleRadius * 2, circleRadius * 3/2 + dimens);
                }
                break;
            case ActionMenu.expandDirectLeft:
                xA = circleRadius;
                xB = circleRadius * 2 + extra;
                xC = circleRadius;
                xD = 0;
                yA = circleRadius / 2 + extra;
                yB = circleRadius * 3 / 2;
                yC = circleRadius * 5 / 2 - extra;
                yD = circleRadius * 3 / 2;

                if (isSwitchButton) {
                    xA += dimens;
                    xB += dimens - extra;
                    xC += dimens;
                    xD += dimens - extra;
                    mRect = new Rect(circleRadius / 2 + dimens - extra, circleRadius, circleRadius * 3 / 2 + dimens, circleRadius * 2);
                } else {
                    mRect = new Rect(circleRadius / 2, circleRadius, circleRadius * 3 / 2 + extra, circleRadius * 2);
                }
                break;
            case ActionMenu.expandDirectRight:
                xA = circleRadius + dimens;
                xB = circleRadius * 2 + dimens;
                xC = circleRadius + dimens;
                xD = 0 - extra + dimens;
                yA = circleRadius / 2 + extra;
                yB = circleRadius * 3 / 2;
                yC = circleRadius * 5 / 2 - extra;
                yD = circleRadius * 3 / 2;

                if (isSwitchButton) {
                    xA -= dimens;
                    xB -= dimens - extra;
                    xC -= dimens;
                    xD -= dimens - extra;
                    mRect = new Rect(circleRadius / 2, circleRadius, circleRadius * 3 / 2 + extra, circleRadius * 2);
                }
                else {
                    mRect = new Rect(circleRadius / 2 + dimens - extra, circleRadius, circleRadius * 3 / 2 + dimens, circleRadius * 2);
                }
                break;
        }
        offSet = circleRadius * 2 / 3.6f;
        mPath.moveTo(xA,yA);
        mPath.cubicTo(xA + offSet, yA, xB, yB  - offSet, xB, yB);
        mPath.cubicTo(xB, yB + offSet, xC + offSet, yC, xC, yC);
        mPath.cubicTo(xC - offSet, yC, xD, yD  + offSet, xD, yD);
        mPath.cubicTo(xD, yD - offSet, xA - offSet , yA , xA, yA);

        canvas.drawPath(mPath, mPaint);


        // bitmap
        mPaint.setFilterBitmap(true);

        if (mBitmap != null) {
            canvas.drawBitmap(isSwitchButton && isOpen ? mOnBitmap : mBitmap, null, mRect, mPaint);
        }
        super.onDraw(canvas);
    }


    public void startSpringAnimation(long time, float startValue, float endValue) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new FloatEvaluator(time, startValue,endValue), startValue, endValue);
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isOpen) {
                    if (((ActionMenu)getParent()).getChildCount() == (tag + 1) && itemClickListener != null) {
                        itemClickListener.onAnimationEnd(isOpen);
                    }
                } else {
                    if (isSwitchButton && itemClickListener != null) {
                        itemClickListener.onAnimationEnd(isOpen);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.start();
    }



    public void startFactorAnimation(final long time, final float startValue, final float endValue) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startValue, endValue);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startSpringAnimation(time * 5, endValue, startValue);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.addUpdateListener(this);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(time);
        valueAnimator.start();
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        factor = (float)animation.getAnimatedValue();
        invalidate();
    }

    // click listener

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPress = true;
                invalidate();
                return true;

            case MotionEvent.ACTION_MOVE:
                
                break;

            case MotionEvent.ACTION_UP:
                if (!isPress) {
                    return true;
                }
                invalidate();
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(tag);
                }

                if (isSwitchButton && !isOpen) {
                    ((ActionMenu)getParent()).openMenu();
                } else if ((isSwitchButton && isOpen) || !isSwitchButton){
                    ((ActionMenu)getParent()).closeMenu();
                }
                isPress = false;
                return true;
        }


        return super.onTouchEvent(event);
    }

    public void setItemClickListener(OnActionItemClickListener itemClickListener) {
        if (itemClickListener == null) {
            return;
        }
        this.itemClickListener = itemClickListener;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setmBitmapIcon(int menuIcon, int menuOnIcon) {
        if (menuIcon == -1) {
            return;
        }
        mBitmap = ((BitmapDrawable)getResources().getDrawable(menuIcon)).getBitmap();
        mOnBitmap = ((BitmapDrawable)getResources().getDrawable(menuOnIcon)).getBitmap();
    }
}
