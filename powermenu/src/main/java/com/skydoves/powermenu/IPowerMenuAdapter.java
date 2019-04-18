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

/**
 * IPowerMenuAdapter is an interface of the {@link MenuListAdapter} implementation for the item
 * selected effect.
 */
interface IPowerMenuAdapter {
  /**
   * sets the color of the default item text.
   *
   * @param color color value.
   */
  void setTextColor(int color);

  /**
   * sets the size of the item text.
   *
   * @param size size value.
   */
  void setTextSize(int size);

  /**
   * sets the {@link android.view.Gravity} of the item.
   *
   * @param gravity gravity value.
   */
  void setTextGravity(int gravity);

  /**
   * sets the color of the menu item color.
   *
   * @param color color value.
   */
  void setMenuColor(int color);

  /**
   * sets the color of the selected item text.
   *
   * @param color color value.
   */
  void setSelectedTextColor(int color);

  /**
   * sets the color of the selected menu item color.
   *
   * @param color color value.
   */
  void setSelectedMenuColor(int color);

  /**
   * sets the selected effects what changing colors of the selected menu item.
   *
   * @param selectedEffect enable or unable.
   */
  void setSelectedEffect(boolean selectedEffect);
}
