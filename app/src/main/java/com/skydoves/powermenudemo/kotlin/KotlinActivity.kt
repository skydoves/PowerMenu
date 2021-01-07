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

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.powermenu.kotlin.powerMenu
import com.skydoves.powermenudemo.databinding.ActivityTestBinding

class KotlinActivity : AppCompatActivity() {

  private val moreMenu by powerMenu<MoreMenuFactory>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityTestBinding.inflate(layoutInflater)
    setContentView(binding.root)

    moreMenu.showAsDropDown(binding.button)
    binding.button.setOnClickListener {
      moreMenu.showAsDropDown(it)
    }

    moreMenu.setOnMenuItemClickListener { position, item ->
      moreMenu.selectedPosition = position
      Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
    }
  }
}
