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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomPowerMenu is one the implementation of the {@link AbstractPowerMenu}.
 *
 * <p>It implements the customized {@link PowerMenu} by the user.
 */
@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
public class CustomPowerMenu<T, E extends MenuBaseAdapter<T>> extends AbstractPowerMenu<T, E> {

  protected CustomPowerMenu(
      @NonNull Context context, @NonNull AbstractMenuBuilder abstractMenuBuilder) {
    super(context, abstractMenuBuilder);

    Builder<T, E> builder = (Builder) abstractMenuBuilder;

    if (builder.menuItemClickListener != null) {
      setOnMenuItemClickListener(builder.menuItemClickListener);
    }
    if (builder.selected != -1) {
      setSelectedPosition(builder.selected);
    }

    this.adapter = builder.adapter;
    this.adapter.setListView(getMenuListView());
    this.menuListView.setAdapter(adapter);
    addItemList(builder.Ts);
  }

  @Override
  protected void initialize(@NonNull Context context) {
    super.initialize(context);
    this.adapter = (E) (new MenuBaseAdapter<>(menuListView));
  }

  /** Builder class for creating {@link CustomPowerMenu}. */
  @SuppressWarnings("unchecked")
  public static class Builder<T, E extends MenuBaseAdapter<T>> extends AbstractMenuBuilder {

    private OnMenuItemClickListener<T> menuItemClickListener = null;

    private E adapter;
    private List<T> Ts;

    public Builder(@NonNull Context context, @NonNull E adapter) {
      this.context = context;
      this.Ts = new ArrayList<>();
      this.adapter = adapter;
      this.layoutInflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Builder setLifecycleOwner(@NonNull LifecycleOwner lifecycleOwner) {
      this.lifecycleOwner = lifecycleOwner;
      return this;
    }

    public Builder setShowBackground(boolean show) {
      this.showBackground = show;
      return this;
    }

    public Builder setOnMenuItemClickListener(Object menuItemClickListener) {
      this.menuItemClickListener = (OnMenuItemClickListener<T>) menuItemClickListener;
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

    public Builder setAutoDismiss(boolean autoDismiss) {
      this.autoDismiss = autoDismiss;
      return this;
    }

    public Builder addItem(Object item) {
      this.Ts.add((T) item);
      return this;
    }

    public Builder addItem(int position, Object item) {
      this.Ts.add(position, (T) item);
      return this;
    }

    public Builder addItemList(List<T> itemList) {
      this.Ts.addAll(itemList);
      return this;
    }

    public Builder setPreferenceName(@NonNull String preferenceName) {
      this.preferenceName = preferenceName;
      return this;
    }

    public Builder setInitializeRule(@NonNull Lifecycle.Event event, int defaultPosition) {
      this.initializeRule = event;
      this.defaultPosition = defaultPosition;
      return this;
    }

    public Builder setMenuEffect(@NonNull MenuEffect menuEffect) {
      this.menuEffect = menuEffect;
      return this;
    }

    public CustomPowerMenu build() {
      return new CustomPowerMenu<>(context, this);
    }
  }

  /**
   * An abstract factory class for creating an instance of {@link CustomPowerMenu}.
   *
   * <p>A factory implementation class must have a non-argument constructor.
   */
  public abstract static class Factory {

    /** returns an instance of {@link CustomPowerMenu}. */
    public abstract @NonNull CustomPowerMenu create(
        @NonNull Context context, @NonNull LifecycleOwner lifecycle);
  }
}
