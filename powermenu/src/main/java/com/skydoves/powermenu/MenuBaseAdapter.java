
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

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuBaseAdapter<T> extends BaseAdapter implements IMenuItem<T> {

    private List<T> itemList;

    public MenuBaseAdapter() {
        super();
        itemList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int index) {
        return itemList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return view;
    }

    @Override
    public void addItem(T item) {
        this.itemList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void addItem(int position, T item) {
        this.itemList.add(position, item);
        notifyDataSetChanged();
    }

    @Override
    public void addItemList(List<T> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void setSelected(int position) {

    }

    @Override
    public void removeItem(T item) {
        this.itemList.remove(item);
    }

    @Override
    public void removeItem(int position) {
        this.itemList.remove(position);
    }

    @Override
    public void clearItems() {
        this.itemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public List<T> getItemList() {
        return itemList;
    }
}