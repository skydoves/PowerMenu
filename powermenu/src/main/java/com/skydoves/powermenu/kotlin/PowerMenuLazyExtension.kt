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

@file:Suppress("unused")

package com.skydoves.powermenu.kotlin

import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import com.skydoves.powermenu.PowerMenu
import kotlin.reflect.KClass

/**
 * Returns a [Lazy] delegate to access the [ComponentActivity]'s PowerMenu property.
 * The PowerMenu property will be initialized lazily.
 *
 * @see [Lazy Initialization](https://github.com/skydoves/powermenu#lazy-initialization-in-kotlin)
 */
@Deprecated(
  message = "Use powerMenu<T>() instead",
  replaceWith = ReplaceWith(
    "powerMenu<T>()",
    imports = ["com.skydoves.powermenu.kotlin"]
  )
)
@MainThread
@JvmSynthetic
inline fun <reified T : PowerMenu.Factory> ComponentActivity.powerMenu(
  factory: KClass<T>
): Lazy<PowerMenu> {
  return ActivityPowerMenuLazy(this, this, factory)
}

/**
 * Returns a [Lazy] delegate to access the [ComponentActivity]'s PowerMenu property.
 * The PowerMenu property will be initialized lazily.
 *
 * @see [Lazy Initialization](https://github.com/skydoves/powermenu#lazy-initialization-in-kotlin)
 */
@MainThread
@JvmSynthetic
inline fun <reified T : PowerMenu.Factory> ComponentActivity.powerMenu(): Lazy<PowerMenu> {
  return ActivityPowerMenuLazy(this, this, T::class)
}

/**
 * Returns a [Lazy] delegate to access the [Fragment]'s PowerMenu property.
 * The PowerMenu property will be initialized lazily.
 *
 * @see [Lazy Initialization](https://github.com/skydoves/powermenu#lazy-initialization-in-kotlin)
 */
@Deprecated(
  message = "Use powerMenu<T>() instead",
  replaceWith = ReplaceWith(
    "powerMenu<T>()",
    imports = ["com.skydoves.powermenu.kotlin"]
  )
)
@MainThread
@JvmSynthetic
inline fun <reified T : PowerMenu.Factory> Fragment.powerMenu(
  factory: KClass<T>
): Lazy<PowerMenu?> {
  return FragmentPowerMenuLazy(this, factory)
}

/**
 * Returns a [Lazy] delegate to access the [Fragment]'s PowerMenu property.
 * The PowerMenu property will be initialized lazily.
 *
 * @see [Lazy Initialization](https://github.com/skydoves/powermenu#lazy-initialization-in-kotlin)
 */
@MainThread
@JvmSynthetic
inline fun <reified T : PowerMenu.Factory> Fragment.powerMenu(): Lazy<PowerMenu?> {
  return FragmentPowerMenuLazy(this, T::class)
}

/**
 * Returns a [Lazy] delegate to access the custom [View]'s PowerMenu property.
 * The PowerMenu property will be initialized lazily.
 *
 * @see [Lazy Initialization](https://github.com/skydoves/powermenu#lazy-initialization-in-kotlin)
 */
@MainThread
@JvmSynthetic
inline fun <reified T : PowerMenu.Factory> View.powerMenu(): Lazy<PowerMenu> {
  return ViewPowerMenuLazy(context, T::class)
}
