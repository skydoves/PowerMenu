
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
public class PowerMenu implements IMenuItem<PowerMenuItem>, LifecycleObserver {

    private View backgroundView;
    private View menuView;
    private CardView menuCard;

    private PopupWindow backgroundWindow;
    private PopupWindow menuWindow;

    private LifecycleOwner lifecycleOwner;

    private MenuListAdapter adapter;

    private ListView menuListView;
    private OnMenuItemClickListener menuItemClickListener;
    private LayoutInflater layoutInflater;

    private boolean showBackground = true;
    private boolean allowTouchBackground = false;

    private boolean isShowing = false;

    public PowerMenu(Context context) {
        initialize(context);
    }

    private PowerMenu(Context context, Builder builder) {
        initialize(context);

        setShowBackground(builder.showBackground);
        setAnimation(builder.menuAnimation);
        setMenuRadius(builder.menuRadius);
        setMenuShadow(builder.menuShadow);
        setBackgroundColor(builder.backgroundColor);
        setBackgroundAlpha(builder.backgroundAlpha);
        setFocusable(builder.focusable);
        setSelectedEffect(builder.selectedEffect);

        if(builder.lifecycleOwner != null)
            setLifecycleOwner(builder.lifecycleOwner);
        if(builder.menuItemClickListener != null)
            setOnMenuItemClickListener(builder.menuItemClickListener);
        if(builder.backgroundClickListener != null)
            setOnBackgroundClickListener(builder.backgroundClickListener);
        if(builder.animationStyle != -1)
            setAnimationStyle(builder.animationStyle);
        if(builder.selected != -1)
            setSelected(builder.selected);
        if(builder.width != 0)
            setWidth(builder.width);
        if (builder.height != 0)
            setHeight(builder.height);
        if(builder.divider != null)
            setDivider(builder.divider);
        if(builder.dividerHeight != 0)
            setDividerHeight(builder.dividerHeight);
        if(builder.textColor != -2)
            setTextColor(builder.textColor);
        if(builder.menuColor != -2)
            setMenuColor(builder.menuColor);
        if(builder.selectedTextColor != -2)
            setSelectedTextColor(builder.selectedTextColor);
        if(builder.selectedMenuColor != -2)
            setSelectedMenuColor(builder.selectedMenuColor);

        addItemList(builder.powerMenuItems);
    }

    private void initialize(Context context) {
        adapter = new MenuListAdapter();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        backgroundView = layoutInflater.inflate(R.layout.layout_power_background, null);
        backgroundView.setOnClickListener(background_clickListener);
        backgroundView.setAlpha(0.5f);
        backgroundWindow = new PopupWindow(backgroundView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        menuView = layoutInflater.inflate(R.layout.layout_power_menu, null);
        menuListView = menuView.findViewById(R.id.power_menu_listView);
        menuListView.setAdapter(adapter);
        menuWindow = new PopupWindow(menuView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        menuCard = menuView.findViewById(R.id.power_menu_card);

        setFocusable(false);
        setOnMenuItemClickListener(onMenuItemClickListener);
    }

    private OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {

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
            if(showBackground) backgroundWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
            menuWindow.showAsDropDown(anchor);
            isShowing = true;
        }
    }

    public void showAsDropDown(View anchor, int xOff, int yOff) {
        if(!isShowing()) {
            if(showBackground) backgroundWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
            menuWindow.showAsDropDown(anchor, xOff, yOff);
            isShowing = true;
        }
    }

    public void showAtCenter(View anchor) {
        if(!isShowing()) {
            if(showBackground) backgroundWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
            menuWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
            isShowing = true;
        }
    }

    public void showAtCenter(View anchor, int xOff, int yOff) {
        if(!isShowing()) {
            if(showBackground) backgroundWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
            menuWindow.showAtLocation(anchor, Gravity.CENTER, xOff, yOff);
            isShowing = true;
        }
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
            menuItemClickListener.onItemClick(index, adapter.getItem(index));
        }
    };

