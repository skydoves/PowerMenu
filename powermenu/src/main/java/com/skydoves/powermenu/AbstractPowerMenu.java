/*
 * Copyright (C) 2018 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.powermenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
public abstract class AbstractPowerMenu<E, T extends MenuBaseAdapter> implements LifecycleObserver {

    protected View backgroundView;
    protected View menuView;
    protected CardView menuCard;

    protected PopupWindow backgroundWindow;
    protected PopupWindow menuWindow;

    protected LifecycleOwner lifecycleOwner;

    protected ListView menuListView;
    protected OnMenuItemClickListener menuItemClickListener;
    protected OnDismissedListener onDismissedListener;
    protected LayoutInflater layoutInflater;

    protected View headerView;
    protected View footerView;

    protected T adapter;

    protected boolean showBackground = true;
    protected boolean allowTouchBackground = false;
    protected boolean fixedHeight = false;

    protected boolean isShowing = false;

    protected int contentViewPadding;
    private boolean autoDismiss;
    private AdapterView.OnItemClickListener itemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                    if (autoDismiss) {
                        dismiss();
                    }
                    menuItemClickListener.onItemClick(index, menuListView.getItemAtPosition(index));
                }
            };
    private OnMenuItemClickListener onMenuItemClickListener =
            new OnMenuItemClickListener<E>() {
                @Override
                public void onItemClick(int position, E item) {
                    // empty body
                }
            };
    private View.OnClickListener background_clickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!allowTouchBackground) dismiss();
                }
            };
    private View.OnTouchListener onTouchListener =
            new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE && !showBackground) {
                        dismiss();
                        return true;
                    }
                    return false;
                }
            };
    private View.OnClickListener headerFooterClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // empty body
                }
            };

    protected AbstractPowerMenu(Context context) {
        initialize(context);
    }

    protected AbstractPowerMenu(Context context, AbstractMenuBuilder builder) {
        initialize(context);

        setShowBackground(builder.showBackground);
        setAnimation(builder.menuAnimation);
        setMenuRadius(builder.menuRadius);
        setMenuShadow(builder.menuShadow);
        setBackgroundColor(builder.backgroundColor);
        setBackgroundAlpha(builder.backgroundAlpha);
        setFocusable(builder.focusable);
        setIsClipping(builder.isClipping);
        setAutoDismiss(builder.autoDismiss);

        if (builder.lifecycleOwner != null) setLifecycleOwner(builder.lifecycleOwner);
        if (builder.backgroundClickListener != null)
            setOnBackgroundClickListener(builder.backgroundClickListener);
        if (builder.onDismissedListener != null)
            setOnDismissedListener(builder.onDismissedListener);
        if (builder.headerView != null) setHeaderView(builder.headerView);
        if (builder.footerView != null) setFooterView(builder.footerView);
        if (builder.animationStyle != -1) setAnimationStyle(builder.animationStyle);
        if (builder.width != 0) setWidth(builder.width);
        if (builder.height != 0) setHeight(builder.height);
        if (builder.divider != null) setDivider(builder.divider);
        if (builder.dividerHeight != 0) setDividerHeight(builder.dividerHeight);
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("ConstantConditions")
    protected void initialize(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        backgroundView = layoutInflater.inflate(R.layout.layout_power_background, null);
        backgroundView.setOnClickListener(background_clickListener);
        backgroundView.setAlpha(0.5f);
        backgroundWindow =
                new PopupWindow(
                        backgroundView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);

        menuView = layoutInflater.inflate(R.layout.layout_power_menu, null);
        menuListView = menuView.findViewById(R.id.power_menu_listView);
        menuWindow =
                new PopupWindow(
                        menuView,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
        menuCard = menuView.findViewById(R.id.power_menu_card);

        setFocusable(false);
        setTouchInterceptor(onTouchListener);
        setOnMenuItemClickListener(onMenuItemClickListener);

        contentViewPadding = ConvertUtil.convertDpToPixel(10, context);
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setFocusable(boolean focusable) {
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.setOutsideTouchable(!focusable);
    }

    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        this.menuWindow.setTouchInterceptor(onTouchListener);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener<E> menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
        this.menuListView.setOnItemClickListener(itemClickListener);
    }

    public void showAsDropDown(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor);
        }
    }

    public void showAsDropDown(View anchor, int xOff, int yOff) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, xOff, yOff);
        }
    }

    public void showAsAnchorLeftTop(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, 0, -anchor.getMeasuredHeight());
        }
    }

    public void showAsAnchorLeftBottom(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, 0, -getContentViewPadding());
        }
    }

    public void showAsAnchorRightTop(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(
                    anchor,
                    anchor.getMeasuredWidth() / 2 + getContentViewWidth() / 2,
                    -anchor.getMeasuredHeight());
        }
    }

    public void showAsAnchorRightBottom(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(
                    anchor,
                    anchor.getMeasuredWidth() / 2 + getContentViewWidth() / 2,
                    -getContentViewPadding());
        }
    }

    public void showAtCenter(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        }
    }

    public void showAtCenter(View anchor, int xOff, int yOff) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.CENTER, xOff, yOff);
        }
    }

    public void showAtLocation(View anchor, int xOff, int yOff) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xOff, yOff);
        }
    }

    public void showAtLocation(View anchor, int gravity, int xOff, int yOff) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, gravity, xOff, yOff);
        }
    }

    public void showAsAnchorCenter(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(
                    anchor,
                    anchor.getMeasuredWidth() / 2 - getContentViewWidth() / 2,
                    -anchor.getMeasuredHeight() / 2 - getContentViewHeight() / 2);
        }
    }

    public void showPopup(View anchor) {
        if (showBackground) backgroundWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        isShowing = true;
    }

    public void dismiss() {
        if (isShowing()) {
            menuWindow.dismiss();
            backgroundWindow.dismiss();
            isShowing = false;
            if (onDismissedListener != null) onDismissedListener.onDismissed();
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    public int getContentViewWidth() {
        int width = menuWindow.getContentView().getWidth();
        if (width == 0) {
            return getMeasuredContentView().getMeasuredWidth();
        } else {
            return width;
        }
    }

    public int getContentViewHeight() {
        int height = menuWindow.getContentView().getHeight();
        if (height == 0) {
            height += getAdapter().getContentViewHeight() + getContentViewPadding();
            if (getHeaderView() != null) height += getHeaderView().getMeasuredHeight();
            if (getFooterView() != null) height += getFooterView().getMeasuredHeight();
            return height;
        } else {
            return height;
        }
    }

    protected View getMeasuredContentView() {
        View contentView = menuWindow.getContentView();
        contentView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return contentView;
    }

    protected int getContentViewPadding() {
        return this.contentViewPadding;
    }

    public void setWidth(int width) {
        this.menuWindow.setWidth(width);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) menuListView.getLayoutParams();
        layoutParams.width = width - contentViewPadding;
        getMenuListView().setLayoutParams(layoutParams);
    }

    public void setHeight(int height) {
        this.fixedHeight = true;
        this.menuWindow.setHeight(height);
    }

    protected void setMeasuredHeight(int height) {
        this.menuWindow.setHeight(height);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) menuListView.getLayoutParams();
        layoutParams.height = height - contentViewPadding;
        getMenuListView().setLayoutParams(layoutParams);
    }

    public void setDividerHeight(int height) {
        menuListView.setDividerHeight(height);
    }

    public void setDivider(Drawable divider) {
        menuListView.setDivider(divider);
    }

    public void setShowBackground(boolean show) {
        this.showBackground = show;
    }

    public void setOnDismissedListener(OnDismissedListener onDismissedListener) {
        this.onDismissedListener = onDismissedListener;
    }

    public void setOnBackgroundClickListener(View.OnClickListener onBackgroundClickListener) {
        this.backgroundView.setOnClickListener(onBackgroundClickListener);
    }

    public void setAnimation(MenuAnimation menuAnimation) {
        if (menuAnimation == MenuAnimation.NONE) menuWindow.setAnimationStyle(0);
        else if (menuAnimation == MenuAnimation.DROP_DOWN) menuWindow.setAnimationStyle(-1);
        else if (menuAnimation == MenuAnimation.FADE) {
            menuWindow.setAnimationStyle(R.style.FadeMenuAnimation);
            backgroundWindow.setAnimationStyle(R.style.FadeMenuAnimation);
        } else if (menuAnimation == MenuAnimation.SHOWUP_BOTTOM_LEFT)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_BL);
        else if (menuAnimation == MenuAnimation.SHOWUP_BOTTOM_RIGHT)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_BR);
        else if (menuAnimation == MenuAnimation.SHOWUP_TOP_LEFT)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_TL);
        else if (menuAnimation == MenuAnimation.SHOWUP_TOP_RIGHT)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_TR);
        else if (menuAnimation == MenuAnimation.SHOW_UP_CENTER)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_Center);
        else if (menuAnimation == MenuAnimation.ELASTIC_BOTTOM_LEFT)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_BL);
        else if (menuAnimation == MenuAnimation.ELASTIC_BOTTOM_RIGHT)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_BR);
        else if (menuAnimation == MenuAnimation.ELASTIC_TOP_LEFT)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_TL);
        else if (menuAnimation == MenuAnimation.ELASTIC_TOP_RIGHT)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_TR);
        else if (menuAnimation == MenuAnimation.ELASTIC_CENTER)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_Center);
    }

    public void setAnimationStyle(int style) {
        this.menuWindow.setAnimationStyle(style);
    }

    public void setMenuRadius(float radius) {
        this.menuCard.setRadius(radius);
    }

    public void setMenuShadow(float shadow) {
        this.menuCard.setCardElevation(shadow);
    }

    public void setIsClipping(boolean isClipping) {
        this.menuWindow.setClippingEnabled(isClipping);
    }

    public void setSelection(int position) {
        this.menuListView.setSelection(position);
    }

    public void setBackgroundColor(int color) {
        backgroundView.setBackgroundColor(color);
    }

    public void setBackgroundAlpha(float alpha) {
        backgroundView.setAlpha(alpha);
    }

    public void setHeaderView(int layout) {
        if (this.headerView == null) {
            View view = layoutInflater.inflate(layout, null, false);
            setHeaderView(view);
        }
    }

    public void setHeaderView(View view, Object data, boolean isSelectable) {
        if (this.headerView == null) {
            this.menuListView.addHeaderView(view, data, isSelectable);
            this.headerView = view;
            this.headerView.setOnClickListener(headerFooterClickListener);
            this.headerView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        }
    }

    public void setFooterView(int layout) {
        if (this.footerView == null) {
            View view = layoutInflater.inflate(layout, null, false);
            setFooterView(view);
        }
    }

    public void setFooterView(View view, Object data, boolean isSelectable) {
        if (this.footerView == null) {
            this.menuListView.addFooterView(view, data, isSelectable);
            this.footerView = view;
            this.footerView.setOnClickListener(headerFooterClickListener);
            this.footerView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        }
    }

    public T getAdapter() {
        return this.adapter;
    }

    public ListView getMenuListView() {
        return this.menuListView;
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View view) {
        if (this.headerView == null) {
            this.menuListView.addHeaderView(view);
            this.headerView = view;
            this.headerView.setOnClickListener(headerFooterClickListener);
            this.headerView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        }
    }

    public View getFooterView() {
        return footerView;
    }

    public void setFooterView(View view) {
        if (this.footerView == null) {
            this.menuListView.addFooterView(view);
            this.footerView = view;
            this.footerView.setOnClickListener(headerFooterClickListener);
            this.footerView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        }
    }

    public void setAutoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }
}
