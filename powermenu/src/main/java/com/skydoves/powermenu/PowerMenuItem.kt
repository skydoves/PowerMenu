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

package com.skydoves.powermenu

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

/** PowerMenuItem is the item class for constructing the [PowerMenu]'s list. */
public data class PowerMenuItem @JvmOverloads constructor(
  @JvmField public var title: CharSequence? = null,
  @JvmField public var isSelected: Boolean = false,
  @JvmField @DrawableRes
  public var iconRes: Int = 0,
  @JvmField public var icon: Drawable? = null,
  @JvmField public val iconContentDescription: CharSequence? = null,
  @JvmField public var tag: Any? = null,
)
