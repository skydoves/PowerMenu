/*
 * Copyright (C) 2017 skydoves
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

/**
 * AbstractPowerMenu is the abstract class of {@link PowerMenu} and {@link CustomPowerMenu}.
 *
 * <p>It implements basically almost things of the PowerMenu.
 *
 * <p>
 */
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

    /**
     * sets {@link LifecycleOwner} for preventing memory leak.
     *
     * <p>if sets the {@link LifecycleOwner} this popup will be dismissed automatically
     *
     * <p>when onDestroy method called by lifecycle.
     *
     * @param lifecycleOwner {@link androidx.appcompat.app.AppCompatActivity},
     *     <p>{@link androidx.fragment.app.FragmentActivity} or etc are implements {@link
     *     LifecycleOwner}.
     */
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.lifecycleOwner = lifecycleOwner;
    }

    /**
     * makes focusing only on the menu popup.
     *
     * @param focusable focusable or not.
     */
    public void setFocusable(boolean focusable) {
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.setOutsideTouchable(!focusable);
    }

    /**
     * sets {@link android.view.View.OnTouchListener} manually for the outside of popup.
     *
     * @param onTouchListener onTouchListener.
     */
    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        this.menuWindow.setTouchInterceptor(onTouchListener);
    }

    /**
     * sets {@link OnMenuItemClickListener}.
     *
     * @param menuItemClickListener menu item click listener.
     */
    public void setOnMenuItemClickListener(OnMenuItemClickListener<E> menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
        this.menuListView.setOnItemClickListener(itemClickListener);
    }

    /**
     * showing the popup to the anchor.
     *
     * @param anchor anchor view.
     */
    public void showPopup(View anchor) {
        if (showBackground) backgroundWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        isShowing = true;
    }

    /**
     * showing the popup menu as drop down to the anchor.
     *
     * @param anchor anchor view.
     */
    public void showAsDropDown(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor);
        }
    }

    /**
     * showing the popup menu as drop down to the anchor with x-off and y-off.
     *
     * @param anchor anchor view.
     * @param xOff x-off,
     * @param yOff y-off.
     */
    public void showAsDropDown(View anchor, int xOff, int yOff) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, xOff, yOff);
        }
    }

    /**
     * showing the popup menu as left-top aligns to the anchor.
     *
     * @param anchor anchor view.
     */
    public void showAsAnchorLeftTop(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, 0, -anchor.getMeasuredHeight());
        }
    }

    /**
     * showing the popup menu as left-bottom aligns to the anchor.
     *
     * @param anchor anchor view.
     */
    public void showAsAnchorLeftBottom(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, 0, -getContentViewPadding());
        }
    }

    /**
     * showing the popup menu as right-top aligns to the anchor.
     *
     * @param anchor anchor view.
     */
    public void showAsAnchorRightTop(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(
                    anchor,
                    anchor.getMeasuredWidth() / 2 + getContentViewWidth() / 2,
                    -anchor.getMeasuredHeight());
        }
    }

    /**
     * showing the popup menu as right-bottom aligns to the anchor.
     *
     * @param anchor anchor view.
     */
    public void showAsAnchorRightBottom(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(
                    anchor,
                    anchor.getMeasuredWidth() / 2 + getContentViewWidth() / 2,
                    -getContentViewPadding());
        }
    }

    /**
     * showing the popup menu as center align to the anchor.
     *
     * @param anchor anchor view.
     */
    public void showAsAnchorCenter(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(
                    anchor,
                    anchor.getMeasuredWidth() / 2 - getContentViewWidth() / 2,
                    -anchor.getMeasuredHeight() / 2 - getContentViewHeight() / 2);
        }
    }

    /**
     * showing the popup menu as center aligns to the anchor.
     *
     * @param anchor anchor view.
     */
    public void showAtCenter(View anchor) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        }
    }

    /**
     * showing the popup menu as center aligns to the anchor with x-off and y-off.
     *
     * @param anchor anchor view.
     * @param xOff x-off.
     * @param yOff y-off.
     */
    public void showAtCenter(View anchor, int xOff, int yOff) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.CENTER, xOff, yOff);
        }
    }

    /**
     * showing the popup menu to the specific location to the anchor.
     *
     * @param anchor anchor view.
     * @param xOff x-off.
     * @param yOff y-off.
     */
    public void showAtLocation(View anchor, int xOff, int yOff) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xOff, yOff);
        }
    }

    /**
     * showing the popup menu to the specific location to the anchor with {@link Gravity}.
     *
     * @param anchor anchor view.
     * @param gravity gravity of the menu.
     * @param xOff x-off.
     * @param yOff y-off.
     */
    public void showAtLocation(View anchor, int gravity, int xOff, int yOff) {
        if (!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, gravity, xOff, yOff);
        }
    }

    /** dismiss the popup menu. */
    public void dismiss() {
        if (isShowing()) {
            menuWindow.dismiss();
            backgroundWindow.dismiss();
            isShowing = false;
            if (onDismissedListener != null) onDismissedListener.onDismissed();
        }
    }

    /**
     * gets the popup is showing or not.
     *
     * @return the popup is showing or not.
     */
    public boolean isShowing() {
        return isShowing;
    }

    /**
     * gets measured width of the popup.
     *
     * @return measured width of the popup.
     */
    public int getContentViewWidth() {
        int width = menuWindow.getContentView().getWidth();
        if (width == 0) {
            return getMeasuredContentView().getMeasuredWidth();
        } else {
            return width;
        }
    }

    /**
     * gets measured height of the popup.
     *
     * @return measured height of the popup.
     */
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

    /**
     * gets the content view of the popup menu.
     *
     * @return content view of the popup menu.
     */
    protected View getMeasuredContentView() {
        View contentView = menuWindow.getContentView();
        contentView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return contentView;
    }

    /**
     * gets the content view padding of the popup menu.
     *
     * @return the content view padding view of the popup menu.
     */
    protected int getContentViewPadding() {
        return this.contentViewPadding;
    }

    /**
     * sets the width of the popup menu.
     *
     * @param width width of the popup menu.
     */
    public void setWidth(int width) {
        this.menuWindow.setWidth(width);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) menuListView.getLayoutParams();
        layoutParams.width = width - contentViewPadding;
        getMenuListView().setLayoutParams(layoutParams);
    }

    /**
     * sets the height of the popup menu.
     *
     * @param height height of the popup menu.
     */
    public void setHeight(int height) {
        this.fixedHeight = true;
        this.menuWindow.setHeight(height);
    }

    /**
     * sets the measured height of the popup menu list.
     *
     * @param height the measured height of the popup menu list.
     */
    protected void setMeasuredHeight(int height) {
        this.menuWindow.setHeight(height);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) menuListView.getLayoutParams();
        layoutParams.height = height - contentViewPadding;
        getMenuListView().setLayoutParams(layoutParams);
    }

    /**
     * sets the divider height of the popup menu.
     *
     * @param height divider height of the popup menu.
     */
    public void setDividerHeight(int height) {
        menuListView.setDividerHeight(height);
    }

    /**
     * sets the drawable of the divider.
     *
     * @param divider drawable of the divider.
     */
    public void setDivider(Drawable divider) {
        menuListView.setDivider(divider);
    }

    /**
     * sets the background is showing or not.
     *
     * @param show background is showing or not.
     */
    public void setShowBackground(boolean show) {
        this.showBackground = show;
    }

    /**
     * sets the dismissed listener of the popup menu.
     *
     * @param onDismissedListener {@link OnDismissedListener}.
     */
    public void setOnDismissedListener(OnDismissedListener onDismissedListener) {
        this.onDismissedListener = onDismissedListener;
    }

    /**
     * sets the background click listener of the background.
     *
     * @param onBackgroundClickListener {@link android.view.View.OnClickListener}.
     */
    public void setOnBackgroundClickListener(View.OnClickListener onBackgroundClickListener) {
        this.backgroundView.setOnClickListener(onBackgroundClickListener);
    }

    /**
     * sets animations of the popup. It will start up when the popup is showing.
     *
     * @param menuAnimation menu animation.
     */
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

    /**
     * sets custom animations of the popup. It will start up when the popup is showing.
     *
     * @param style custom animation style.
     */
    public void setAnimationStyle(int style) {
        this.menuWindow.setAnimationStyle(style);
    }

    /**
     * sets the corner radius of the popup menu.
     *
     * @param radius corner radius.
     */
    public void setMenuRadius(float radius) {
        this.menuCard.setRadius(radius);
    }

    /**
     * sets the shadow of the popup menu.
     *
     * @param shadow shadow value.
     */
    public void setMenuShadow(float shadow) {
        this.menuCard.setCardElevation(shadow);
    }

    /**
     * sets the clipping enable or unable.
     *
     * @param isClipping clipping enable or unable.
     */
    public void setIsClipping(boolean isClipping) {
        this.menuWindow.setClippingEnabled(isClipping);
    }

    /**
     * sets the selected position of the popup menu. It can be used for scrolling as the position.
     *
     * @param position selected position.
     */
    public void setSelection(int position) {
        this.menuListView.setSelection(position);
    }

    /**
     * sets the color of the background.
     *
     * @param color color value.
     */
    public void setBackgroundColor(int color) {
        backgroundView.setBackgroundColor(color);
    }

    /**
     * sets the alpha of the background.
     *
     * @param alpha alpha value.
     */
    public void setBackgroundAlpha(float alpha) {
        backgroundView.setAlpha(alpha);
    }

    /**
     * sets the header view of the popup menu list.
     *
     * @param view {@link View}.
     */
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

    /**
     * sets the header view of the popup menu list.
     *
     * @param view {@link View}.
     * @param data {@link Object}.
     * @param isSelectable is selectable or not.
     */
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

    /**
     * sets the header view of the popup menu using layout.
     *
     * @param layout layout.
     */
    public void setHeaderView(int layout) {
        if (this.headerView == null) {
            View view = layoutInflater.inflate(layout, null, false);
            setHeaderView(view);
        }
    }

    /**
     * sets the footer view of the popup menu list.
     *
     * @param view {@link View}.
     */
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

    /**
     * sets the footer view of the popup menu list.
     *
     * @param view {@link View}.
     * @param data {@link Object}.
     * @param isSelectable is selectable or not.
     */
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

    /**
     * sets the footer view of the popup menu using layout.
     *
     * @param layout layout.
     */
    public void setFooterView(int layout) {
        if (this.footerView == null) {
            View view = layoutInflater.inflate(layout, null, false);
            setFooterView(view);
        }
    }

    /**
     * gets the adapter of the popup menu list.
     *
     * @return adapter
     */
    public T getAdapter() {
        return this.adapter;
    }

    /**
     * gets the {@link ListView} of the popup menu.
     *
     * @return {@link ListView}.
     */
    public ListView getMenuListView() {
        return this.menuListView;
    }

    /**
     * gets the header view of the popup menu list.
     *
     * @return {@link View}.
     */
    public View getHeaderView() {
        return headerView;
    }

    /**
     * gets the footer view of the popup menu list.
     *
     * @return {@link View}.
     */
    public View getFooterView() {
        return footerView;
    }

    /**
     * sets the auto-dismissing or not.
     *
     * <p>The popup menu will be dismissed automatically when the item would be clicked.
     *
     * @param autoDismiss is auto-dismissing or not.
     */
    public void setAutoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }
}
