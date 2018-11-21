
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

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
public class PowerMenu extends AbstractPowerMenu<PowerMenuItem, MenuListAdapter> implements IMenuItem<PowerMenuItem> {

    protected PowerMenu(Context context, AbstractMenuBuilder abstractMenuBuilder) {
        super(context, abstractMenuBuilder);

        Builder builder = (Builder) abstractMenuBuilder;

        setSelectedEffect(builder.selectedEffect);
        if (builder.menuItemClickListener != null)
            setOnMenuItemClickListener(builder.menuItemClickListener);
        if (builder.textColor != -2)
            setTextColor(builder.textColor);
        if (builder.menuColor != -2)
            setMenuColor(builder.menuColor);
        if (builder.selectedTextColor != -2)
            setSelectedTextColor(builder.selectedTextColor);
        if (builder.selectedMenuColor != -2)
            setSelectedMenuColor(builder.selectedMenuColor);
        if (builder.selected != -1)
            setSelectedPosition(builder.selected);

        this.menuListView.setAdapter(adapter);
        addItemList(builder.powerMenuItems);
    }

    @Override
    protected void initialize(Context context) {
        super.initialize(context);
        this.adapter = new MenuListAdapter(menuListView);
    }

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
    public void addItem(PowerMenuItem item) {
        if (getAdapter() != null)
            getAdapter().addItem(item);
    }

    @Override
    public void addItem(int position, PowerMenuItem item) {
        if (getAdapter() != null)
            getAdapter().addItem(position, item);
    }

    @Override
    public void addItemList(List<PowerMenuItem> itemList) {
        if (getAdapter() != null)
            getAdapter().addItemList(itemList);
    }

    @Override
    public void removeItem(PowerMenuItem item) {
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
        if (adapter != null)
            getAdapter().clearItems();
    }

    @Override
    public List<PowerMenuItem> getItemList() {
        return getAdapter().getItemList();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        dismiss();
    }

    public void setSelectedEffect(boolean effect) {
        getAdapter().setSelectedEffect(effect);
    }

    public void setTextColor(int color) {
        this.getAdapter().setTextColor(color);
    }

    public void setMenuColor(int color) {
        this.getAdapter().setMenuColor(color);
    }

    public void setSelectedTextColor(int color) {
        this.getAdapter().setSelectedTextColor(color);
    }

    public void setSelectedMenuColor(int color) {
        this.getAdapter().setSelectedMenuColor(color);
    }

    public static class Builder extends AbstractMenuBuilder {

        private OnMenuItemClickListener<PowerMenuItem> menuItemClickListener = null;
        private int textColor = -2;
        private int menuColor = -2;
        private boolean selectedEffect = true;
        private int selectedTextColor = -2;
        private int selectedMenuColor = -2;

        private List<PowerMenuItem> powerMenuItems;

        public Builder(Context context) {
            this.context = context;
            this.powerMenuItems = new ArrayList<>();
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

        public Builder setOnMenuItemClickListener(OnMenuItemClickListener<PowerMenuItem> menuItemClickListener) {
            this.menuItemClickListener = menuItemClickListener;
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

        public Builder setIsClipping(boolean isClipping) {
            this.isClipping = isClipping;
            return this;
        }

        public Builder setAutoDismiss(boolean autoDismiss) {
            this.autoDismiss = autoDismiss;
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
