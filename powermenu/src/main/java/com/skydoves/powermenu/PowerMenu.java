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
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

    /**
     * Builder class for creating {@link PowerMenu}.
     *
     * @param context context.
     */
    public Builder(@NonNull Context context) {
      this.context = context;
      this.powerMenuItems = new ArrayList<>();
      this.layoutInflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * sets the {@link LifecycleOwner} for dismissing automatically when the {@link LifecycleOwner}
     * is destroyed. It will prevents memory leak.
     *
     * @param lifecycleOwner lifecycle owner
     * @return {@link Builder}.
     * @see <a href="https://github.com/skydoves/PowerMenu#avoid-memory-leak">GitHub :
     *     PowerMenu-Avoid-Memory-Leak</a>
     */
    public Builder setLifecycleOwner(@NonNull LifecycleOwner lifecycleOwner) {
      this.lifecycleOwner = lifecycleOwner;
      return this;
    }

    /**
     * sets the visibility of the background popup.
     *
     * @param show visibility of the background popup.
     * @return {@link Builder}.
     */
    public Builder setShowBackground(boolean show) {
      this.showBackground = show;
      return this;
    }

    /**
     * sets the {@link OnMenuItemClickListener} to the popup.
     *
     * @param menuItemClickListener {@link OnMenuItemClickListener} interface.
     * @return {@link Builder}.
     */
    public Builder setOnMenuItemClickListener(
        OnMenuItemClickListener<PowerMenuItem> menuItemClickListener) {
      this.menuItemClickListener = menuItemClickListener;
      return this;
    }

    /**
     * sets the {@link View.OnClickListener} to the popup.
     *
     * @param onBackgroundClickListener {@link View.OnClickListener} interface.
     * @return {@link Builder}.
     */
    public Builder setOnBackgroundClickListener(View.OnClickListener onBackgroundClickListener) {
      this.backgroundClickListener = onBackgroundClickListener;
      return this;
    }

    /**
     * sets the {@link OnDismissedListener} to the popup.
     *
     * @param onDismissListener {@link OnDismissedListener} interface.
     * @return {@link Builder}.
     */
    public Builder setOnDismissListener(OnDismissedListener onDismissListener) {
      this.onDismissedListener = onDismissListener;
      return this;
    }

    /**
     * sets the header view by layout resource.
     *
     * @param headerView layout resource
     * @return {@link Builder}.
     */
    public Builder setHeaderView(@LayoutRes int headerView) {
      this.headerView = layoutInflater.inflate(headerView, null);
      return this;
    }

    /**
     * sets the header view by layout view.
     *
     * @param headerView header view.
     * @return {@link Builder}.
     */
    public Builder setHeaderView(View headerView) {
      this.headerView = headerView;
      return this;
    }

    /**
     * sets the footer view by layout resource.
     *
     * @param footerView footer view.
     * @return {@link Builder}.
     */
    public Builder setFooterView(@LayoutRes int footerView) {
      this.footerView = layoutInflater.inflate(footerView, null);
      return this;
    }

    /**
     * sets the footer view by layout view.
     *
     * @param footerView footer view.
     * @return {@link Builder}.
     */
    public Builder setFooterView(View footerView) {
      this.footerView = footerView;
      return this;
    }

    /**
     * sets the menu animation to the popup.
     *
     * @param menuAnimation animation.
     * @return {@link Builder}.
     */
    public Builder setAnimation(@NonNull MenuAnimation menuAnimation) {
      this.menuAnimation = menuAnimation;
      return this;
    }

    /**
     * sets the customized menu animation using resource.
     *
     * @param style animation resource.
     * @return {@link Builder}.
     */
    public Builder setAnimationStyle(int style) {
      this.animationStyle = style;
      return this;
    }

    /**
     * sets the corner radius of the popup menu.
     *
     * @param radius corner radius.
     * @return {@link Builder}.
     */
    public Builder setMenuRadius(float radius) {
      this.menuRadius = radius;
      return this;
    }

    /**
     * sets the shadow of the popup menu.
     *
     * @param shadow popup shadow.
     * @return {@link Builder}.
     */
    public Builder setMenuShadow(float shadow) {
      this.menuShadow = shadow;
      return this;
    }

    /**
     * sets the width size of the popup menu.
     *
     * @param width width size.
     * @return {@link Builder}.
     */
    public Builder setWidth(int width) {
      this.width = width;
      return this;
    }

    /**
     * sets the height size of the popup menu.
     *
     * @param height height size.
     * @return {@link Builder}.
     */
    public Builder setHeight(int height) {
      this.height = height;
      return this;
    }

    /**
     * sets the width and height size of the popup menu.
     *
     * @param width width size.
     * @param height height size.
     * @return {@link Builder}.
     */
    public Builder setSize(int width, int height) {
      this.width = width;
      this.height = height;
      return this;
    }

    /**
     * sets the content text color of the popup menu item.
     *
     * @param color menu item's content text color.
     * @return {@link Builder}.
     */
    public Builder setTextColor(int color) {
      this.textColor = color;
      return this;
    }

    /**
     * sets the content text color of the popup menu item.
     *
     * @param color menu item's content text color by resource.
     * @return {@link Builder}.
     */
    public Builder setTextColorResource(int color) {
      this.textColor = ContextCompat.getColor(context, color);
      return this;
    }

    /**
     * sets the content text size of the popup menu item.
     *
     * @param size menu item's content text size.
     * @return {@link Builder}.
     */
    public Builder setTextSize(int size) {
      this.textSize = size;
      return this;
    }

    /**
     * sets the content text {@link Gravity} of the popup menu item.
     *
     * @param gravity menu item's content text gravity.
     * @return {@link Builder}.
     */
    public Builder setTextGravity(int gravity) {
      this.textGravity = gravity;
      return this;
    }

    /**
     * sets the content text {@link Typeface} of the popup menu item.
     *
     * @param typeface menu item's content text typeface.
     * @return {@link Builder}.
     */
    public Builder setTextTypeface(@NonNull Typeface typeface) {
      this.textTypeface = typeface;
      return this;
    }

    /**
     * sets the normal menu item background color.
     *
     * @param color normal menu item background color.
     * @return {@link Builder}.
     */
    public Builder setMenuColor(int color) {
      this.menuColor = color;
      return this;
    }

    /**
     * sets the normal menu item background color.
     *
     * @param color normal menu item background color by resource.
     * @return {@link Builder}.
     */
    public Builder setMenuColorResource(int color) {
      this.menuColor = ContextCompat.getColor(context, color);
      return this;
    }

    /**
     * sets the selected menu item text color.
     *
     * @param color selected menu item text color.
     * @return {@link Builder}.
     */
    public Builder setSelectedTextColor(int color) {
      this.selectedTextColor = color;
      return this;
    }

    /**
     * sets the selected menu item text color.
     *
     * @param color selected menu item text color by resource.
     * @return {@link Builder}.
     */
    public Builder setSelectedTextColorResource(int color) {
      this.selectedTextColor = ContextCompat.getColor(context, color);
      return this;
    }

    /**
     * sets the selected menu item background color.
     *
     * @param color selected menu item background color.
     * @return {@link Builder}.
     */
    public Builder setSelectedMenuColor(int color) {
      this.selectedMenuColor = color;
      return this;
    }

    /**
     * sets the selected menu item background color.
     *
     * @param color selected menu item background color by resource.
     * @return {@link Builder}.
     */
    public Builder setSelectedMenuColorResource(int color) {
      this.selectedMenuColor = ContextCompat.getColor(context, color);
      return this;
    }

    /**
     * sets true or false of the menu item selected effect
     *
     * @param effect the menu item selected effect.
     * @return {@link Builder}.
     */
    public Builder setSelectedEffect(boolean effect) {
      this.selectedEffect = effect;
      return this;
    }

    /**
     * sets the divider height between the menu items.
     *
     * @param height divider height between the menu items.
     * @return {@link Builder}.
     */
    public Builder setDividerHeight(int height) {
      this.dividerHeight = height;
      return this;
    }

    /**
     * sets the divider {@link Drawable} between the menu items.
     *
     * @param divider divider {@link Drawable} between the menu items.
     * @return {@link Builder}.
     */
    public Builder setDivider(Drawable divider) {
      this.divider = divider;
      return this;
    }

    /**
     * sets the color of the background popup.
     *
     * @param color color of the background popup.
     * @return {@link Builder}.
     */
    public Builder setBackgroundColor(int color) {
      this.backgroundColor = color;
      return this;
    }

    /**
     * sets the color of the background popup.
     *
     * @param color color of the background popup by resource.
     * @return {@link Builder}.
     */
    public Builder setBackgroundColorResource(int color) {
      this.backgroundColor = ContextCompat.getColor(context, color);
      return this;
    }

    /**
     * sets the alpha of the background popup.
     *
     * @param alpha alpha of the background popup.
     * @return {@link Builder}.
     */
    public Builder setBackgroundAlpha(float alpha) {
      this.backgroundAlpha = alpha;
      return this;
    }

    /**
     * sets the system UI visibility of the background popup.
     *
     * @param visibility system UI visibility of the background popup.
     * @return {@link Builder}.
     */
    public Builder setBackgroundSystemUiVisibility(int visibility) {
      this.backgroundSystemUiVisibility = visibility;
      return this;
    }

    /**
     * sets the focusability of the popup menu.
     *
     * @param focusable focusability of the popup menu.
     * @return {@link Builder}.
     */
    public Builder setFocusable(boolean focusable) {
      this.focusable = focusable;
      return this;
    }

    /**
     * sets the initialized selected effect menu item position.
     *
     * @param position initialized selected effect menu item position.
     * @return {@link Builder}.
     */
    public Builder setSelected(int position) {
      this.selected = position;
      return this;
    }

    /**
     * sets the clipping or not of the popup menu.
     *
     * @param isClipping clipping or not of the popup menu.
     * @return {@link Builder}.
     */
    public Builder setIsClipping(boolean isClipping) {
      this.isClipping = isClipping;
      return this;
    }

    /**
     * sets the dismissing automatically when the menu item is clicked.
     *
     * @param autoDismiss dismissing automatically when the menu item is clicked.
     * @return {@link Builder}.
     */
    public Builder setAutoDismiss(boolean autoDismiss) {
      this.autoDismiss = autoDismiss;
      return this;
    }

    /**
     * adds an {@link PowerMenuItem} item to the popup menu list.
     *
     * @param item {@link PowerMenuItem} item.
     * @return {@link Builder}.
     */
    public Builder addItem(PowerMenuItem item) {
      this.powerMenuItems.add(item);
      return this;
    }

    /**
     * adds an {@link PowerMenuItem} item to the popup menu list at position.
     *
     * @param position specific position.
     * @param item {@link PowerMenuItem} item.
     * @return {@link Builder}.
     */
    public Builder addItem(int position, PowerMenuItem item) {
      this.powerMenuItems.add(position, item);
      return this;
    }

    /**
     * adds a list of the {@link PowerMenuItem} item to the popup menu list.
     *
     * @param itemList list of the {@link PowerMenuItem}.
     * @return {@link Builder}.
     */
    public Builder addItemList(List<PowerMenuItem> itemList) {
      this.powerMenuItems.addAll(itemList);
      return this;
    }

    /**
     * sets the preference name of the popup menu. For persistence the clicked item position. If the
     * preference name is set, the persisted position item will be clicked automatically when the
     * popup menu initialized. This option should be used with setInitializeRule and
     * setLifecycleOwner.
     *
     * @param preferenceName name of the popup menu.
     * @return {@link Builder}.
     * @see <a href="https://github.com/skydoves/PowerMenu#preference"</a>
     */
    public Builder setPreferenceName(@NonNull String preferenceName) {
      this.preferenceName = preferenceName;
      return this;
    }

    /**
     * sets the initialization rule of the recovering persisted position.
     *
     * @param event when should be recovered.
     * @param defaultPosition default selected position.
     * @return {@link Builder}.
     */
    public Builder setInitializeRule(@NonNull Lifecycle.Event event, int defaultPosition) {
      this.initializeRule = event;
      this.defaultPosition = defaultPosition;
      return this;
    }

    /**
     * sets the circular revealed effect using {@link CircularEffect}.
     *
     * @param circularEffect circular revealed effect using {@link CircularEffect}.
     * @return @return {@link Builder}.
     */
    public Builder setCircularEffect(@NonNull CircularEffect circularEffect) {
      this.circularEffect = circularEffect;
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
