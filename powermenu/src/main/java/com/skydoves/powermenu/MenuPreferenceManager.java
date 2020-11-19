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
import android.content.SharedPreferences;

@SuppressWarnings({"unused"})
class MenuPreferenceManager {

  private static final String position = "_POSITION";
  private static MenuPreferenceManager menuPreferenceManager;
  private final SharedPreferences sharedPreferences;

  private MenuPreferenceManager(Context context) {
    sharedPreferences =
        context.getSharedPreferences("com.skydoves.powermenu", Context.MODE_PRIVATE);
  }

  /**
   * initialize the {@link MenuPreferenceManager} instance.
   *
   * @param context context.
   */
  protected static void initialize(Context context) {
    menuPreferenceManager = new MenuPreferenceManager(context);
  }

  /**
   * gets an instance of the {@link MenuPreferenceManager}.
   *
   * <p>It must be called after invoking initialize() method.
   *
   * @return {@link MenuPreferenceManager}.
   */
  protected static MenuPreferenceManager getInstance() {
    return menuPreferenceManager;
  }

  /**
   * gets the saved menu position from preference.
   *
   * @param name preference name.
   * @param defaultPosition default preference menu position.
   * @return the saved menu position.
   */
  protected int getPosition(String name, int defaultPosition) {
    return sharedPreferences.getInt(name, defaultPosition);
  }

  /**
   * saves a menu position on preference.
   *
   * @param name preference name.
   * @param position preference menu position.
   */
  protected void setPosition(String name, int position) {
    sharedPreferences.edit().putInt(name, position).apply();
  }

  /**
   * clears the saved color from preference.
   *
   * @param name preference name.
   */
  protected void clearPosition(String name) {
    sharedPreferences.edit().remove(name).apply();
  }
}
