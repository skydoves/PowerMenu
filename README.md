# PowerMenu
[![license](https://img.shields.io/badge/license-apache%202.0-green.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![travis](https://travis-ci.org/skydoves/PowerMenu.svg?branch=master)](https://travis-ci.org/skydoves/PowerMenu) <br>
A library that let you implement popup so easily. <br>

![gif1](https://user-images.githubusercontent.com/24237865/32500436-c52b02ec-c418-11e7-8027-9aceb5cbe368.gif)
![screenshot0](https://user-images.githubusercontent.com/24237865/32500435-c4f81594-c418-11e7-98e5-d1ddbbb6c2ad.jpg)

## Download
#### Gradle
```java
dependencies {
    compile 'com.github.skydoves:powermenu:1.0.5'
}
```

## Usage

### Basic example
This is a basic example on a screenshot. <br>
You can build PowerMenu using Builder.
```java
PowerMenu powerMenu = new PowerMenu.Builder(context)
                .addItemList(list) // list has "Novel", "Poerty", "Art"
                .addItem(new PowerMenuItem("Journals", false))
                .addItem(new PowerMenuItem("Travel", false))
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(context.getResources().getColor(R.color.md_grey_800))
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
```

You can add items or item List using PowerMenuItem class. <br>
This is how to initialize PowerMenuItem.
```java
PowerMenuItem powerMenuItem = new PowerMenuItem("Travel", true);
```

At first, argument is item Title, and the other is setting selected status. <br>
If true, the item's text or background colour is changed by your settings like below<br>

```java
.setSelectedTextColor(Color.WHITE)
.setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary))
```

You can listen to item click.
```java
    private  OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            powerMenu.setSelected(position); // change selected item
            powerMenu.dismiss();
        }
    };
```

and the last, show popup
```java
powerMenu.showAsDropDown(view); // view is an anchor
```
or
```java
powerMenu.showAsDropDown(view, (int)xOffset, (int)yOffset);
```

### Customizing Popup
You can customizing item styles using CustomPowerMenu and your customized adapter. <br>
Below is how to customizing popup item that has an icon.  <br><br>
At first, you should create your item model.
```java
public class IconPowerMenuItem {
    private Drawable icon;
    private String title;

    public IconPowerMenuItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }
 // --- skipped setter and getter methods
}

```

And next, you should create your own customized XML layout and adapter. <br>
Custom Adapter should extend MenuBaseAdapter<YOUR_ITEM_MODEL>.

```java
public class IconMenuAdapter extends MenuBaseAdapter<IconPowerMenuItem> {

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_icon_menu, viewGroup, false);
        }

        IconPowerMenuItem item = (IconPowerMenuItem) getItem(index);
        final ImageView icon = view.findViewById(R.id.item_icon);
        icon.setImageDrawable(item.getIcon());
        final TextView title = view.findViewById(R.id.item_title);
        title.setText(item.getTitle());
        return view;
    }
}

```

and the last, build CustomPowerMenu.
```
CustomPowerMenu customPowerMenu = new CustomPowerMenu.Builder<>(context, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_wechat), "WeChat"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_facebook), "Facebook"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_twitter), "Twitter"))
                .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_line), "Line"))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .build();
```

You can add a onMenuItemClickListener like below.
```java
private OnMenuItemClickListener<IconPowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            iconMenu.dismiss();
        }
    };
```

## Functions
### Popup & Item Attrubutes
```java
.addItemList(list)
.addItem(new PowerMenuItem("Journals", false)) // add an PowerMenuItem
.addItem(3, new PowerMenuItem("Travel", false)) // add an PowerMenuItem at position 3
.setLifecycleOwner(lifecycleOwner) // set powermenu's LifecycleOwner what activity or fragment. This make avoid memory leak.
.setWith(300) // set popup width size
.setHeight(400) // set popup height size
.setMenuRadius(10f) // set popup corner radius
.setMenuShadow(10f) // set popup shadow
.setDivider(new ColorDrawable(context.getResources().getColor(R.color.md_blue_grey_300))) // set a divider
.setDividerHeight(1) // set divider's height
.setAnimation(MenuAnimation.FADE) // set Animation
.setTextColor(context.getResources().getColor(R.color.md_grey_800)) // set normoal item text color
.setSelectedTextColor(Color.WHITE) // set selected item text color
.setMenuColor(Color.WHITE) // set normoal item background color
.setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary)) // set selected item background color
.setSelectedEffect(false) // if false, no apply selected colors(text, background)
.setOnMenuItemClickListener(onMenuItemClickListener) // add a item click listener
.addHeaderView(int layout) // Add a fixed view to appear at the top of the list
.addHeaderView(View v, Object data, boolean isSelectable) // Add a fixed view to appear at the top of the list
.addFooterView(View v) // Add a fixed view to appear at the bottom of the list
.addFooterView(View v, Object data, boolean isSelectable) // Add a fixed view to appear at the bottom of the list
.setSelection(int position) // scroll to position
.getMenuListView() // return menu ListView on Powermenu
```

### Background Attrubutes
```java
.setBackgroundAlpha(0.7f) // set background's alpha
.setBackgroundColor(Color.GRAY) // set background's color
.setShowBackground(false) // set showing background
.setOnBackgroundClickListener(onClickListener) // set a background click listener. default is dismiss popup.
```

### Show & Dismiss
```java
.showAsDropDown(view); // show popup with drop-down at anchor view
.showAsDropDown(view, -370, 0); // showAsDropDown with moves (xoff, yoff)
.showAtCenter(layout); // show popup at anchor view's center
.showAtCenter(layout, 0, 0); // showAtCenter with moves (xoff, yoff)
.isShowing(); return true or false
.dismiss(); // dismiss popup
```

## Avoid Memory leak
Dialog, PopupWindow and etc.. have memory leak issue if not dismissed before activity or fragment are destroyed.<br>
But Lifecycles are now also integrated with the Support Library since Architecture Components 1.0 Stable released.<br>
So you can solve memory leak issue so easily.<br>

First, implement LifecycleOwner on your activity or fragment.
```java
public class MainActivity extends AppCompatActivity implements LifecycleOwner
```

The last, just use setLifecycleOwner method before show.
```java
.setLifecycleOwner(lifecycleOwner)
```


# License
```xml
Copyright 2017 skydoves

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