    public void setWidth(int width) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) menuListView.getLayoutParams();
        layoutParams.width = width;
        menuListView.setLayoutParams(layoutParams);
    }

    public void setHeight(int height) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) menuListView.getLayoutParams();
        layoutParams.height = height;
        menuListView.setLayoutParams(layoutParams);
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setSelectedEffect(boolean effect) {
        adapter.setSelectedEffect(effect);
    }

    public void setTextColor(int color) {
        this.adapter.setTextColor(color);
    }

    public void setMenuColor(int color) {
        this.adapter.setMenuColor(color);
    }

    public void setSelectedTextColor(int color) {
        this.adapter.setSelectedTextColor(color);
    }

    public void setSelectedMenuColor(int color) {
        this.adapter.setSelectedMenuColor(color);
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

    public void setOnMenuItemClickListener(OnMenuItemClickListener<PowerMenuItem> menuItemClickListener) {
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

    public MenuListAdapter getAdapter() {
        return this.adapter;
    }

    public ListView getMenuListView() {
        return this.menuListView;
    }

    public void addHeaderView(int layout) {
        this.menuListView.addHeaderView(layoutInflater.inflate(layout, null, false));
    }

    public void addHeaderView(View view) {
        this.menuListView.addHeaderView(view);
    }

    public void addHeaderView(View view, Object data, boolean isSelectable) {
        this.menuListView.addHeaderView(view, data, isSelectable);
    }

    public void addFooterView(int layout) {
        this.menuListView.addFooterView(layoutInflater.inflate(layout, null, false));
    }

    public void addFooterView(View view) {
        this.menuListView.addFooterView(view);
    }

    public void addFooterView(View view, Object data, boolean isSelectable) {
        this.menuListView.addFooterView(view, data, isSelectable);
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

    public void setFocusable(boolean focusable) {
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.setOutsideTouchable(!focusable);
    }

    @Override
    public void setSelected(int position) {
        if (adapter != null)
            adapter.setSelected(position);
    }

    @Override
    public void addItem(PowerMenuItem item) {
        if (adapter != null)
            adapter.addItem(item);
    }

    @Override
    public void addItem(int position, PowerMenuItem item) {
        if (adapter != null)
            adapter.addItem(position, item);
    }

    @Override
    public void addItemList(List<PowerMenuItem> itemList) {
        if (adapter != null)
            adapter.addItemList(itemList);
    }

    @Override
    public void removeItem(PowerMenuItem item) {
        if (adapter != null)
            adapter.removeItem(item);
    }

    @Override
    public void removeItem(int position) {
        if (adapter != null)
            adapter.removeItem(position);
    }

    @Override
    public void clearItems() {
        if (adapter != null)
            adapter.clearItems();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        dismiss();
    }

    @Override
    public List<PowerMenuItem> getItemList() {
        return adapter.getItemList();
    }

    public static class Builder {

        private Context context;

        private boolean showBackground = true;
        private LifecycleOwner lifecycleOwner = null;
        private OnMenuItemClickListener<PowerMenuItem> menuItemClickListener = null;
        private View.OnClickListener backgroundClickListener = null;
        private MenuAnimation menuAnimation = MenuAnimation.DROP_DOWN;
        private int animationStyle = -1;
        private float menuRadius = 5;
        private float menuShadow = 5;
        private int width = 0;
        private int height = 0;
        private int textColor = -2;
        private int menuColor = -2;
        private boolean selectedEffect = true;
        private int selectedTextColor = -2;
        private int selectedMenuColor = -2;
        private int dividerHeight = 0;
        private Drawable divider = null;
        private int backgroundColor = Color.BLACK;
        private float backgroundAlpha = 0.6f;
        private boolean focusable = false;
        private int selected = -1;

        private List<PowerMenuItem> powerMenuItems;

        public Builder(Context context) {
            this.context = context;
            this.powerMenuItems = new ArrayList<>();
        }

        public Builder setLifecycleOwner(LifecycleOwner lifecycleOwner) {
            this.lifecycleOwner = lifecycleOwner;
            return this;
        }

        public Builder setShowBackground(boolean show) {
            this.showBackground = show;
            return this;
        }

        public Builder setOnMenuItemClickListener(OnMenuItemClickListener<PowerMenuItem> menuItemClickListener) {
            this.menuItemClickListener = menuItemClickListener;
            return this;
        }

        public Builder setOnBackgroundClickListener(View.OnClickListener onBackgroundClickListener) {
            this.backgroundClickListener = onBackgroundClickListener;
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

        public Builder setTextColor(int color) {
            this.textColor = color;
            return this;
        }

        public Builder setMenuColor(int color) {
            this.menuColor = color;
            return this;
        }

        public Builder setSelectedTextColor(int color) {
            this.selectedTextColor = color;
            return this;
        }

        public Builder setSelectedMenuColor(int color) {
            this.selectedMenuColor = color;
            return this;
        }

        public Builder setSelectedEffect(boolean effect) {
            this.selectedEffect = effect;
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

        public Builder addItem(PowerMenuItem item) {
            this.powerMenuItems.add(item);
            return this;
        }

        public Builder addItem(int position, PowerMenuItem item) {
            this.powerMenuItems.add(position, item);
            return this;
        }

        public Builder addItemList(List<PowerMenuItem> itemList) {
            this.powerMenuItems.addAll(itemList);
            return this;
        }

        public PowerMenu build() {
            return new PowerMenu(context, this);
        }
    }
}
