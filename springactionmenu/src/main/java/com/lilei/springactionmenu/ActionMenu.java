package com.lilei.springactionmenu;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by lilei on 2016/11/6.
 */

public class ActionMenu extends ViewGroup{

    public static final int expandDirectTop = 0;        // top
    public static final int expandDirectDown = 1;        // down
    public static final int expandDirectLeft = 2;        // left
    public static final int expandDirectRight = 3;        // right

    private Context context;
    private int expandDirect = 0;
    private int circleRadius;
    private int dimens;
    private long duration;
    private int normalColor;
    private int pressColor;
    private int menuIcon;
    private int menuOnIcon;

    private int childViewCount;             // button count
    private boolean isOpen = false;

    private OnActionItemClickListener itemClickListener;




    public ActionMenu(Context context) {
        this(context, null);
    }

    public ActionMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < childViewCount; i++) {
            ActionButtonItems items = (ActionButtonItems)getChildAt(i);

            switch (expandDirect) {

                case expandDirectTop:
                    l = 0;
                    t = (circleRadius * 2 + dimens) * (childViewCount - 1) + circleRadius * 2;
                    if (i == 0) {
                        t -= dimens;
                    }
                    r = circleRadius * 3;
                    b = t + circleRadius * 2 + dimens;
                    break;

                case expandDirectDown:
                    l = 0;
                    t = dimens * (- 1);
                    if (i == 0) {
                        t += dimens;
                    }
                    r = circleRadius * 3;
                    b = t + circleRadius * 2 + dimens;
                    break;

                case expandDirectLeft:
                    l = (circleRadius * 2 + dimens) * (childViewCount - 1) + circleRadius * 2;
                    if (i == 0) {
                        l -= dimens;
                    }
                    t = 0;
                    r = l + circleRadius * 2 + dimens;
                    b = circleRadius * 3;
                    break;

                case expandDirectRight:
                    l = dimens * (- 1);
                    if (i == 0) {
                        l += dimens;
                    }
                    t = 0;
                    r = l + circleRadius * 2 + dimens;
                    b = circleRadius * 3;
                    break;
            }
            items.layout(l, t, r, b);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width, height;
        if (expandDirect == expandDirectTop || expandDirect ==expandDirectDown) {
            width = circleRadius * 3;
            height = (childViewCount - 1) * (circleRadius * 2 + dimens) + circleRadius * 2 * 2;
        } else {
            width = (childViewCount - 1) * (circleRadius * 2 + dimens) + circleRadius * 2 * 2;
            height = circleRadius * 3;
        }
        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();
    //    setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0), resolveSizeAndState(height, heightMeasureSpec, 0));
        setMeasuredDimension(width, height);
    }

    private void init(Context context, AttributeSet attrs) {

        // set xml property
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionMenu);
        circleRadius = (int) typedArray.getDimension(R.styleable.ActionMenu_circleRadius, 30.f);
        dimens = (int) typedArray.getDimension(R.styleable.ActionMenu_dimens, 30.f);
        duration = typedArray.getInteger(R.styleable.ActionMenu_animationDuration, 500);
        expandDirect = typedArray.getInteger(R.styleable.ActionMenu_expandDirect, 0);
        normalColor = typedArray.getColor(R.styleable.ActionMenu_buttonNormalColor, Color.RED);
        pressColor = typedArray.getColor(R.styleable.ActionMenu_buttonPressColor, Color.RED);
        menuIcon = typedArray.getResourceId(R.styleable.ActionMenu_actionMenuIcon, -1);
        menuOnIcon = typedArray.getResourceId(R.styleable.ActionMenu_actionMenuOnIcon, menuIcon);

        // add root button
        addView(new ActionButtonItems(context, 0, circleRadius, dimens, expandDirect, normalColor, pressColor, true));
        ((ActionButtonItems)getChildAt(0)).setmBitmapIcon(menuIcon, menuOnIcon);
        childViewCount = 1;
    }


    /**
     * buttonItem open animation
     */
    private void buttonItemOpenAnimation(int index,ActionButtonItems view) {
        if (index == 0) {
            view.startFactorAnimation(duration / 6, 0, 1);
        } else {
            ViewPropertyAnimator propertyAnimator = view.animate().alpha(1).
                    setInterpolator(new OvershootInterpolator()).setDuration(duration / 3);

            switch (expandDirect) {
                case expandDirectTop:
                    propertyAnimator.y((circleRadius * 2 + dimens) * (childViewCount - 1 - index) + circleRadius * 2);
                    break;
                case expandDirectDown:
                    propertyAnimator.y(circleRadius * 2 * index + dimens * (index - 1));
                    break;
                case expandDirectLeft:
                    propertyAnimator.x((circleRadius * 2 + dimens) * (childViewCount - 1 - index) + circleRadius * 2);
                    break;
                case expandDirectRight:
                    propertyAnimator.x(circleRadius * 2 * index + dimens * (index - 1));
                    break;
            }
            if (isOpen) {
                view.setVisibility(View.VISIBLE);
            }

            propertyAnimator.start();

            // start factor animation
            view.startFactorAnimation(duration / 6, 0, -1);
        }
        view.setOpen(isOpen);
    }

    /**
     * buttonItem close animation
     */
    private void buttonItemCloseAnimation(int index, final ActionButtonItems view) {
        if (index == 0) {
            view.startFactorAnimation(duration / 6, 0, -1);
        } else {
            ViewPropertyAnimator propertyAnimator = view.animate().alpha(0).setDuration(duration / 3);

            switch (expandDirect) {
                case expandDirectTop:
                    propertyAnimator.y((circleRadius * 2 + dimens) * (childViewCount - 1) + circleRadius * 2);
                    break;
                case expandDirectDown:
                    propertyAnimator.y(dimens * (- 1));
                    break;
                case expandDirectLeft:
                    propertyAnimator.x((circleRadius * 2 + dimens) * (childViewCount - 1) + circleRadius * 2);
                    break;
                case expandDirectRight:
                    propertyAnimator.x(dimens * (- 1));
                    break;
            }

            propertyAnimator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!isOpen) {
                        view.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

            propertyAnimator.start();
        }
        view.setOpen(isOpen);
    }



    /*********************** public *********************/
    /****************************************************/

    /**
     * open menu
     */
    public void openMenu() {
        isOpen = true;
        for (int i = 0; i < childViewCount; i++) {
            buttonItemOpenAnimation(i, (ActionButtonItems)getChildAt(i));
        }
    }

    /**
     * close menu
     */
    public void closeMenu() {
        isOpen = false;
        for (int i = 0; i < childViewCount; i++) {
            buttonItemCloseAnimation(i, (ActionButtonItems)getChildAt(i));
        }
    }


    /**
     * add item button
     * @param drawableIcon button icon drawable
     */
    public void addView(int drawableIcon) {
        addView(drawableIcon, normalColor, pressColor);
    }

    public void addView(int drawableIcon, int normalColor, int pressColor) {
        addView(new ActionButtonItems(context, childViewCount, circleRadius, dimens, expandDirect, drawableIcon, normalColor, pressColor));
        childViewCount ++;
    }


    /**
     * check the menu is or not open
     * @return isOpen
     */
    public boolean isOpen() {
        return isOpen;
    }


    /**
     * set item click listener
     */
    public void setItemClickListener(OnActionItemClickListener itemClickListener) {
        if (itemClickListener == null) {
            return;
        }
        this.itemClickListener = itemClickListener;
        for (int i = 0; i < childViewCount; i++) {
            ActionButtonItems view = (ActionButtonItems) getChildAt(i);
            view.setItemClickListener(itemClickListener);
        }
    }


}
