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
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.ColorInt;

/**
 * MenuListAdapter extends {@link MenuBaseAdapter}.
 *
 * <p>This is the {@link PowerMenu}'s default adapter.
 */
@SuppressWarnings({"WeakerAccess"})
public class MenuListAdapter extends MenuBaseAdapter<PowerMenuItem> implements IPowerMenuAdapter {

  @ColorInt private int textColor = -2;
  @ColorInt private int menuColor = -2;
  @ColorInt private int selectedTextColor = -2;
  @ColorInt private int selectedMenuColor = -2;
  private int textSize = 12;
  private int textGravity = Gravity.START;
  private Typeface textTypeface = null;

  private boolean selectedEffect = true;

  public MenuListAdapter(ListView listView) {
    super(listView);
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public View getView(final int index, View view, ViewGroup viewGroup) {
    final Context context = viewGroup.getContext();

    if (view == null) {
      LayoutInflater inflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.item_power_menu, viewGroup, false);
    }

    PowerMenuItem powerMenuItem = (PowerMenuItem) getItem(index);

    final View background = view.findViewById(R.id.item_power_menu_layout);
    final TextView title = view.findViewById(R.id.item_power_menu_title);
    final ImageView icon = view.findViewById(R.id.item_power_menu_icon);

    title.setText(powerMenuItem.title);
    title.setTextSize(textSize);
    title.setGravity(textGravity);

    if (textTypeface != null) {
      title.setTypeface(textTypeface);
    }

    if (powerMenuItem.icon != 0) {
      icon.setImageResource(powerMenuItem.icon);
      icon.setVisibility(View.VISIBLE);
    } else {
      icon.setVisibility(View.GONE);
    }

    if (powerMenuItem.isSelected) {

      setSelectedPosition(index);

      if (selectedMenuColor == -2)
        background.setBackgroundColor(context.getResources().getColor(R.color.menu_background));
      else background.setBackgroundColor(selectedMenuColor);

      if (selectedTextColor == -2)
        title.setTextColor(context.getResources().getColor(R.color.menu_text_selected));
      else title.setTextColor(selectedTextColor);
    } else {
      if (menuColor == -2) background.setBackgroundColor(Color.WHITE);
      else background.setBackgroundColor(menuColor);

      if (textColor == -2)
        title.setTextColor(context.getResources().getColor(R.color.menu_text_no_selected));
      else title.setTextColor(textColor);
    }
    return super.getView(index, view, viewGroup);
  }

  @Override
  public void setSelectedPosition(int position) {
    super.setSelectedPosition(position);

    if (selectedEffect) {
      for (int i = 0; i < getItemList().size(); i++) {
        PowerMenuItem item = (PowerMenuItem) getItem(i);

        item.setIsSelected(false);
        if (i == position) {
          item.setIsSelected(true);
        }
      }
      notifyDataSetChanged();
    }
  }

  @Override
  public void setTextColor(int color) {
    this.textColor = color;
  }

  @Override
  public void setMenuColor(int color) {
    this.menuColor = color;
  }

  @Override
  public void setSelectedTextColor(int color) {
    this.selectedTextColor = color;
  }

  @Override
  public void setSelectedMenuColor(int color) {
    this.selectedMenuColor = color;
  }

  @Override
  public void setSelectedEffect(boolean selectedEffect) {
    this.selectedEffect = selectedEffect;
  }

  @Override
  public void setTextSize(int size) {
    this.textSize = size;
  }

  @Override
  public void setTextGravity(int gravity) {
    this.textGravity = gravity;
  }

  @Override
  public void setTextTypeface(Typeface typeface) {
    this.textTypeface = typeface;
  }
}
