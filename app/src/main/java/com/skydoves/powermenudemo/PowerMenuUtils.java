package com.skydoves.powermenudemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.skydoves.powermenudemo.customs.adapters.CenterMenuAdapter;
import com.skydoves.powermenudemo.customs.adapters.IconMenuAdapter;
import com.skydoves.powermenudemo.customs.items.IconPowerMenuItem;

/**
 * Developed by skydoves on 2017-10-29.
 * Copyright (c) 2017 skydoves rights reserved.
 */

public class PowerMenuUtils {

    public static PowerMenu getHamburgerPowerMenu(Context context, OnMenuItemClickListener onMenuItemClickListener) {
        return new PowerMenu.Builder(context)
                .addItem(new PowerMenuItem("Novel", true))
                .addItem(new PowerMenuItem("Poetry", false))
                .addItem(new PowerMenuItem("Art", false))
                .addItem(new PowerMenuItem("Journals", false))
                .addItem(new PowerMenuItem("Travel", false))
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(context.getResources().getColor(R.color.md_grey_800))
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
    }

    public static PowerMenu getProfilePowerMenu(Context context, OnMenuItemClickListener onMenuItemClickListener) {
        return new PowerMenu.Builder(context)
                .addItem(new PowerMenuItem("Profile", false))
                .addItem(new PowerMenuItem("Board", false))
                .addItem(new PowerMenuItem("Logout", false))
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(context.getResources().getColor(R.color.md_grey_800))
                .setMenuColor(Color.WHITE)
                .setSelectedEffect(false)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
    }

    public static CustomPowerMenu getWritePowerMenu(Context context, OnMenuItemClickListener onMenuItemClickListener) {
        ColorDrawable drawable = new ColorDrawable(context.getResources().getColor(R.color.md_blue_grey_300));
        return new CustomPowerMenu.Builder<>(context, new CenterMenuAdapter())
                .addItem("Novel")
                .addItem("Poetry")
                .addItem("Art")
                .addItem("Journals")
                .addItem("Travel")
                .setAnimation(MenuAnimation.FADE)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setDivider(drawable)
                .setDividerHeight(1)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
    }

    public static CustomPowerMenu getAlertPowerMenu(Context context, OnMenuItemClickListener onMenuItemClickListener) {
        return new CustomPowerMenu.Builder<>(context, new CenterMenuAdapter())
                .addItem("You need to login!")
                .setAnimation(MenuAnimation.ELASTIC_CENTER)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setFocusable(true)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setOnBackgroundClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).build();
    }

    public static CustomPowerMenu getIconPowerMenu(Context context, OnMenuItemClickListener onMenuItemClickListener) {
        return new CustomPowerMenu.Builder<>(context, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_wechat), "WeChat"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_facebook), "Facebook"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_twitter), "Twitter"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_line), "Line"))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .build();
    }
}
