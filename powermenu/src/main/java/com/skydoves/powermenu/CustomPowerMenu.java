
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
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
public class CustomPowerMenu<T, E extends MenuBaseAdapter<T>> extends AbstractPowerMenu<E> implements IMenuItem<T> {

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
        if(builder.onDismissedListener != null)
            setOnDismissedListener(builder.onDismissedListener);
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

    @Override
    protected void initialize(Context context) {
        super.initialize(context);
        this.adapter = (E)(new MenuBaseAdapter<>(menuListView));
        setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener<T> menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
        this.menuListView.setOnItemClickListener(itemClickListener);
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
            menuItemClickListener.onItemClick(index, menuListView.getItemAtPosition(index));
        }
    };

    private OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener<T>() {
        @Override
        public void onItemClick(int position, T item) {

        }
    };

    @Override
    public void setListView(ListView listView) {
        getAdapter().setListView(getMenuListView());
    }

    @Override
    public ListView getListView() {
        return getAdapter().getListView();
    }

    @Override
    public void setSelectedPosition(int position) {
        if (getAdapter() != null)
            getAdapter().setSelectedPosition(position);
    }

    @Override
    public int getSelectedPosition() {
        return getAdapter().getSelectedPosition();
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

    @Override
    public List<T> getItemList() {
        return getAdapter().getItemList();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        dismiss();
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
        private OnDismissedListener onDismissedListener = null;
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

        public Builder setOnDismissListener(OnDismissedListener onDismissListener) {
            this.onDismissedListener = onDismissListener;
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

        public Builder setWidth(int width) {
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