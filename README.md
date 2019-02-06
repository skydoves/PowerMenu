# PowerMenu
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![travis](https://travis-ci.org/skydoves/PowerMenu.svg?branch=master)](https://travis-ci.org/skydoves/PowerMenu) 
[![Android Weekly](https://img.shields.io/badge/Android%20weekly-326-orange.svg)](https://androidweekly.net/issues/issue-326)
[![Javadoc](https://img.shields.io/badge/Javadoc-PowerMenu-yellow.svg)](https://skydoves.github.io/libraries/powermenu/javadoc/index.html) <br>
The most powerful and easiest way to implement modern popup. <br>
PowerMenu can be fully customized and used for popup dialogs. <br>

![gif1](https://user-images.githubusercontent.com/24237865/32500436-c52b02ec-c418-11e7-8027-9aceb5cbe368.gif)
![screenshot0](https://user-images.githubusercontent.com/24237865/32500435-c4f81594-c418-11e7-98e5-d1ddbbb6c2ad.jpg)

## Download
[![Download](https://api.bintray.com/packages/devmagician/maven/powermenu/images/download.svg)](https://bintray.com/devmagician/maven/powermenu/_latestVersion)
[![Jitpack](https://jitpack.io/v/skydoves/PowerMenu.svg)](https://jitpack.io/#skydoves/PowerMenu)
### Gradle
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "com.github.skydoves:powermenu:2.0.7"
}
```

## Usage

### Basic example
This is a basic example on a screenshot. Here is how to create `PowerMenu` using `PowerMenu.Builder`.
```java
PowerMenu powerMenu = new PowerMenu.Builder(context)
          .addItemList(list) // list has "Novel", "Poerty", "Art"
          .addItem(new PowerMenuItem("Journals", false)) // add an item.
          .addItem(new PowerMenuItem("Travel", false)) // aad an item list.
          .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
          .setMenuRadius(10f) // sets the corner radius.
          .setMenuShadow(10f) // sets the shadow.
          .setTextColor(context.getResources().getColor(R.color.md_grey_800))
          .setSelectedTextColor(Color.WHITE)
          .setMenuColor(Color.WHITE)
          .setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary))
          .setOnMenuItemClickListener(onMenuItemClickListener)
          .build();
```

We can add an item or an item list using `PowerMenuItem` class. This is how to initialize `PowerMenuItem`.
```java
new PowerMenuItem("Travel");
new PowerMenuItem("Poetery", false); // item name, isSelected (default is false).
new PowerMenuItem("Art", R.drawable.icon_art) // item name, item menu icon.
new PowerMenuItem("Travel", R.drawable.icon_travel, true) // item name, item menu icon, isSelected .
```

The first argument is an item **title**, and the other is **selected status**. <br>
If **isSelected** is true, the item's text and the background color will be changed by settings like below.<br>

```java
.setSelectedTextColor(Color.WHITE) // sets the color of the selected item text. 
.setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary)) // sets the color of the selected menu item color.
```

`OnMenuItemClickListener` is for listening to the item click of the popup menu.
```java
private OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
    @Override
    public void onItemClick(int position, PowerMenuItem item) {
        Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        powerMenu.setSelectedPosition(position); // change selected item
        powerMenu.dismiss();
    }
};
```

After implementing the listener, we should set using `setOnMenuItemClickListener` method.
```java
.setOnMenuItemClickListener(onMenuItemClickListener)
```

The last, show the popup! [Various show & dismiss methods](https://github.com/skydoves/PowerMenu#show--dismiss).
```java
powerMenu.showAsDropDown(view); // view is an anchor
```

## Customizing Popup
We can customize item styles using `CustomPowerMenu` and your customized adapter. <br>
Here is how to customize the popup item that has an icon. <br>

![custom0](https://user-images.githubusercontent.com/24237865/45586080-5a07e800-b92b-11e8-8c7e-5aa0e524598c.png)
![gif0](https://user-images.githubusercontent.com/24237865/45586081-5a07e800-b92b-11e8-9cba-303eb013b7c9.gif) <br>

Firstly, we should create our item model class.
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

And we should create our customized XML layout and an adapter. <br>
Custom Adapter should extend `MenuBaseAdapter<YOUR_ITEM_MODEL_CLASS>`.

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
        return super.getView(index, view, viewGroup);
    }
}
```

The last, create the `CustomPowerMenu` with the `onMenuItemClickListener`.
```java
CustomPowerMenu customPowerMenu = new CustomPowerMenu.Builder<>(context, new IconMenuAdapter())
       .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_wechat), "WeChat"))
       .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_facebook), "Facebook"))
       .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_twitter), "Twitter"))
       .addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_line), "Line"))
       .setOnMenuItemClickListener(onIconMenuItemClickListener)
       .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
       .setMenuRadius(10f)
       .setMenuShadow(10f)
       .build();
```
```java
private OnMenuItemClickListener<IconPowerMenuItem> onIconMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
    @Override
    public void onItemClick(int position, IconPowerMenuItem item) {
        Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        iconMenu.dismiss();
    }
};
```

## Dialogs
We can create looks like dialogs using `PowerMenu`.<br><br>
![screenshot_2017-12-18-23-39-00](https://user-images.githubusercontent.com/24237865/34111113-1de9bfce-e44c-11e7-9b60-44b8d440b910.png)
![screenshot_2017-12-18-23-39-05](https://user-images.githubusercontent.com/24237865/34111114-1e19ddf8-e44c-11e7-9747-17713d2932bd.png)
<br>

Here is an example of the normal dialog. Dialogs are composed of a header, footer, and body. <br>
```java
PowerMenu powerMenu = new PowerMenu.Builder(context)
           .setHeaderView(R.layout.layout_dialog_header) // header used for title
           .setFooterView(R.layout.layout_dialog_footer) // footer used for yes and no buttons
           .addItem(new PowerMenuItem("This is DialogPowerMenu", false)) // this is body
           .setLifecycleOwner(lifecycleOwner)
           .setAnimation(MenuAnimation.SHOW_UP_CENTER)
           .setMenuRadius(10f)
           .setMenuShadow(10f)
           .setWith(600)
           .setSelectedEffect(false)
           .build();
```

And we can create a customized dialog like below.
```java
CustomPowerMenu customPowerMenu = new CustomPowerMenu.Builder<>(context, new CustomDialogMenuAdapter())
         setHeaderView(R.layout.layout_custom_dialog_header) // header used for title
        .setFooterView(R.layout.layout_custom_dialog_footer) // footer used for Read More and Close buttons
         // this is body
        .addItem(new NameCardMenuItem(context.getResources().getDrawable(R.drawable.face3), "Sophie", context.getString(R.string.board3)))
        .setLifecycleOwner(lifecycleOwner)
        .setAnimation(MenuAnimation.SHOW_UP_CENTER)
        .setWith(800)
        .setMenuRadius(10f)
        .setMenuShadow(10f)
        .build();
```

## Anchor
We can show the popup menu as drop down to the anchor. <br><br>

<img src="https://user-images.githubusercontent.com/24237865/38161125-da1ac0aa-3503-11e8-9a91-7dc4f8f4d9c3.gif" align="left" width="30%">

```java
.showAsAnchorLeftTop(view) // showing the popup menu as left-top aligns to the anchor.
.showAsAnchorLeftBottom(view) // showing the popup menu as left-bottom aligns to the anchor.
.showAsAnchorRightTop(view) // using with .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) looks better
.showAsAnchorRightBottom(view) // using with .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) looks better
.showAsAnchorCenter(view) // using with .setAnimation(MenuAnimation.SHOW_UP_CENTER) looks better
```

or we can control the position of the popup menu using the below methods.
```java
.getContentViewWidth() // return popup's measured width
.getContentViewHeight() // return popup's measured height
```

like this :
```java
// showing the popup menu at the center of an anchor. This is the same using .showAsAnchorCenter.
hamburgerMenu.showAsDropDown(view, 
        view.getMeasuredWidth()/2 - hamburgerMenu.getContentViewWidth(), 
       -view.getMeasuredHeight()/2 - hamburgerMenu.getContentViewHeight());
```
<br>

## Background
These are options for the background.
```java
.setShowBackground(false) // do not showing background.
.setTouchInterceptor(onTouchListener) // sets the touch listener for the outside of popup.
.setFocusable(true) // makes focusing only on the menu popup.
```

## Functions
### PowerMenu methods
```java
.addItemList(list) // add a PowerMenuItem list.
.addItem(new PowerMenuItem("Journals", false)) // add a PowerMenuItem.
.addItem(3, new PowerMenuItem("Travel", false)) // add a PowerMenuItem at position 3.
.setLifecycleOwner(lifecycleOwner) // set LifecycleOwner for preventing memory leak.
.setWith(300) // sets the popup width size.
.setHeight(400) // sets the popup height size.
.setMenuRadius(10f) // sets the popup corner radius.
.setMenuShadow(10f) // sets the popup shadow.
.setDivider(new ColorDrawable(context.getResources().getColor(R.color.md_blue_grey_300))) // sets a divider.
.setDividerHeight(1) // sets the divider height.
.setAnimation(MenuAnimation.FADE) // sets animations of the popup. It will start up when the popup is showing.
.setTextColor(context.getResources().getColor(R.color.md_grey_800)) // sets the color of the default item text.
.setSelectedTextColor(Color.WHITE) // sets the color of the selected item text.
.setMenuColor(Color.WHITE) // sets the color of the menu item color.
.setSelectedMenuColor(context.getResources().getColor(R.color.colorPrimary)) // sets the color of the selected menu item color.
.setSelectedEffect(false) // sets the selected effects what changing colors of the selected menu item.
.setOnMenuItemClickListener(onMenuItemClickListener) // sets an item click listener.
.setOnDismissListener(OnDismissedListener onDismissListener) // sets a menu dismiss listener.
.setHeaderView(View view) //  sets the header view of the popup menu list.
.setHeaderView(int layout) // sets the header view of the popup menu using layout.
.setFooterView(View view) // sets the footer view of the popup menu list.
.setFooterView(int layout) // sets the footer view of the popup menu using layout.
.setSelection(int position) // sets the selected position of the popup menu. It can be used for scrolling as the position.
.getSelectedPosition() // gets the selected item position. if not selected before, returns -1 .
.getHeaderView() // gets the header view of the popup menu list.
.getFooterView() // gets the footer view of the popup menu list.
.getMenuListView() // gets the ListView of the popup menu.
```

### Background methods
```java
.setBackgroundAlpha(0.7f) // sets the alpha of the background.
.setBackgroundColor(Color.GRAY) // sets the color of the background.
.setShowBackground(false) // sets the background is showing or not.
.setOnBackgroundClickListener(onClickListener) // sets the background click listener of the background.
```

### Show & Dismiss
```java
.showAsDropDown(View anchor); // showing the popup menu as drop down to the anchor.
.showAsDropDown(View anchor, -370, 0); // showing the popup menu as drop down to the anchor with x-off and y-off.
.showAtCenter(View layout); // showing the popup menu as center aligns to the anchor.
.showAtCenter(View layout, 0, 0); // showing the popup menu as center aligns to the anchor with x-off and y-off.
.showAtLocation(View anchor, int xOff, int yOff) // showing the popup menu as center aligns to the anchor with x-off and y-off.
.showAtLocation(View anchor, int gravity, int xOff, int yOff) // showing the popup menu to the specific location to the anchor with Gravity.
.isShowing(); // gets the popup is showing or not.
.dismiss(); // dismiss the popup.
```

## Avoid Memory leak
Dialog, PopupWindow and etc.. have memory leak issue if not dismissed before activity or fragment are destroyed.<br>
But Lifecycles are now integrated with the Support Library since Architecture Components 1.0 Stable released.<br>
So we can solve the memory leak issue so easily.<br>

Just use `setLifecycleOwner` method. Then `dismiss` method will be called automatically before activity or fragment would be destroyed.
```java
.setLifecycleOwner(lifecycleOwner)
```

## Supports
If you feel like support me a coffee for my efforts, I would greatly appreciate it. <br><br>
<a href="https://www.buymeacoffee.com/skydoves" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/purple_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

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
