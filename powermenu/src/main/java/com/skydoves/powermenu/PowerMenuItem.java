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

import androidx.annotation.DrawableRes;

/** PowerMenuItem is the item class for constructing the {@link PowerMenu}'s list. */
@SuppressWarnings({"WeakerAccess", "unused"})
public class PowerMenuItem {

  protected String title;
  @DrawableRes protected int icon;
  protected boolean isSelected;
  protected Object tag;

  public PowerMenuItem(String title) {
    this.title = title;
  }

  public PowerMenuItem(String title, Object tag) {
    this.title = title;
    this.tag = tag;
  }

  public PowerMenuItem(String title, @DrawableRes int icon) {
    this.title = title;
    this.icon = icon;
  }

  public PowerMenuItem(String title, @DrawableRes int icon, Object tag) {
    this.title = title;
    this.icon = icon;
    this.tag = tag;
  }

  public PowerMenuItem(String title, boolean isSelected) {
    this.title = title;
    this.isSelected = isSelected;
  }

  public PowerMenuItem(String title, boolean isSelected, Object tag) {
    this.title = title;
    this.isSelected = isSelected;
    this.tag = tag;
  }

  public PowerMenuItem(String title, @DrawableRes int icon, boolean isSelected) {
    this.title = title;
    this.icon = icon;
    this.isSelected = isSelected;
  }

  public PowerMenuItem(String title, @DrawableRes int icon, boolean isSelected, Object tag) {
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
  public String getTitle() {
    return this.title;
  }

  /**
   * sets the title.
   *
   * @param title title.
   */
  public void setTitle(String title) {
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
   * gets the icon.
   *
   * @return the icon.
   */
  public int getIcon() {
    return icon;
  }

  /**
   * sets a icon.
   *
   * @param icon icon.
   */
  public void setIcon(@DrawableRes int icon) {
    this.icon = icon;
  }
}
