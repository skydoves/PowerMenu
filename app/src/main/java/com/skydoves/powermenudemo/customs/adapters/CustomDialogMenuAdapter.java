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

package com.skydoves.powermenudemo.customs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.skydoves.powermenu.MenuBaseAdapter;
import com.skydoves.powermenudemo.R;
import com.skydoves.powermenudemo.customs.items.NameCardMenuItem;

@SuppressWarnings("ConstantConditions")
public class CustomDialogMenuAdapter extends MenuBaseAdapter<NameCardMenuItem> {

  public CustomDialogMenuAdapter() {
    super();
  }

  @Override
  public View getView(int index, View view, ViewGroup viewGroup) {
    final Context context = viewGroup.getContext();

    if (view == null) {
      LayoutInflater inflater = LayoutInflater.from(context);
      view = inflater.inflate(R.layout.item_name_card, viewGroup, false);
    }

    NameCardMenuItem item = (NameCardMenuItem) getItem(index);
    final ImageView icon = view.findViewById(R.id.item_name_card_profile);
    icon.setImageDrawable(item.getIcon());
    final TextView name = view.findViewById(R.id.item_name_card_name);
    name.setText(item.getName());
    final TextView content = view.findViewById(R.id.item_name_card_board);
    content.setText(item.getContent());
    return super.getView(index, view, viewGroup);
  }
}
