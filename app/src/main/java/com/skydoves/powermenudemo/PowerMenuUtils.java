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

package com.skydoves.powermenudemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.skydoves.powermenudemo.customs.adapters.CenterMenuAdapter;
import com.skydoves.powermenudemo.customs.adapters.CustomDialogMenuAdapter;
import com.skydoves.powermenudemo.customs.items.NameCardMenuItem;

public class PowerMenuUtils {

  public static PowerMenu getHamburgerPowerMenu(
      Context context,
      LifecycleOwner lifecycleOwner,
      OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener,
      OnDismissedListener onDismissedListener) {
    return new PowerMenu.Builder(context)
        .addItem(new PowerMenuItem("Novel", true))
        .addItem(new PowerMenuItem("Poetry", false))
        .addItem(new PowerMenuItem("Art", false))
        .addItem(new PowerMenuItem("Journals", false))
        .addItem(new PowerMenuItem("Travel", false))
        .setAutoDismiss(true)
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
        .setCircularEffect(CircularEffect.BODY)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setTextColor(ContextCompat.getColor(context, R.color.md_grey_800))
        .setTextSize(12)
        .setTextGravity(Gravity.CENTER)
        .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
        .setSelectedTextColor(Color.WHITE)
        .setMenuColor(Color.WHITE)
        .setSelectedMenuColor(ContextCompat.getColor(context, R.color.colorPrimary))
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .setOnDismissListener(onDismissedListener)
        .setPreferenceName("HamburgerPowerMenu")
        .setInitializeRule(Lifecycle.Event.ON_CREATE, 0)
        .build();
  }

  public static PowerMenu getProfilePowerMenu(
      Context context,
      LifecycleOwner lifecycleOwner,
      OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {
    return new PowerMenu.Builder(context)
        .setHeaderView(R.layout.item_title_header)
        .addItem(new PowerMenuItem("Profile", false))
        .addItem(new PowerMenuItem("Board", false))
        .addItem(new PowerMenuItem("Logout", false))
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setTextColor(ContextCompat.getColor(context, R.color.md_grey_800))
        .setTextGravity(Gravity.CENTER)
        .setMenuColor(Color.WHITE)
        .setSelectedEffect(false)
        .setShowBackground(false)
        .setFocusable(true)
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .build();
  }

  public static CustomPowerMenu<String, CenterMenuAdapter> getWritePowerMenu(
      Context context,
      LifecycleOwner lifecycleOwner,
      OnMenuItemClickListener<String> onMenuItemClickListener) {
    ColorDrawable drawable =
        new ColorDrawable(ContextCompat.getColor(context, R.color.md_blue_grey_300));
    return new CustomPowerMenu.Builder<>(context, new CenterMenuAdapter())
        .addItem("Novel")
        .addItem("Poetry")
        .addItem("Art")
        .addItem("Journals")
        .addItem("Travel")
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.FADE)
        .setCircularEffect(CircularEffect.BODY)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setDivider(drawable)
        .setDividerHeight(1)
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .build();
  }

  public static CustomPowerMenu<String, CenterMenuAdapter> getAlertPowerMenu(
      Context context,
      LifecycleOwner lifecycleOwner,
      OnMenuItemClickListener<String> onMenuItemClickListener) {
    return new CustomPowerMenu.Builder<>(context, new CenterMenuAdapter())
        .addItem("You need to login!")
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.ELASTIC_CENTER)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setFocusable(true)
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .setOnBackgroundClickListener(view -> {})
        .build();
  }

  public static PowerMenu getIconPowerMenu(
      Context context,
      LifecycleOwner lifecycleOwner,
      OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {

    Context styledContext = new ContextThemeWrapper(context, R.style.PopupCardThemeOverlay);

    return new PowerMenu.Builder(styledContext)
        .addItem(new PowerMenuItem("WeChat", R.drawable.ic_wechat))
        .addItem(new PowerMenuItem("Facebook", R.drawable.ic_facebook))
        .addItem(new PowerMenuItem("Twitter", R.drawable.ic_twitter))
        .addItem(new PowerMenuItem("Line", R.drawable.ic_line))
        .addItem(new PowerMenuItem("Other"))
        .setLifecycleOwner(lifecycleOwner)
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .setAnimation(MenuAnimation.FADE)
        .setMenuRadius(context.getResources().getDimensionPixelSize(R.dimen.menu_corner_radius))
        .setMenuShadow(context.getResources().getDimensionPixelSize(R.dimen.menu_elevation))
        .build();
  }

  public static PowerMenu getDialogPowerMenu(Context context, LifecycleOwner lifecycleOwner) {
    return new PowerMenu.Builder(context)
        .setHeaderView(R.layout.layout_dialog_header)
        .setFooterView(R.layout.layout_dialog_footer)
        .addItem(new PowerMenuItem("This is DialogPowerMenu", false))
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.SHOW_UP_CENTER)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setPadding(14)
        .setWidth(600)
        .setSelectedEffect(false)
        .build();
  }

  public static CustomPowerMenu<NameCardMenuItem, CustomDialogMenuAdapter> getCustomDialogPowerMenu(
      Context context, LifecycleOwner lifecycleOwner) {
    return new CustomPowerMenu.Builder<>(context, new CustomDialogMenuAdapter())
        .setHeaderView(R.layout.layout_custom_dialog_header)
        .setFooterView(R.layout.layout_custom_dialog_footer)
        .addItem(
            new NameCardMenuItem(
                ContextCompat.getDrawable(context, R.drawable.face3),
                "Sophie",
                context.getString(R.string.board3)))
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.SHOW_UP_CENTER)
        .setWidth(800)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .build();
  }
}
