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
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.Px;
import androidx.annotation.StyleRes;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.skydoves.powermenu.annotations.Dp;

/**
 * AbstractMenuBuilder is the abstract builder class of {@link PowerMenu.Builder} and {@link
 * CustomPowerMenu.Builder}.
 */
public abstract class AbstractMenuBuilder {

  protected Context context;
  protected LayoutInflater layoutInflater;
  protected boolean showBackground = true;
  protected LifecycleOwner lifecycleOwner = null;
  protected View.OnClickListener backgroundClickListener = null;
  protected OnDismissedListener onDismissedListener = null;
  protected MenuAnimation menuAnimation = MenuAnimation.DROP_DOWN;
  protected View headerView = null;
  protected View footerView = null;
  @StyleRes protected int animationStyle = -1;
  @Px protected float menuRadius = 5;
  @Px protected float menuShadow = 5;
  @Px protected int width = 0;
  @Px protected int height = 0;
  @Px protected int padding = 0;
  @Px protected int dividerHeight = 0;
  protected Drawable divider = null;
  @ColorInt protected int backgroundColor = Color.BLACK;
  @Dp protected int iconSize = 35;
  @Dp protected int iconPadding = 7;
  @ColorInt protected int iconColor = -2;

  @FloatRange(from = 0.0, to = 1.0)
  protected float backgroundAlpha = 0.6f;

  protected int backgroundSystemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE;
  protected boolean focusable = false;
  protected int selected = -1;
  protected boolean isClipping = true;
  protected boolean autoDismiss = false;
  protected boolean dismissIfShowAgain = true;
  protected String preferenceName = null;
  protected Lifecycle.Event initializeRule = null;
  protected int defaultPosition = 0;
  protected CircularEffect circularEffect = null;
}
