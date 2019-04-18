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
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.MenuEffect;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.skydoves.powermenudemo.customs.adapters.CenterMenuAdapter;
import com.skydoves.powermenudemo.customs.adapters.CustomDialogMenuAdapter;
import com.skydoves.powermenudemo.customs.items.NameCardMenuItem;

@SuppressWarnings({"WeakerAccess", "unchecked"})
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
        .setMenuEffect(MenuEffect.BODY)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setTextColor(context.getResources().getColor(R.color.md_grey_800))
        .setTextSize(12)
        .setTextGravity(Gravity.CENTER)
        .setSelectedTextColor(Color.WHITE)
        .setMenuColor(Color.WHITE)
        .setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary))
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
        .setTextColor(context.getResources().getColor(R.color.md_grey_800))
        .setMenuColor(Color.WHITE)
        .setSelectedEffect(false)
        .setShowBackground(false)
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .build();
  }

  public static CustomPowerMenu getWritePowerMenu(
      Context context,
      LifecycleOwner lifecycleOwner,
      OnMenuItemClickListener onMenuItemClickListener) {
    ColorDrawable drawable =
        new ColorDrawable(context.getResources().getColor(R.color.md_blue_grey_300));
    return new CustomPowerMenu.Builder<>(context, new CenterMenuAdapter())
        .addItem("Novel")
        .addItem("Poetry")
        .addItem("Art")
        .addItem("Journals")
        .addItem("Travel")
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.FADE)
        .setMenuEffect(MenuEffect.BODY)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setDivider(drawable)
        .setDividerHeight(1)
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .build();
  }

  public static CustomPowerMenu getAlertPowerMenu(
      Context context,
      LifecycleOwner lifecycleOwner,
      OnMenuItemClickListener onMenuItemClickListener) {
    return new CustomPowerMenu.Builder<>(context, new CenterMenuAdapter())
        .addItem("You need to login!")
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.ELASTIC_CENTER)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .setFocusable(true)
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .setOnBackgroundClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {}
            })
        .build();
  }

  public static PowerMenu getIconPowerMenu(
      Context context,
      LifecycleOwner lifecycleOwner,
      OnMenuItemClickListener onMenuItemClickListener) {

    return new PowerMenu.Builder(context)
        .addItem(new PowerMenuItem("WeChat", R.drawable.ic_wechat))
        .addItem(new PowerMenuItem("Facebook", R.drawable.ic_facebook))
        .addItem(new PowerMenuItem("Twitter", R.drawable.ic_twitter))
        .addItem(new PowerMenuItem("Line", R.drawable.ic_line))
        .addItem(new PowerMenuItem("Other"))
        .setLifecycleOwner(lifecycleOwner)
        .setOnMenuItemClickListener(onMenuItemClickListener)
        .setAnimation(MenuAnimation.FADE)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
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
        .setWidth(600)
        .setSelectedEffect(false)
        .build();
  }

  public static CustomPowerMenu getCustomDialogPowerMenu(
      Context context, LifecycleOwner lifecycleOwner) {
    return new CustomPowerMenu.Builder<>(context, new CustomDialogMenuAdapter())
        .setHeaderView(R.layout.layout_custom_dialog_header)
        .setFooterView(R.layout.layout_custom_dialog_footer)
        .addItem(
            new NameCardMenuItem(
                context.getResources().getDrawable(R.drawable.face3),
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
