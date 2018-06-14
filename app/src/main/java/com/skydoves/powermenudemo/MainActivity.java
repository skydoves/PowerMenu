package com.skydoves.powermenudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuDismissListener;
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
    private PowerMenu dialogMenu;
    private CustomPowerMenu customDialogMenu;
    private CustomPowerMenu iconMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hamburgerMenu = PowerMenuUtils.getHamburgerPowerMenu(this, this, onHamburgerItemClickListener);
        profileMenu = PowerMenuUtils.getProfilePowerMenu(this, this, onProfileItemClickListener);
        writeMenu = PowerMenuUtils.getWritePowerMenu(this, this, onWriteItemClickListener);
        alertMenu = PowerMenuUtils.getAlertPowerMenu(this, this, onAlertItemClickListener);
        iconMenu = PowerMenuUtils.getIconPowerMenu(this, this, onIconMenuItemClickListener);

        hamburgerMenu.setOnDismissListener(new PowerMenuDismissListener() {
            @Override
            public void onPowerMenuDismiss() {
                // do something on dismiss
            }
        });

        initializeDialogMenu();
        initializeCustomDialogMenu();
    }

    private void initializeDialogMenu() {
        dialogMenu = PowerMenuUtils.getDialogPowerMenu(this, this);
        View footerView = dialogMenu.getFooterView();
        TextView textView_yes = footerView.findViewById(R.id.textView_yes);
        textView_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Yes", Toast.LENGTH_SHORT).show();
                dialogMenu.dismiss();
            }
        });
        TextView textView_no = footerView.findViewById(R.id.textView_no);
        textView_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "No", Toast.LENGTH_SHORT).show();
                dialogMenu.dismiss();
            }
        });
    }

    private void initializeCustomDialogMenu() {
        customDialogMenu = PowerMenuUtils.getCustomDialogPowerMenu(this, this);
        View footerView = customDialogMenu.getFooterView();
        TextView textView_yes = footerView.findViewById(R.id.textView_yes);
        textView_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Read More", Toast.LENGTH_SHORT).show();
                customDialogMenu.dismiss();
            }
        });
        TextView textView_no = footerView.findViewById(R.id.textView_no);
        textView_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Close", Toast.LENGTH_SHORT).show();
                customDialogMenu.dismiss();
            }
        });
    }

    private  OnMenuItemClickListener<PowerMenuItem> onHamburgerItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            hamburgerMenu.setSelectedPosition(position);
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

    public void onDialog(View view) {
        if(dialogMenu.isShowing()) {
            dialogMenu.dismiss();
            return;
        }
        View layout = findViewById(R.id.layout_main);
        dialogMenu.showAtCenter(layout);
    }

    public void onCustomDialog(View view) {
        if(customDialogMenu.isShowing()) {
            customDialogMenu.dismiss();
            return;
        }
        View layout = findViewById(R.id.layout_main);
        customDialogMenu.showAtCenter(layout);
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
        else if(dialogMenu.isShowing())
            dialogMenu.dismiss();
        else if(customDialogMenu.isShowing())
            customDialogMenu.dismiss();
        else if(iconMenu.isShowing())
            iconMenu.dismiss();
        else
            super.onBackPressed();
    }
}
