
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

import java.util.List;

public interface IMenuItem<T> {
    void addItem(T item);
    void addItem(int position, T item);
    void addItemList(List<T> itemList);

    void setSelected(int position);

    void removeItem(T item);
    void removeItem(int position);

    void clearItems();

    List<T> getItemList();
}
