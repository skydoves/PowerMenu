/*
 * Copyright (C) 2017 skydoves (Jaewoong Eum)
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

import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;

/** PowerMenuItem is the item class for constructing the {@link PowerMenu}'s list. */
@SuppressWarnings({"unused"})
public class PowerMenuItem {

  protected CharSequence title;
  @DrawableRes protected int iconRes;
  protected Drawable icon;
  protected boolean isSelected;
  protected Object tag;

  public PowerMenuItem(CharSequence title) {
    this.title = title;
  }

  public PowerMenuItem(CharSequence title, Object tag) {
    this.title = title;
    this.tag = tag;
  }

  public PowerMenuItem(CharSequence title, @DrawableRes int iconRes) {
    this.title = title;
    this.iconRes = iconRes;
  }

  public PowerMenuItem(CharSequence title, @DrawableRes int iconRes, Object tag) {
    this.title = title;
    this.iconRes = iconRes;
    this.tag = tag;
  }

  public PowerMenuItem(CharSequence title, Drawable icon) {
    this.title = title;
    this.icon = icon;
  }

  public PowerMenuItem(CharSequence title, Drawable icon, Object tag) {
    this.title = title;
    this.icon = icon;
    this.tag = tag;
  }

  public PowerMenuItem(CharSequence title, boolean isSelected) {
    this.title = title;
    this.isSelected = isSelected;
  }

  public PowerMenuItem(CharSequence title, boolean isSelected, Object tag) {
    this.title = title;
    this.isSelected = isSelected;
    this.tag = tag;
  }

  public PowerMenuItem(CharSequence title, @DrawableRes int iconRes, boolean isSelected) {
    this.title = title;
    this.iconRes = iconRes;
    this.isSelected = isSelected;
  }

  public PowerMenuItem(
      CharSequence title, @DrawableRes int iconRes, boolean isSelected, Object tag) {
    this.title = title;
    this.iconRes = iconRes;
    this.isSelected = isSelected;
    this.tag = tag;
  }

  public PowerMenuItem(CharSequence title, Drawable icon, boolean isSelected) {
    this.title = title;
    this.icon = icon;
    this.isSelected = isSelected;
  }

  public PowerMenuItem(CharSequence title, Drawable icon, boolean isSelected, Object tag) {
    this.title = title;
    this.icon = icon;
    this.isSelected = isSelected;
    this.tag = tag;
  }

  /**
   * gets the title.
   *
   * @return the title.
   */
  public CharSequence getTitle() {
    return this.title;
  }

  /**
   * sets the title.
   *
   * @param title title.
   */
  public void setTitle(CharSequence title) {
    this.title = title;
  }

  /**
   * gets the selected or not.
   *
   * @return the selected or not.
   */
  public boolean isSelected() {
    return this.isSelected;
  }

  /**
   * sets the selected.
   *
   * @param isSelected selected or not.
   */
  public void setIsSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  /**
   * gets the tag.
   *
   * @return the tag.
   */
  public Object getTag() {
    return tag;
  }

  /**
   * sets a tag.
   *
   * @param tag Object.
   */
  public void setTag(Object tag) {
    this.tag = tag;
  }

  /**
   * gets the icon resource.
   *
   * @return the icon resource.
   */
  @DrawableRes
  public int getIconRes() {
    return iconRes;
  }

  /**
   * gets the icon drawable.
   *
   * @return the icon drawable.
   */
  public Drawable getIcon() {
    return icon;
  }

  /**
   * sets an icon.
   *
   * @param icon icon.
   */
  public void setIconRes(@DrawableRes int icon) {
    this.iconRes = icon;
  }

  /**
   * sets an icon drawable.
   *
   * @param iconRes drawable.
   */
  public void setIconRes(Drawable iconRes) {
    this.icon = icon;
  }
}
