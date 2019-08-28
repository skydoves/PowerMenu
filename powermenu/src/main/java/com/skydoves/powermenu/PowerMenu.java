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
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.skydoves.powermenu.kotlin.PowerMenuDsl;
import java.util.ArrayList;
import java.util.List;

/**
 * PowerMenu is one the implementation of the {@link AbstractPowerMenu}.
 *
 * <p>It implements the popup showing with the item selected effect by {@link MenuListAdapter}.
 *
 * <p>{@link PowerMenuItem} is the member of the PowerMenu's list.
 */
@SuppressWarnings("unused")
public class PowerMenu extends AbstractPowerMenu<PowerMenuItem, MenuListAdapter>
    implements IPowerMenuAdapter {

  private PowerMenu(@NonNull Context context, @NonNull AbstractMenuBuilder abstractMenuBuilder) {
    super(context, abstractMenuBuilder);

    Builder builder = (Builder) abstractMenuBuilder;

    setSelectedEffect(builder.selectedEffect);
    if (builder.menuItemClickListener != null)
      setOnMenuItemClickListener(builder.menuItemClickListener);
    if (builder.textColor != -2) setTextColor(builder.textColor);
    if (builder.menuColor != -2) setMenuColor(builder.menuColor);
    if (builder.selectedTextColor != -2) setSelectedTextColor(builder.selectedTextColor);
    if (builder.selectedMenuColor != -2) setSelectedMenuColor(builder.selectedMenuColor);
    if (builder.selected != -1) setSelectedPosition(builder.selected);
    if (builder.textSize != 12) setTextSize(builder.textSize);
    if (builder.textGravity != Gravity.START) setTextGravity(builder.textGravity);
    if (builder.textTypeface != null) setTextTypeface(builder.textTypeface);

    this.menuListView.setAdapter(adapter);
    addItemList(builder.powerMenuItems);
  }

  @Override
  protected void initialize(@NonNull Context context) {
    super.initialize(context);
    this.adapter = new MenuListAdapter(menuListView);
  }

  @Override
  public void setTextColor(int color) {
    this.getAdapter().setTextColor(color);
  }

  @Override
  public void setMenuColor(int color) {
    this.getAdapter().setMenuColor(color);
  }

  @Override
  public void setSelectedTextColor(int color) {
    this.getAdapter().setSelectedTextColor(color);
  }

  @Override
  public void setSelectedMenuColor(int color) {
    this.getAdapter().setSelectedMenuColor(color);
  }

  @Override
  public void setSelectedEffect(boolean selectedEffect) {
    getAdapter().setSelectedEffect(selectedEffect);
  }

  @Override
  public void setTextSize(int size) {
    this.getAdapter().setTextSize(size);
  }

  @Override
  public void setTextGravity(int gravity) {
    this.getAdapter().setTextGravity(gravity);
  }

  @Override
  public void setTextTypeface(Typeface typeface) {
    this.getAdapter().setTextTypeface(typeface);
  }

  /** Builder class for creating {@link PowerMenu}. */
  @PowerMenuDsl
  public static class Builder extends AbstractMenuBuilder {

    private OnMenuItemClickListener<PowerMenuItem> menuItemClickListener = null;
    private int textColor = -2;
    private int menuColor = -2;
    private boolean selectedEffect = true;
    private int selectedTextColor = -2;
    private int selectedMenuColor = -2;
    private int textSize = 12;
    private int textGravity = Gravity.START;
    private Typeface textTypeface = null;

    private List<PowerMenuItem> powerMenuItems;

    public Builder(@NonNull Context context) {
      this.context = context;
      this.powerMenuItems = new ArrayList<>();
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

    public Builder setOnMenuItemClickListener(
        OnMenuItemClickListener<PowerMenuItem> menuItemClickListener) {
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

    public Builder setTextSize(int size) {
      this.textSize = size;
      return this;
    }

    public Builder setTextGravity(int gravity) {
      this.textGravity = gravity;
      return this;
    }

    public Builder setTextTypeface(@NonNull Typeface typeface) {
      this.textTypeface = typeface;
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

    public Builder setPreferenceName(String preferenceName) {
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

    public PowerMenu build() {
      return new PowerMenu(context, this);
    }
  }

  /**
   * An abstract factory class for creating an instance of {@link PowerMenu}.
   *
   * <p>A factory implementation class must have a non-argument constructor.
   */
  public abstract static class Factory {

    /** returns an instance of {@link PowerMenu}. */
    public abstract @NonNull PowerMenu create(
        @NonNull Context context, @NonNull LifecycleOwner lifecycle);
  }
}
