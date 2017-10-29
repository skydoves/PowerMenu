package com.skydoves.powermenudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.skydoves.powermenudemo.customs.items.IconPowerMenuItem;

/**
 * Developed by skydoves on 2017-10-29.
 * Copyright (c) 2017 skydoves rights reserved.
 */

public class MainActivity extends AppCompatActivity {

    private PowerMenu hamburgerMenu;
    private PowerMenu profileMenu;
    private CustomPowerMenu writeMenu;
    private CustomPowerMenu alertMenu;
    private CustomPowerMenu iconMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hamburgerMenu = PowerMenuUtils.getHamburgerPowerMenu(this, onHamburgerItemClickListener);
        profileMenu = PowerMenuUtils.getProfilePowerMenu(this, onProfileItemClickListener);
        writeMenu = PowerMenuUtils.getWritePowerMenu(this, onWriteItemClickListener);
        alertMenu = PowerMenuUtils.getAlertPowerMenu(this, onAlertItemClickListener);
        iconMenu = PowerMenuUtils.getIconPowerMenu(this, onIconMenuItemClickListener);
    }

    private  OnMenuItemClickListener<PowerMenuItem> onHamburgerItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            hamburgerMenu.setSelected(position);
            hamburgerMenu.dismiss();
        }
    };

    private OnMenuItemClickListener<PowerMenuItem> onProfileItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            profileMenu.dismiss();
        }
    };

    private OnMenuItemClickListener<String> onWriteItemClickListener = new OnMenuItemClickListener<String>() {
        @Override
        public void onItemClick(int position, String title) {
            Toast.makeText(getBaseContext(), title, Toast.LENGTH_SHORT).show();
            writeMenu.dismiss();
        }
    };

    private OnMenuItemClickListener<String> onAlertItemClickListener = new OnMenuItemClickListener<String>() {
        @Override
        public void onItemClick(int position, String title) {
            Toast.makeText(getBaseContext(), title, Toast.LENGTH_SHORT).show();
            alertMenu.dismiss();
        }
    };

    private OnMenuItemClickListener<IconPowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            iconMenu.dismiss();
        }
    };

    public void onHamburger(View view) {
        if(hamburgerMenu.isShowing()) {
            hamburgerMenu.dismiss();
            return;
        }
        hamburgerMenu.showAsDropDown(view);
    }

    public void onProfile(View view) {
        if(profileMenu.isShowing()) {
            profileMenu.dismiss();
            return;
        }
        profileMenu.showAsDropDown(view, -370, 0);
    }

    public void onWrite(View view) {
        if(writeMenu.isShowing()) {
            writeMenu.dismiss();
            return;
        }
        View layout = findViewById(R.id.layout_main);
        writeMenu.showAtCenter(layout);
    }

    public void onAlert(View view) {
        if(alertMenu.isShowing()) {
            alertMenu.dismiss();
            return;
        }
        View layout = findViewById(R.id.layout_main);
        alertMenu.showAtCenter(layout);
    }

    public void onShare(View view) {
        if(iconMenu.isShowing()) {
            iconMenu.dismiss();
            return;
        }
        iconMenu.showAsDropDown(view, -370, 0);
    }

    @Override
    public void onBackPressed() {
        if(hamburgerMenu.isShowing())
            hamburgerMenu.dismiss();
        else if(profileMenu.isShowing())
            profileMenu.dismiss();
        else if(writeMenu.isShowing())
            writeMenu.dismiss();
        else if(alertMenu.isShowing())
            alertMenu.dismiss();
        else if(iconMenu.isShowing())
            iconMenu.dismiss();
        else
            super.onBackPressed();
    }
}
