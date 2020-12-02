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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.skydoves.powermenudemo.customs.adapters.CenterMenuAdapter;
import com.skydoves.powermenudemo.customs.adapters.CustomDialogMenuAdapter;
import com.skydoves.powermenudemo.customs.items.NameCardMenuItem;
import com.skydoves.powermenudemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  private PowerMenu hamburgerMenu;
  private PowerMenu profileMenu;
  private CustomPowerMenu<String, CenterMenuAdapter> writeMenu;
  private CustomPowerMenu<String, CenterMenuAdapter> alertMenu;
  private PowerMenu dialogMenu;
  private CustomPowerMenu<NameCardMenuItem, CustomDialogMenuAdapter> customDialogMenu;
  private PowerMenu iconMenu;
  private final OnMenuItemClickListener<PowerMenuItem> onHamburgerItemClickListener =
      new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
          Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
          hamburgerMenu.setSelectedPosition(position);
        }
      };
  private final OnDismissedListener onHamburgerMenuDismissedListener =
      () -> Log.d("Test", "onDismissed hamburger menu");
  private final OnMenuItemClickListener<PowerMenuItem> onProfileItemClickListener =
      new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
          Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
          profileMenu.dismiss();
        }
      };
  private final OnMenuItemClickListener<String> onWriteItemClickListener =
      new OnMenuItemClickListener<String>() {
        @Override
        public void onItemClick(int position, String title) {
          Toast.makeText(getBaseContext(), title, Toast.LENGTH_SHORT).show();
          writeMenu.dismiss();
        }
      };
  private final OnMenuItemClickListener<String> onAlertItemClickListener =
      new OnMenuItemClickListener<String>() {
        @Override
        public void onItemClick(int position, String title) {
          Toast.makeText(getBaseContext(), title, Toast.LENGTH_SHORT).show();
          alertMenu.dismiss();
        }
      };
  private final OnMenuItemClickListener<PowerMenuItem> onIconMenuItemClickListener =
      new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
          Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
          iconMenu.dismiss();
        }
      };

  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());

    setContentView(binding.getRoot());

    hamburgerMenu =
        PowerMenuUtils.getHamburgerPowerMenu(
            this, this, onHamburgerItemClickListener, onHamburgerMenuDismissedListener);
    profileMenu = PowerMenuUtils.getProfilePowerMenu(this, this, onProfileItemClickListener);
    writeMenu = PowerMenuUtils.getWritePowerMenu(this, this, onWriteItemClickListener);
    alertMenu = PowerMenuUtils.getAlertPowerMenu(this, this, onAlertItemClickListener);
    iconMenu = PowerMenuUtils.getIconPowerMenu(this, this, onIconMenuItemClickListener);

    initializeDialogMenu();
    initializeCustomDialogMenu();
  }

  private void initializeDialogMenu() {
    dialogMenu = PowerMenuUtils.getDialogPowerMenu(this, this);
    View footerView = dialogMenu.getFooterView();
    TextView textView_yes = footerView.findViewById(R.id.textView_yes);
    textView_yes.setOnClickListener(
        view -> {
          Toast.makeText(getBaseContext(), "Yes", Toast.LENGTH_SHORT).show();
          dialogMenu.dismiss();
        });
    TextView textView_no = footerView.findViewById(R.id.textView_no);
    textView_no.setOnClickListener(
        view -> {
          Toast.makeText(getBaseContext(), "No", Toast.LENGTH_SHORT).show();
          dialogMenu.dismiss();
        });
  }

  private void initializeCustomDialogMenu() {
    customDialogMenu = PowerMenuUtils.getCustomDialogPowerMenu(this, this);
    View footerView = customDialogMenu.getFooterView();
    TextView textView_yes = footerView.findViewById(R.id.textView_yes);
    textView_yes.setOnClickListener(
        view -> {
          Toast.makeText(getBaseContext(), "Read More", Toast.LENGTH_SHORT).show();
          customDialogMenu.dismiss();
        });
    TextView textView_no = footerView.findViewById(R.id.textView_no);
    textView_no.setOnClickListener(
        view -> {
          Toast.makeText(getBaseContext(), "Close", Toast.LENGTH_SHORT).show();
          customDialogMenu.dismiss();
        });
  }

  public void onHamburger(View view) {
    if (hamburgerMenu.isShowing()) {
      hamburgerMenu.dismiss();
      return;
    }
    hamburgerMenu.showAsDropDown(view);
  }

  public void onProfile(View view) {
    profileMenu.showAsDropDown(view, -370, 0);
  }

  public void onDialog(View view) {
    if (dialogMenu.isShowing()) {
      dialogMenu.dismiss();
      return;
    }
    View layout = binding.getRoot();
    dialogMenu.showAtCenter(layout);
  }

  public void onCustomDialog(View view) {
    if (customDialogMenu.isShowing()) {
      customDialogMenu.dismiss();
      return;
    }
    View layout = binding.getRoot();
    customDialogMenu.showAtCenter(layout);
  }

  public void onWrite(View view) {
    if (writeMenu.isShowing()) {
      writeMenu.dismiss();
      return;
    }
    View layout = binding.getRoot();
    writeMenu.showAtCenter(layout);
  }

  public void onAlert(View view) {
    if (alertMenu.isShowing()) {
      alertMenu.dismiss();
      return;
    }
    View layout = binding.getRoot();
    alertMenu.showAtCenter(layout);
  }

  public void onShare(View view) {
    if (iconMenu.isShowing()) {
      iconMenu.dismiss();
      return;
    }
    iconMenu.showAsDropDown(view, -370, 0);
  }

  @Override
  public void onBackPressed() {
    if (hamburgerMenu.isShowing()) {
      hamburgerMenu.dismiss();
    } else if (profileMenu.isShowing()) {
      profileMenu.dismiss();
    } else if (writeMenu.isShowing()) {
      writeMenu.dismiss();
    } else if (alertMenu.isShowing()) {
      alertMenu.dismiss();
    } else if (dialogMenu.isShowing()) {
      dialogMenu.dismiss();
    } else if (customDialogMenu.isShowing()) {
      customDialogMenu.dismiss();
    } else if (iconMenu.isShowing()) {
      iconMenu.dismiss();
    } else {
      super.onBackPressed();
    }
  }
}
