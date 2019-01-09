package com.skydoves.powermenudemo.customs.items;

import android.graphics.drawable.Drawable;

/**
 * Developed by skydoves on 2017-10-29.
 * Copyright (c) 2017 skydoves rights reserved.
 */

public class NameCardMenuItem {
    private Drawable icon;
    private String name;
    private String content;

    public NameCardMenuItem(Drawable icon, String name, String content) {
        this.icon = icon;
        this.name = name;
        this.content = content;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
