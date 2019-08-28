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

package com.skydoves.powermenudemo.kotlin

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.skydoves.powermenu.CircularEffect
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.createPowerMenu
import com.skydoves.powermenudemo.R

class MoreMenuFactory : PowerMenu.Factory() {

  override fun create(context: Context, lifecycle: LifecycleOwner): PowerMenu {
    return createPowerMenu(context) {
      addItem(PowerMenuItem("Novel", true))
      addItem(PowerMenuItem("Poetry", false))
      addItem(PowerMenuItem("Art", false))
      addItem(PowerMenuItem("Journals", false))
      addItem(PowerMenuItem("Travel", false))
      setAutoDismiss(true)
      setLifecycleOwner(lifecycle)
      setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
      setCircularEffect(CircularEffect.BODY)
      setMenuRadius(10f)
      setMenuShadow(10f)
      setTextColorResource(R.color.md_grey_800)
      setTextSize(12)
      setTextGravity(Gravity.CENTER)
      setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
      setSelectedTextColor(Color.WHITE)
      setMenuColor(Color.WHITE)
      setSelectedMenuColorResource(R.color.colorPrimary)
      setPreferenceName("HamburgerPowerMenu")
      setInitializeRule(Lifecycle.Event.ON_CREATE, 0)
    }
  }
}
