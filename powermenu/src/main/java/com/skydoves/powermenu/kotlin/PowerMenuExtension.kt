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

@file:Suppress("SpellCheckingInspection", "unused")

package com.skydoves.powermenu.kotlin

import android.view.View
import androidx.annotation.MainThread
import com.skydoves.powermenu.AbstractPowerMenu

/** showing the popup menu as drop down to the anchor. */
fun View.showAsDropDown(powerMenu: AbstractPowerMenu<*, *>) {
  powermenu { powerMenu.showAsDropDown(this) }
}

/** showing the popup menu as drop down to the anchor with x-off and y-off. */
fun View.showAsDropDown(powerMenu: AbstractPowerMenu<*, *>, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAsDropDown(this, xOff, yOff) }
}

/** showing the popup menu as left-top aligns to the anchor. */
fun View.showAsAnchorLeftTop(powerMenu: AbstractPowerMenu<*, *>) {
  powermenu { powerMenu.showAsAnchorLeftTop(this) }
}

/** showing the popup menu as left-top aligns to the anchor with x-off and y-off. */
fun View.showAsAnchorLeftTop(powerMenu: AbstractPowerMenu<*, *>, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAsAnchorLeftTop(this, xOff, yOff) }
}

/** showing the popup menu as left-bottom aligns to the anchor. */
fun View.showAsAnchorLeftBottom(powerMenu: AbstractPowerMenu<*, *>) {
  powermenu { powerMenu.showAsAnchorLeftBottom(this) }
}

/** showing the popup menu as left-bottom aligns to the anchor. */
fun View.showAsAnchorLeftBottom(powerMenu: AbstractPowerMenu<*, *>, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAsAnchorLeftBottom(this, xOff, yOff) }
}

/** showing the popup menu as right-top aligns to the anchor. */
fun View.showAsAnchorRightTop(powerMenu: AbstractPowerMenu<*, *>) {
  powermenu { powerMenu.showAsAnchorRightTop(this) }
}

/** showing the popup menu as right-top aligns to the anchor. */
fun View.showAsAnchorRightTop(powerMenu: AbstractPowerMenu<*, *>, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAsAnchorRightTop(this, xOff, yOff) }
}

/** showing the popup menu as right-bottom aligns to the anchor. */
fun View.showAsAnchorRightBottom(powerMenu: AbstractPowerMenu<*, *>) {
  powermenu { powerMenu.showAsAnchorRightBottom(this) }
}

/** showing the popup menu as right-bottom aligns to the anchor. */
fun View.showAsAnchorRightBottom(powerMenu: AbstractPowerMenu<*, *>, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAsAnchorRightBottom(this, xOff, yOff) }
}

/** showing the popup menu as center align to the anchor. */
fun View.showAsAnchorCenter(powerMenu: AbstractPowerMenu<*, *>) {
  powermenu { powerMenu.showAsAnchorRightBottom(this) }
}

/** showing the popup menu as center align to the anchor. */
fun View.showAsAnchorCenter(powerMenu: AbstractPowerMenu<*, *>, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAsAnchorCenter(this, xOff, yOff) }
}

/** showing the popup menu as center aligns to the anchor. */
fun View.showAtCenter(powerMenu: AbstractPowerMenu<*, *>) {
  powermenu { powerMenu.showAtCenter(this) }
}

/** showing the popup menu as center aligns to the anchor with x-off and y-off. */
fun View.showAtCenter(powerMenu: AbstractPowerMenu<*, *>, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAtCenter(this, xOff, yOff) }
}

/** showing the popup menu to the specific location to the anchor. */
fun View.showAtLocation(powerMenu: AbstractPowerMenu<*, *>, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAtLocation(this, xOff, yOff) }
}

/** showing the popup menu to the specific location to the anchor with {@link Gravity}. */
fun View.showAtLocation(powerMenu: AbstractPowerMenu<*, *>, gravity: Int, xOff: Int, yOff: Int) {
  powermenu { powerMenu.showAtLocation(this, gravity, xOff, yOff) }
}

@MainThread
@JvmSynthetic
internal inline fun View.powermenu(crossinline block: () -> Unit) {
  post { block() }
}
