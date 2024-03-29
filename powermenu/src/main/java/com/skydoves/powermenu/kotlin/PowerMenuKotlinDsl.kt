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

@file:Suppress("unused")

package com.skydoves.powermenu.kotlin

import android.content.Context
import androidx.annotation.MainThread
import com.skydoves.powermenu.PowerMenu

@DslMarker
internal annotation class PowerMenuDsl

/** creates an instance of [PowerMenu] by [PowerMenu.Builder] using kotlin dsl. */
@MainThread
@PowerMenuDsl
@JvmSynthetic
public inline fun createPowerMenu(
  context: Context,
  crossinline block: PowerMenu.Builder.() -> Unit,
): PowerMenu =
  PowerMenu.Builder(context).apply(block).build()
