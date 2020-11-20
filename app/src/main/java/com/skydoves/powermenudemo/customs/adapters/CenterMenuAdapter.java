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
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.skydoves.powermenu.MenuBaseAdapter;
import com.skydoves.powermenudemo.R;

public class CenterMenuAdapter extends MenuBaseAdapter<String> {

  public CenterMenuAdapter() {
    super();
  }

  @Override
  public View getView(int index, View view, ViewGroup viewGroup) {
    final Context context = viewGroup.getContext();

    if (view == null) {
      LayoutInflater inflater = LayoutInflater.from(context);
      assert inflater != null;
      view = inflater.inflate(R.layout.item_title_menu, viewGroup, false);
    }

    String item = (String) getItem(index);
    final TextView title = view.findViewById(R.id.item_title);
    title.setText(item);
    title.setTextColor(ContextCompat.getColor(context, R.color.md_grey_800));
    return super.getView(index, view, viewGroup);
  }

  @Override
  public void setSelectedPosition(int position) {
    notifyDataSetChanged();
  }
}
