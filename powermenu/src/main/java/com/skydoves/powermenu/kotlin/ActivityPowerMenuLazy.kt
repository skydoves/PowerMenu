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

package com.skydoves.powermenu.kotlin

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.powermenu.PowerMenu
import kotlin.reflect.KClass

/**
 * An implementation of [Lazy] for creating an instance of the [PowerMenu] lazily in Activities.
 * Tied to the given [lifecycleOwner], [clazz].
 *
 * @param context A context for creating resources of the [PowerMenu] lazily.
 * @param lifecycleOwner A [LifecycleOwner] for dismissing automatically when the [LifecycleOwner] is being destroyed.
 * This will prevents memory leak: [Avoid Memory Leak](https://github.com/skydoves/powermenu#avoid-memory-leak).
 * @param clazz A [PowerMenu.Factory] kotlin class for creating a new instance of the PowerMenu.
 */
@PublishedApi
internal class ActivityPowerMenuLazy<out T : PowerMenu.Factory>(
  private val context: Context,
  private val lifecycleOwner: LifecycleOwner,
  private val clazz: KClass<T>
) : Lazy<PowerMenu> {

  private var cached: PowerMenu? = null

  override val value: PowerMenu
    get() {
      var instance = cached
      if (instance === null) {
        val factory = clazz::java.get().newInstance()
        instance = factory.create(context, lifecycleOwner)
        cached = instance
      }

      return instance
    }

  override fun isInitialized(): Boolean = cached !== null

  override fun toString(): String = if (isInitialized()) value.toString() else "Lazy value not initialized yet."
}
