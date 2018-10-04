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

/**
 * Developed by skydoves on 2017-12-18.
 * Copyright (c) 2017 skydoves rights reserved.
 */

public class CustomDialogMenuAdapter extends MenuBaseAdapter<NameCardMenuItem> {

    public CustomDialogMenuAdapter() {
        super();
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
