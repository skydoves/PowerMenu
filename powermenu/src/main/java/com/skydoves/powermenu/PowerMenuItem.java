
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

public class PowerMenuItem {
    private String title;
    private int icon;
    private boolean isSelected;
    private Object tag;

    public PowerMenuItem(String title) {
        this.title = title;
    }

    public PowerMenuItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public PowerMenuItem(String title, boolean isSelected) {
        this.title = title;
        this.isSelected = isSelected;
    }

    public PowerMenuItem(String title, Object tag) {
        this.title = title;
        this.tag = tag;
    }

    public PowerMenuItem(String title, int icon, boolean isSelected) {
        this.title = title;
        this.icon = icon;
        this.isSelected = isSelected;
    }

    public PowerMenuItem(String title, boolean isSelected, Object tag) {
        this.title = title;
        this.isSelected = isSelected;
        this.tag = tag;
    }

    public PowerMenuItem(String title, int icon, boolean isSelected, Object tag) {
        this.title = title;
        this.icon = icon;
        this.isSelected = isSelected;
        this.tag = tag;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
