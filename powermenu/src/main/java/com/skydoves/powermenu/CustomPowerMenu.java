
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

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class CustomPowerMenu<T, E extends MenuBaseAdapter<T>> implements IMenuItem<T>, LifecycleObserver {

    private View backgroundView;
    private View menuView;
    private CardView menuCard;

    private PopupWindow backgroundWindow;
    private PopupWindow menuWindow;

    private LifecycleOwner lifecycleOwner;

    private E adapter;

    private ListView menuListView;
    private OnMenuItemClickListener menuItemClickListener;

    private LayoutInflater layoutInflater;

    private View headerView;
    private View footerView;

    private boolean showBackground = true;
    private boolean allowTouchBackground = false;

    private boolean isShowing = false;

    private int contentViewPadding;

    public CustomPowerMenu(Context context) {
        initialize(context);
    }

    private CustomPowerMenu(Context context, Builder<T, E> builder) {
        initialize(context);

        setShowBackground(builder.showBackground);
        setAnimation(builder.menuAnimation);
        setMenuRadius(builder.menuRadius);
        setMenuShadow(builder.menuShadow);
        setBackgroundColor(builder.backgroundColor);
        setBackgroundAlpha(builder.backgroundAlpha);
        setFocusable(builder.focusable);
        setIsClipping(builder.isClipping);

        if(builder.lifecycleOwner != null)
            setLifecycleOwner(builder.lifecycleOwner);
        if(builder.menuItemClickListener != null)
            setOnMenuItemClickListener(builder.menuItemClickListener);
        if(builder.backgroundClickListener != null)
            setOnBackgroundClickListener(builder.backgroundClickListener);
        if(builder.headerView != null)
            setHeaderView(builder.headerView);
        if(builder.footerView != null)
            setFooterView(builder.footerView);
        if(builder.animationStyle != -1)
            setAnimationStyle(builder.animationStyle);
        if(builder.selected != -1)
            setSelectedPosition(builder.selected);
        if(builder.width != 0)
            setWidth(builder.width);
        if (builder.height != 0)
            setHeight(builder.height);
        if(builder.divider != null)
            setDivider(builder.divider);
        if(builder.dividerHeight != 0)
            setDividerHeight(builder.dividerHeight);

        this.adapter = builder.adapter;
        this.adapter.setListView(getMenuListView());
        this.menuListView.setAdapter(adapter);
        addItemList(builder.Ts);
    }

    private void initialize(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        backgroundView = layoutInflater.inflate(R.layout.layout_power_background, null);
        backgroundView.setOnClickListener(background_clickListener);
        backgroundView.setAlpha(0.5f);
        backgroundWindow = new PopupWindow(backgroundView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        menuView = layoutInflater.inflate(R.layout.layout_power_menu, null);
        menuListView = menuView.findViewById(R.id.power_menu_listView);
        menuWindow = new PopupWindow(menuView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        menuCard = menuView.findViewById(R.id.power_menu_card);
        adapter = (E)(new MenuBaseAdapter<>(menuListView));

        setFocusable(false);
        setOnMenuItemClickListener(onMenuItemClickListener);

        contentViewPadding = ConvertUtil.convertDpToPixel(10, context);
    }

    private OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener<T>() {
        @Override
        public void onItemClick(int position, T item) {

        }
    };

    private View.OnClickListener headerFooterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener background_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!allowTouchBackground)
                dismiss();
        }
    };

    public void showAsDropDown(View anchor) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor);
        }
    }

    public void showAsDropDown(View anchor, int xOff, int yOff) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, xOff, yOff);
        }
    }

    public void showAsAnchorCenter(View anchor) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor,
                    anchor.getMeasuredWidth()/2 - getContentViewWidth()/2,
                    -anchor.getMeasuredHeight()/2 - getContentViewHeight()/2);
        }
    }

    public void showAsAnchorLeftTop(View anchor) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, 0, -anchor.getMeasuredHeight());
        }
    }

    public void showAsAnchorLeftBottom(View anchor) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, 0, -getContentViewPadding());
        }
    }

    public void showAsAnchorRightTop(View anchor) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, anchor.getMeasuredWidth()/2 + getContentViewWidth()/2, -anchor.getMeasuredHeight());
        }
    }

    public void showAsAnchorRightBottom(View anchor) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor, anchor.getMeasuredWidth()/2 + getContentViewWidth()/2, -getContentViewPadding());
        }
    }

    public void showAtCenter(View anchor) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        }
    }

    public void showAtCenter(View anchor, int xOff, int yOff) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.CENTER, xOff, yOff);
        }
    }

    public void showAtLocation(View anchor, int xOff, int yOff) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xOff, yOff);
        }
    }

    public void showAtLocation(View anchor, int gravity, int xOff, int yOff) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAtLocation(anchor, gravity, xOff, yOff);
        }
    }

    public void showAtCenterOnAnchor(View anchor) {
        if(!isShowing()) {
            showPopup(anchor);
            menuWindow.showAsDropDown(anchor,
                    anchor.getMeasuredWidth()/2 - getContentViewWidth()/2,
                    -anchor.getMeasuredHeight()/2 - getContentViewHeight()/2);
        }
    }

    private void showPopup(View anchor) {
        if(showBackground) backgroundWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
        isShowing = true;
        setWidth(getContentViewWidth());
        setHeight(getContentViewHeight());
    }

    public void dismiss() {
        if(isShowing()) {
            backgroundWindow.dismiss();
            menuWindow.dismiss();
            isShowing = false;
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
            menuItemClickListener.onItemClick(index, menuListView.getItemAtPosition(index));
        }
    };

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.lifecycleOwner = lifecycleOwner;
    }

    public int getContentViewWidth() {
        int width = menuWindow.getContentView().getWidth();
        if(width == 0) {
            return getMeasuredContentView().getMeasuredWidth();
        } else {
            return width;
        }
    }

    public int getContentViewHeight() {
        int height = menuWindow.getContentView().getHeight();
        if(height == 0) {
            return getAdapter().getContentViewHeight() + getContentViewPadding();
        } else {
            return height;
        }
    }

    private View getMeasuredContentView() {
        View contentView = menuWindow.getContentView();
        contentView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return contentView;
    }

    private int getContentViewPadding() {
        return this.contentViewPadding;
    }

    @Override
    public void setListView(ListView listView) {
        getAdapter().setListView(getMenuListView());
    }

    @Override
    public ListView getListView() {
        return getAdapter().getListView();
    }

    public void setWidth(int width) {
        this.menuWindow.setWidth(width);
    }

    public void setHeight(int height) {
        this.menuWindow.setHeight(height);
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

    public void setOnMenuItemClickListener(OnMenuItemClickListener<T> menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
        this.menuListView.setOnItemClickListener(itemClickListener);
    }

    public void setOnBackgroundClickListener(View.OnClickListener onBackgroundClickListener) {
        this.backgroundView.setOnClickListener(onBackgroundClickListener);
    }

    public void setAnimation(MenuAnimation menuAnimation) {
        if(menuAnimation == MenuAnimation.NONE)
            menuWindow.setAnimationStyle(0);
        else if(menuAnimation == MenuAnimation.DROP_DOWN)
            menuWindow.setAnimationStyle(-1);
        else if(menuAnimation == MenuAnimation.FADE) {
            menuWindow.setAnimationStyle(R.style.FadeMenuAnimation);
            backgroundWindow.setAnimationStyle(R.style.FadeMenuAnimation);
        }
        else if(menuAnimation == MenuAnimation.SHOWUP_BOTTOM_LEFT)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_BL);
        else if(menuAnimation == MenuAnimation.SHOWUP_BOTTOM_RIGHT)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_BR);
        else if(menuAnimation == MenuAnimation.SHOWUP_TOP_LEFT)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_TL);
        else if(menuAnimation == MenuAnimation.SHOWUP_TOP_RIGHT)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_TR);
        else if(menuAnimation == MenuAnimation.SHOW_UP_CENTER)
            menuWindow.setAnimationStyle(R.style.ShowUpAnimation_Center);
        else if(menuAnimation == MenuAnimation.ELASTIC_BOTTOM_LEFT)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_BL);
        else if(menuAnimation == MenuAnimation.ELASTIC_BOTTOM_RIGHT)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_BR);
        else if(menuAnimation == MenuAnimation.ELASTIC_TOP_LEFT)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_TL);
        else if(menuAnimation == MenuAnimation.ELASTIC_TOP_RIGHT)
            menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_TR);
        else if(menuAnimation == MenuAnimation.ELASTIC_CENTER)
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

    public E getAdapter() {
        return this.adapter;
    }

    public ListView getMenuListView() {
        return this.menuListView;
    }

    public void setHeaderView(int layout) {
        if(this.headerView == null) {
            View view = layoutInflater.inflate(layout, null, false);
            this.menuListView.addHeaderView(view);
            this.headerView = view;
            this.headerView.setOnClickListener(headerFooterClickListener);
        }
    }

    public void setHeaderView(View view) {
        if(this.headerView == null) {
            this.menuListView.addHeaderView(view);
            this.headerView = view;
            this.headerView.setOnClickListener(headerFooterClickListener);
        }
    }

    public void setHeaderView(View view, Object data, boolean isSelectable) {
        if(this.headerView == null) {
            this.menuListView.addHeaderView(view, data, isSelectable);
            this.headerView = view;
            this.headerView.setOnClickListener(headerFooterClickListener);
        }
    }

    public void setFooterView(int layout) {
        if(this.footerView == null) {
            View view = layoutInflater.inflate(layout, null, false);
            this.menuListView.addFooterView(view);
            this.footerView = view;
            this.footerView.setOnClickListener(headerFooterClickListener);
        }
    }

    public void setFooterView(View view) {
        if(this.footerView == null) {
            this.menuListView.addFooterView(view);
            this.footerView = view;
            this.footerView.setOnClickListener(headerFooterClickListener);
        }
    }

    public void setFooterView(View view, Object data, boolean isSelectable) {
        if(this.footerView == null) {
            this.menuListView.addFooterView(view, data, isSelectable);
            this.footerView = view;
            this.footerView.setOnClickListener(headerFooterClickListener);
        }
    }

    public void setIsClipping(boolean isClipping) {
        this.menuWindow.setClippingEnabled(isClipping);
    }

    public View getHeaderview() {
        return headerView;
    }

    public View getFooterView() {
        return footerView;
    }

    public void setSelection(int position) {
        this.menuListView.setSelection(position);
    }

    @Override
    public int getSelectedPosition() {
        return getAdapter().getSelectedPosition();
    }

    public void setBackgroundColor(int color) {
        backgroundView.setBackgroundColor(color);
    }

    public void setBackgroundAlpha(float alpha) {
        backgroundView.setAlpha(alpha);
    }

    public void setFocusable(boolean focusable) {
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.setOutsideTouchable(!focusable);
    }

    public void setSelectedPosition(int position) {
        if (getAdapter() != null)
            getAdapter().setSelectedPosition(position);
    }

    @Override
    public void addItem(Object item) {
        if (getAdapter() != null)
            getAdapter().addItem((T)item);
    }

    @Override
    public void addItem(int position, T item) {
        if (getAdapter() != null)
            getAdapter().addItem(position, item);
    }

    @Override
    public void addItemList(List<T> itemList) {
        if (getAdapter() != null)
            getAdapter().addItemList(itemList);
    }

    @Override
    public void removeItem(T item) {
        if (getAdapter() != null)
            getAdapter().removeItem(item);
    }

    @Override
    public void removeItem(int position) {
        if (getAdapter() != null)
            getAdapter().removeItem(position);
    }

    @Override
    public void clearItems() {
        if (getAdapter() != null)
            getAdapter().clearItems();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        dismiss();
    }

    @Override
    public List<T> getItemList() {
        return getAdapter().getItemList();
    }

    @SuppressWarnings("unchecked")
    public static class Builder<T, E extends MenuBaseAdapter<T>> {

        private Context context;
        private LayoutInflater layoutInflater;

        private E adapter;
        private boolean showBackground = true;
        private LifecycleOwner lifecycleOwner = null;
        private OnMenuItemClickListener<T> menuItemClickListener = null;
        private View.OnClickListener backgroundClickListener = null;
        private MenuAnimation menuAnimation = MenuAnimation.DROP_DOWN;
        private View headerView = null;
        private View footerView = null;
        private int animationStyle = -1;
        private float menuRadius = 5;
        private float menuShadow = 5;
        private int width = 0;
        private int height = 0;
        private int dividerHeight = 0;
        private Drawable divider = null;
        private int backgroundColor = Color.BLACK;
        private float backgroundAlpha = 0.6f;
        private boolean focusable = false;
        private int selected = -1;
        private boolean isClipping = true;

        private List<T> Ts;

        public Builder(Context context, E adapter) {
            this.context = context;
            this.Ts = new ArrayList<>();
            this.adapter = adapter;
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public Builder setLifecycleOwner(LifecycleOwner lifecycleOwner) {
            this.lifecycleOwner = lifecycleOwner;
            return this;
        }

        public Builder setShowBackground(boolean show) {
            this.showBackground = show;
            return this;
        }

        public Builder setOnMenuItemClickListener(Object menuItemClickListener) {
            this.menuItemClickListener = (OnMenuItemClickListener<T>)menuItemClickListener;
            return this;
        }

        public Builder setOnBackgroundClickListener(View.OnClickListener onBackgroundClickListener) {
            this.backgroundClickListener = onBackgroundClickListener;
            return this;
        }

        public Builder setHeaderView(int headerView) {
            this.headerView = layoutInflater.inflate(headerView, null);
            return this;
        }

        public Builder setHeaderView(View headerView) {
            this.headerView = headerView;
            return this;
        }

        public Builder setFooterView(int footerView) {
            this.footerView = layoutInflater.inflate(footerView, null);
            return this;
        }

        public Builder setFooterView(View footerView) {
            this.footerView = footerView;
            return this;
        }

        public Builder setAnimation(MenuAnimation menuAnimation) {
            this.menuAnimation = menuAnimation;
            return this;
        }

        public Builder setAnimationStyle(int style) {
            this.animationStyle = style;
            return this;
        }

        public Builder setMenuRadius(float radius) {
            this.menuRadius = radius;
            return this;
        }

        public Builder setMenuShadow(float shadow) {
            this.menuShadow = shadow;
            return this;
        }

        public Builder setWith(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setDividerHeight(int height) {
            this.dividerHeight = height;
            return this;
        }

        public Builder setDivider(Drawable divider) {
            this.divider = divider;
            return this;
        }

        public Builder setBackgroundColor(int color) {
            this.backgroundColor = color;
            return this;
        }

        public Builder setBackgroundAlpha(float alpha) {
            this.backgroundAlpha = alpha;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public Builder setSelected(int position) {
            this.selected = position;
            return this;
        }

        public Builder setIsClipping(boolean isClipping) {
            this.isClipping = isClipping;
            return this;
        }

        public Builder addItem(Object item) {
            this.Ts.add((T)item);
            return this;
        }

        public Builder addItem(int position, Object item) {
            this.Ts.add(position, (T)item);
            return this;
        }

        public Builder addItemList(List<T> itemList) {
            this.Ts.addAll(itemList);
            return this;
        }

        public CustomPowerMenu build() {
            return new CustomPowerMenu<>(context, this);
        }
    }
}