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

package com.skydoves.powermenudemo.customs.items;

import android.graphics.drawable.Drawable;

@SuppressWarnings("unused")
public class NameCardMenuItem {
  private Drawable icon;
  private String name;
  private String content;

  public NameCardMenuItem(Drawable icon, String name, String content) {
    this.icon = icon;
    this.name = name;
    this.content = content;
  }

  public Drawable getIcon() {
    return this.icon;
  }

  public void setIcon(Drawable drawable) {
    this.icon = drawable;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
