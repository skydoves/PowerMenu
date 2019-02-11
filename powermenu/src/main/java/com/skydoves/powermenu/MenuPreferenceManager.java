package com.skydoves.powermenu;

import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings("unused")
public class MenuPreferenceManager {

  private static final String position = "_POSITION";
  private static MenuPreferenceManager menuPreferenceManager;
  private SharedPreferences sharedPreferences;

  private MenuPreferenceManager(Context context) {
    sharedPreferences =
        context.getSharedPreferences("com.skydoves.powermenu", Context.MODE_PRIVATE);
  }

  /**
   * gets an instance of the {@link MenuPreferenceManager}.
   *
   * @param context context.
   * @return {@link MenuPreferenceManager}.
   */
  protected static MenuPreferenceManager getInstance(Context context) {
    if (menuPreferenceManager == null)
      menuPreferenceManager = new MenuPreferenceManager(context);
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
