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

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.annotation.StyleRes;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import com.skydoves.powermenu.databinding.LayoutPowerBackgroundBinding;
import com.skydoves.powermenu.databinding.LayoutPowerMenuBinding;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * AbstractPowerMenu is the abstract class of {@link PowerMenu} and {@link CustomPowerMenu}.
 *
 * <p>It implements basically almost things of the PowerMenu.
 *
 * <p>
 */
@SuppressWarnings({"unchecked", "unused"})
public abstract class AbstractPowerMenu<E, T extends MenuBaseAdapter<E>>
    implements IMenuItem<E>, LifecycleObserver {

  protected View backgroundView;
  protected View menuView;
  protected CardView menuCard;

  protected PopupWindow backgroundWindow;
  protected PopupWindow menuWindow;

  protected Lifecycle.Event initializeRule;
  protected LifecycleOwner lifecycleOwner;

  protected ListView menuListView;
  protected OnMenuItemClickListener<E> menuItemClickListener;
  protected OnDismissedListener onDismissedListener;
  protected LayoutInflater layoutInflater;

  protected View headerView;
  protected View footerView;

  protected T adapter;

  protected boolean showBackground = true;
  protected boolean allowTouchBackground = false;
  protected boolean fixedHeight = false;

  protected boolean isShowing = false;

  @Px protected int contentViewPadding;
  private int defaultPosition;
  private CircularEffect circularEffect;
  private boolean autoDismiss;
  private boolean dismissIfShowAgain;
  private AdapterView.OnItemClickListener itemClickListener =
      new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
          if (autoDismiss) {
            dismiss();
          }
          menuItemClickListener.onItemClick(index, (E) menuListView.getItemAtPosition(index));
        }
      };
  private OnMenuItemClickListener<E> onMenuItemClickListener =
      new OnMenuItemClickListener<E>() {
        @Override
        public void onItemClick(int position, E item) {
          // empty body
        }
      };
  private View.OnClickListener background_clickListener =
      new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (!allowTouchBackground) dismiss();
        }
      };
  private View.OnTouchListener onTouchListener =
      new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          if (event.getAction() == MotionEvent.ACTION_OUTSIDE && !showBackground) {
            dismiss();
            return true;
          }
          return false;
        }
      };
  private View.OnClickListener headerFooterClickListener =
      new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          // empty body
        }
      };

  protected AbstractPowerMenu(Context context) {
    initialize(context);
  }

  protected AbstractPowerMenu(Context context, AbstractMenuBuilder builder) {
    initialize(context);

    setShowBackground(builder.showBackground);
    setAnimation(builder.menuAnimation);
    setMenuRadius(builder.menuRadius);
    setMenuShadow(builder.menuShadow);
    setBackgroundColor(builder.backgroundColor);
    setBackgroundAlpha(builder.backgroundAlpha);
    setBackgroundSystemUiVisibility(builder.backgroundSystemUiVisibility);
    setFocusable(builder.focusable);
    setIsClipping(builder.isClipping);
    setAutoDismiss(builder.autoDismiss);
    setDefaultPosition(builder.defaultPosition);
    setDismissIfShowAgain(builder.dismissIfShowAgain);

    if (builder.lifecycleOwner != null) setLifecycleOwner(builder.lifecycleOwner);
    if (builder.backgroundClickListener != null) {
      setOnBackgroundClickListener(builder.backgroundClickListener);
    }
    if (builder.onDismissedListener != null) setOnDismissedListener(builder.onDismissedListener);
    if (builder.headerView != null) setHeaderView(builder.headerView);
    if (builder.footerView != null) setFooterView(builder.footerView);
    if (builder.animationStyle != -1) setAnimationStyle(builder.animationStyle);
    if (builder.width != 0) setWidth(builder.width);
    if (builder.height != 0) setHeight(builder.height);
    if (builder.padding != 0) setPadding(builder.padding);
    if (builder.divider != null) setDivider(builder.divider);
    if (builder.dividerHeight != 0) setDividerHeight(builder.dividerHeight);
    if (builder.preferenceName != null) setPreferenceName(builder.preferenceName);
    if (builder.initializeRule != null) setInitializeRule(builder.initializeRule);
    if (builder.circularEffect != null) setCircularEffect(builder.circularEffect);
  }

  protected void initialize(Context context) {
    this.layoutInflater = LayoutInflater.from(context);
    assert layoutInflater != null;
    this.backgroundView =
        LayoutPowerBackgroundBinding.inflate(layoutInflater, null, false).getRoot();
    this.backgroundView.setOnClickListener(background_clickListener);
    this.backgroundView.setAlpha(0.5f);
    this.backgroundWindow =
        new PopupWindow(
            backgroundView,
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT);

    LayoutPowerMenuBinding bindingMenu =
        LayoutPowerMenuBinding.inflate(layoutInflater, null, false);
    this.menuView = bindingMenu.getRoot();
    this.menuListView = bindingMenu.powerMenuListView;
    this.menuWindow =
        new PopupWindow(
            menuView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    this.menuCard = bindingMenu.powerMenuCard;

    setFocusable(false);
    setTouchInterceptor(onTouchListener);
    setOnMenuItemClickListener(onMenuItemClickListener);

    contentViewPadding = ConvertUtil.convertDpToPixel(10, context);
    MenuPreferenceManager.initialize(context);
  }

  /**
   * sets {@link LifecycleOwner} for preventing memory leak.
   *
   * <p>if sets the {@link LifecycleOwner} this popup will be dismissed automatically
   *
   * <p>when onDestroy method called by lifecycle.
   *
   * @param lifecycleOwner {@link androidx.appcompat.app.AppCompatActivity},
   *     <p>{@link androidx.fragment.app.FragmentActivity} or etc are implements {@link
   *     LifecycleOwner}.
   */
  public void setLifecycleOwner(@NonNull LifecycleOwner lifecycleOwner) {
    lifecycleOwner.getLifecycle().addObserver(this);
    this.lifecycleOwner = lifecycleOwner;
  }

  /**
   * makes focusing only on the menu popup.
   *
   * @param focusable focusable or not.
   */
  public void setFocusable(boolean focusable) {
    this.menuWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    this.menuWindow.setOutsideTouchable(!focusable);
  }

  /**
   * sets {@link android.view.View.OnTouchListener} manually for the outside of popup.
   *
   * @param onTouchListener onTouchListener.
   */
  public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
    this.menuWindow.setTouchInterceptor(onTouchListener);
  }

  /**
   * gets onMenuItemClickListener.
   *
   * @return {@link OnMenuItemClickListener}.
   */
  public OnMenuItemClickListener<E> getOnMenuItemClickListener() {
    return this.menuItemClickListener;
  }

  /**
   * sets {@link OnMenuItemClickListener}.
   *
   * @param menuItemClickListener menu item click listener.
   */
  public void setOnMenuItemClickListener(OnMenuItemClickListener<E> menuItemClickListener) {
    this.menuItemClickListener = menuItemClickListener;
    this.menuListView.setOnItemClickListener(itemClickListener);
  }

  /**
   * showing the popup to the anchor.
   *
   * @param anchor anchor view.
   */
  @MainThread
  private void showPopup(final View anchor, final Function0<Object> function) {
    if (!isShowing()) {
      this.isShowing = true;
      anchor.post(
          new Runnable() {
            @Override
            public void run() {
              if (showBackground) backgroundWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
              doMenuEffect();
              function.invoke();
            }
          });
    } else if (this.dismissIfShowAgain) {
      dismiss();
    }
  }

  /**
   * showing the popup menu as drop down to the anchor.
   *
   * @param anchor anchor view.
   */
  public void showAsDropDown(final View anchor) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(anchor);
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as drop down to the anchor with x-off and y-off.
   *
   * @param anchor anchor view.
   * @param xOff x-off,
   * @param yOff y-off.
   */
  public void showAsDropDown(final View anchor, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(anchor, xOff, yOff);
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as left-top aligns to the anchor.
   *
   * @param anchor anchor view.
   */
  public void showAsAnchorLeftTop(final View anchor) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(anchor, 0, -anchor.getMeasuredHeight());
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as left-top aligns to the anchor with x-off and y-off.
   *
   * @param anchor anchor view.
   * @param xOff x-off.
   * @param yOff y-off.
   */
  public void showAsAnchorLeftTop(final View anchor, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(anchor, xOff, yOff - anchor.getMeasuredHeight());
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as left-bottom aligns to the anchor.
   *
   * @param anchor anchor view.
   */
  public void showAsAnchorLeftBottom(final View anchor) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(anchor, 0, -getContentViewPadding());
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as left-bottom aligns to the anchor.
   *
   * @param anchor anchor view.
   * @param xOff x-off.
   * @param yOff y-off.
   */
  public void showAsAnchorLeftBottom(final View anchor, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(anchor, xOff, yOff - getContentViewPadding());
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as right-top aligns to the anchor.
   *
   * @param anchor anchor view.
   */
  public void showAsAnchorRightTop(final View anchor) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(
                anchor,
                anchor.getMeasuredWidth() / 2 + getContentViewWidth() / 2,
                -anchor.getMeasuredHeight());
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as right-top aligns to the anchor.
   *
   * @param anchor anchor view.
   * @param xOff x-off.
   * @param yOff y-off.
   */
  public void showAsAnchorRightTop(final View anchor, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(
                anchor,
                xOff + anchor.getMeasuredWidth() / 2 + getContentViewWidth() / 2,
                yOff - anchor.getMeasuredHeight());
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as right-bottom aligns to the anchor.
   *
   * @param anchor anchor view.
   */
  public void showAsAnchorRightBottom(final View anchor) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(
                anchor,
                anchor.getMeasuredWidth() / 2 + getContentViewWidth() / 2,
                -getContentViewPadding());
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as right-bottom aligns to the anchor.
   *
   * @param anchor anchor view.
   * @param xOff x-off.
   * @param yOff y-off.
   */
  public void showAsAnchorRightBottom(final View anchor, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(
                anchor,
                xOff + anchor.getMeasuredWidth() / 2 + getContentViewWidth() / 2,
                yOff - getContentViewPadding());
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as center align to the anchor.
   *
   * @param anchor anchor view.
   */
  public void showAsAnchorCenter(final View anchor) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(
                anchor,
                anchor.getMeasuredWidth() / 2 - getContentViewWidth() / 2,
                -anchor.getMeasuredHeight() / 2 - getContentViewHeight() / 2);
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as center align to the anchor.
   *
   * @param anchor anchor view.
   * @param xOff x-off.
   * @param yOff y-off.
   */
  public void showAsAnchorCenter(final View anchor, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAsDropDown(
                anchor,
                xOff + anchor.getMeasuredWidth() / 2 - getContentViewWidth() / 2,
                yOff - anchor.getMeasuredHeight() / 2 - getContentViewHeight() / 2);
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as center aligns to the anchor.
   *
   * @param anchor anchor view.
   */
  public void showAtCenter(final View anchor) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu as center aligns to the anchor with x-off and y-off.
   *
   * @param anchor anchor view.
   * @param xOff x-off.
   * @param yOff y-off.
   */
  public void showAtCenter(final View anchor, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAtLocation(anchor, Gravity.CENTER, xOff, yOff);
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu to the specific location to the anchor.
   *
   * @param anchor anchor view.
   * @param xOff x-off.
   * @param yOff y-off.
   */
  public void showAtLocation(final View anchor, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xOff, yOff);
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /**
   * showing the popup menu to the specific location to the anchor with {@link Gravity}.
   *
   * @param anchor anchor view.
   * @param gravity gravity of the menu.
   * @param xOff x-off.
   * @param yOff y-off.
   */
  public void showAtLocation(final View anchor, final int gravity, final int xOff, final int yOff) {
    Function0<Object> function =
        new Function0<Object>() {
          @Override
          public Unit invoke() {
            menuWindow.showAtLocation(anchor, gravity, xOff, yOff);
            return Unit.INSTANCE;
          }
        };
    showPopup(anchor, function);
  }

  /** apply menu effect. */
  private void doMenuEffect() {
    if (getCircularEffect() != null) {
      if (getCircularEffect().equals(CircularEffect.BODY)) {
        circularRevealed(menuWindow.getContentView());
      } else if (getCircularEffect().equals(CircularEffect.INNER)) {
        circularRevealed(getListView());
      }
    }
  }

  /**
   * shows circular revealed animation to a view.
   *
   * @param targetView view for animation target.
   */
  private void circularRevealed(View targetView) {
    targetView.addOnLayoutChangeListener(
        new View.OnLayoutChangeListener() {
          @Override
          public void onLayoutChange(
              View view,
              int left,
              int top,
              int right,
              int bottom,
              int oldLeft,
              int oldTop,
              int oldRight,
              int oldBottom) {
            view.removeOnLayoutChangeListener(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              Animator animator =
                  ViewAnimationUtils.createCircularReveal(
                      view,
                      (view.getLeft() + view.getRight()) / 2,
                      (view.getTop() + view.getBottom()) / 2,
                      0f,
                      Math.max(view.getWidth(), view.getHeight()));
              animator.setDuration(900);
              animator.start();
            }
          }
        });
  }

  /** dismiss the popup menu. */
  public void dismiss() {
    if (isShowing()) {
      this.menuWindow.dismiss();
      this.backgroundWindow.dismiss();
      this.isShowing = false;
      if (this.onDismissedListener != null) this.onDismissedListener.onDismissed();
    }
  }

  /**
   * gets the popup is showing or not.
   *
   * @return the popup is showing or not.
   */
  public boolean isShowing() {
    return this.isShowing;
  }

  /**
   * gets measured width of the popup.
   *
   * @return measured width of the popup.
   */
  public int getContentViewWidth() {
    int width = menuWindow.getContentView().getWidth();
    if (width == 0) {
      return getMeasuredContentView().getMeasuredWidth();
    } else {
      return width;
    }
  }

  /**
   * gets measured height of the popup.
   *
   * @return measured height of the popup.
   */
  public int getContentViewHeight() {
    int height = menuWindow.getContentView().getHeight();
    if (height == 0) {
      height += getAdapter().getContentViewHeight() + getContentViewPadding();
      if (getHeaderView() != null) height += getHeaderView().getMeasuredHeight();
      if (getFooterView() != null) height += getFooterView().getMeasuredHeight();
    }
    return height;
  }

  /**
   * gets the content view of the popup menu.
   *
   * @return content view of the popup menu.
   */
  protected View getMeasuredContentView() {
    View contentView = menuWindow.getContentView();
    contentView.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    return contentView;
  }

  /**
   * gets the content view padding of the popup menu.
   *
   * @return the content view padding view of the popup menu.
   */
  protected int getContentViewPadding() {
    return this.contentViewPadding;
  }

  /**
   * sets the width of the popup menu.
   *
   * @param width width of the popup menu.
   */
  public void setWidth(@Px int width) {
    this.menuWindow.setWidth(width);
    FrameLayout.LayoutParams layoutParams =
        (FrameLayout.LayoutParams) menuListView.getLayoutParams();
    layoutParams.width = width - contentViewPadding;
    getMenuListView().setLayoutParams(layoutParams);
  }

  /**
   * sets the height of the popup menu.
   *
   * @param height height of the popup menu.
   */
  public void setHeight(@Px int height) {
    this.fixedHeight = true;
    this.menuWindow.setHeight(height);
  }

  /**
   * sets a padding size of the popup menu.
   *
   * @param padding padding size.
   */
  public void setPadding(@Px int padding) {
    this.menuListView.setPadding(padding, padding, padding, padding);
  }

  /**
   * sets the measured height of the popup menu list.
   *
   * @param height the measured height of the popup menu list.
   */
  protected void setMeasuredHeight(@Px int height) {
    this.menuWindow.setHeight(height);
    FrameLayout.LayoutParams layoutParams =
        (FrameLayout.LayoutParams) menuListView.getLayoutParams();
    layoutParams.height = height - contentViewPadding;
    getMenuListView().setLayoutParams(layoutParams);
  }

  /**
   * sets the divider height of the popup menu.
   *
   * @param height divider height of the popup menu.
   */
  public void setDividerHeight(@Px int height) {
    menuListView.setDividerHeight(height);
  }

  /**
   * sets the drawable of the divider.
   *
   * @param divider drawable of the divider.
   */
  public void setDivider(Drawable divider) {
    menuListView.setDivider(divider);
  }

  /**
   * sets the background is showing or not.
   *
   * @param show background is showing or not.
   */
  public void setShowBackground(boolean show) {
    this.showBackground = show;
  }

  /**
   * sets the dismissed listener of the popup menu.
   *
   * @param onDismissedListener {@link OnDismissedListener}.
   */
  public void setOnDismissedListener(OnDismissedListener onDismissedListener) {
    this.onDismissedListener = onDismissedListener;
  }

  /**
   * sets the background click listener of the background.
   *
   * @param onBackgroundClickListener {@link android.view.View.OnClickListener}.
   */
  public void setOnBackgroundClickListener(View.OnClickListener onBackgroundClickListener) {
    this.backgroundView.setOnClickListener(onBackgroundClickListener);
  }

  /**
   * sets animations of the popup. It will start up when the popup is showing.
   *
   * @param menuAnimation menu animation.
   */
  public void setAnimation(@NonNull MenuAnimation menuAnimation) {
    if (menuAnimation == MenuAnimation.NONE) {
      menuWindow.setAnimationStyle(0);
    } else if (menuAnimation == MenuAnimation.DROP_DOWN) {
      menuWindow.setAnimationStyle(-1);
    } else if (menuAnimation == MenuAnimation.FADE) {
      menuWindow.setAnimationStyle(R.style.FadeMenuAnimation);
      backgroundWindow.setAnimationStyle(R.style.FadeMenuAnimation);
    } else if (menuAnimation == MenuAnimation.SHOWUP_BOTTOM_LEFT) {
      menuWindow.setAnimationStyle(R.style.ShowUpAnimation_BL);
    } else if (menuAnimation == MenuAnimation.SHOWUP_BOTTOM_RIGHT) {
      menuWindow.setAnimationStyle(R.style.ShowUpAnimation_BR);
    } else if (menuAnimation == MenuAnimation.SHOWUP_TOP_LEFT) {
      menuWindow.setAnimationStyle(R.style.ShowUpAnimation_TL);
    } else if (menuAnimation == MenuAnimation.SHOWUP_TOP_RIGHT) {
      menuWindow.setAnimationStyle(R.style.ShowUpAnimation_TR);
    } else if (menuAnimation == MenuAnimation.SHOW_UP_CENTER) {
      menuWindow.setAnimationStyle(R.style.ShowUpAnimation_Center);
    } else if (menuAnimation == MenuAnimation.ELASTIC_BOTTOM_LEFT) {
      menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_BL);
    } else if (menuAnimation == MenuAnimation.ELASTIC_BOTTOM_RIGHT) {
      menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_BR);
    } else if (menuAnimation == MenuAnimation.ELASTIC_TOP_LEFT) {
      menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_TL);
    } else if (menuAnimation == MenuAnimation.ELASTIC_TOP_RIGHT) {
      menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_TR);
    } else if (menuAnimation == MenuAnimation.ELASTIC_CENTER) {
      menuWindow.setAnimationStyle(R.style.ElasticMenuAnimation_Center);
    }
  }

  /**
   * sets custom animations of the popup. It will start up when the popup is showing.
   *
   * @param style custom animation style.
   */
  public void setAnimationStyle(@StyleRes int style) {
    this.menuWindow.setAnimationStyle(style);
  }

  /**
   * sets the corner radius of the popup menu.
   *
   * @param radius corner radius.
   */
  public void setMenuRadius(@Px float radius) {
    this.menuCard.setRadius(radius);
  }

  /**
   * sets the shadow of the popup menu.
   *
   * @param shadow shadow value.
   */
  public void setMenuShadow(@Px float shadow) {
    this.menuCard.setCardElevation(shadow);
  }

  /**
   * sets the clipping enable or unable.
   *
   * @param isClipping clipping enable or unable.
   */
  public void setIsClipping(boolean isClipping) {
    this.menuWindow.setClippingEnabled(isClipping);
  }

  /**
   * sets the selected position of the popup menu. It can be used for scrolling as the position.
   *
   * @param position selected position.
   */
  public void setSelection(int position) {
    this.menuListView.setSelection(position);
  }

  /**
   * sets the color of the background.
   *
   * @param color color value.
   */
  public void setBackgroundColor(@ColorInt int color) {
    backgroundView.setBackgroundColor(color);
  }

  /**
   * sets the alpha of the background.
   *
   * @param alpha alpha value.
   */
  public void setBackgroundAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
    backgroundView.setAlpha(alpha);
  }

  /**
   * sets system UI visibility flags for {@link #backgroundView}.
   *
   * @param visibility visibility value.
   */
  public void setBackgroundSystemUiVisibility(int visibility) {
    backgroundView.setSystemUiVisibility(visibility);
  }

  /**
   * sets the header view of the popup menu list.
   *
   * @param view {@link View}.
   */
  public void setHeaderView(View view) {
    if (this.headerView == null) {
      this.menuListView.addHeaderView(view);
      this.headerView = view;
      this.headerView.setOnClickListener(headerFooterClickListener);
      this.headerView.measure(
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }
  }

  /**
   * sets the header view of the popup menu list.
   *
   * @param view {@link View}.
   * @param data Object.
   * @param isSelectable is selectable or not.
   */
  public void setHeaderView(View view, Object data, boolean isSelectable) {
    if (this.headerView == null) {
      this.menuListView.addHeaderView(view, data, isSelectable);
      this.headerView = view;
      this.headerView.setOnClickListener(headerFooterClickListener);
      this.headerView.measure(
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }
  }

  /**
   * sets the header view of the popup menu using layout.
   *
   * @param layout layout.
   */
  public void setHeaderView(int layout) {
    if (this.headerView == null) {
      View view = layoutInflater.inflate(layout, null, false);
      setHeaderView(view);
    }
  }

  /**
   * sets the footer view of the popup menu list.
   *
   * @param view {@link View}.
   */
  public void setFooterView(View view) {
    if (this.footerView == null) {
      this.menuListView.addFooterView(view);
      this.footerView = view;
      this.footerView.setOnClickListener(headerFooterClickListener);
      this.footerView.measure(
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }
  }

  /**
   * sets the footer view of the popup menu list.
   *
   * @param view {@link View}.
   * @param data Object.
   * @param isSelectable is selectable or not.
   */
  public void setFooterView(View view, Object data, boolean isSelectable) {
    if (this.footerView == null) {
      this.menuListView.addFooterView(view, data, isSelectable);
      this.footerView = view;
      this.footerView.setOnClickListener(headerFooterClickListener);
      this.footerView.measure(
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }
  }

  /**
   * sets the footer view of the popup menu using layout.
   *
   * @param layout layout.
   */
  public void setFooterView(int layout) {
    if (this.footerView == null) {
      View view = layoutInflater.inflate(layout, null, false);
      setFooterView(view);
    }
  }

  /**
   * check validation of the initialization from the preference persistence.
   *
   * @param event lifecycle event.
   * @return true or false.
   */
  private boolean checkRuleValidates(@NonNull Lifecycle.Event event) {
    return getInitializeRule() != null && getInitializeRule().equals(event);
  }

  /**
   * invokes onMenuListener manually.
   *
   * @param position the invoked position.
   */
  public void invokeOnMenuListener(int position) {
    if (position >= 0 && position < getItemList().size() && getOnMenuItemClickListener() != null) {
      getOnMenuItemClickListener()
          .onItemClick(
              getPreferencePosition(position), getItemList().get(getPreferencePosition(position)));
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  public void onCreate() {
    if (checkRuleValidates(Lifecycle.Event.ON_CREATE)) {
      invokeOnMenuListener(defaultPosition);
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  public void onStart() {
    if (checkRuleValidates(Lifecycle.Event.ON_START)) {
      invokeOnMenuListener(defaultPosition);
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  public void onResume() {
    if (checkRuleValidates(Lifecycle.Event.ON_RESUME)) {
      invokeOnMenuListener(defaultPosition);
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  public void onDestroy() {
    dismiss();
  }

  /**
   * gets the adapter of the popup menu list.
   *
   * @return adapter
   */
  public T getAdapter() {
    return this.adapter;
  }

  @Override
  public void addItem(E item) {
    getAdapter().addItem(item);
  }

  @Override
  public void addItem(int position, E item) {
    getAdapter().addItem(position, item);
  }

  @Override
  public void addItemList(List<E> itemList) {
    getAdapter().addItemList(itemList);
  }

  @Override
  public ListView getListView() {
    return getAdapter().getListView();
  }

  @Override
  public void setListView(ListView listView) {
    getAdapter().setListView(getMenuListView());
  }

  @Override
  public int getSelectedPosition() {
    return getAdapter().getSelectedPosition();
  }

  @Override
  public void setSelectedPosition(int position) {
    getAdapter().setSelectedPosition(position);
  }

  @Override
  public void removeItem(E item) {
    getAdapter().removeItem(item);
  }

  @Override
  public void removeItem(int position) {
    getAdapter().removeItem(position);
  }

  @Override
  public void clearItems() {
    getAdapter().clearItems();
  }

  @Override
  public List<E> getItemList() {
    return getAdapter().getItemList();
  }

  /**
   * gets the {@link ListView} of the popup menu.
   *
   * @return {@link ListView}.
   */
  public ListView getMenuListView() {
    return this.menuListView;
  }

  /**
   * gets the header view of the popup menu list.
   *
   * @return {@link View}.
   */
  public View getHeaderView() {
    return headerView;
  }

  /**
   * gets the footer view of the popup menu list.
   *
   * @return {@link View}.
   */
  public View getFooterView() {
    return footerView;
  }

  /**
   * sets the auto-dismissing or not.
   *
   * <p>The popup menu will be dismissed automatically when the item would be clicked.
   *
   * @param autoDismiss is auto-dismissing or not.
   */
  public void setAutoDismiss(boolean autoDismiss) {
    this.autoDismiss = autoDismiss;
  }

  /**
   * sets the dismiss action if already popup is showing.
   *
   * @param dismissIfShowAgain dismiss if already popup is showing.
   */
  public void setDismissIfShowAgain(boolean dismissIfShowAgain) {
    this.dismissIfShowAgain = dismissIfShowAgain;
  }

  /**
   * gets the preference name of PowerMenu.
   *
   * @return preference name.
   */
  public String getPreferenceName() {
    return getAdapter().getPreferenceName();
  }

  /**
   * gets the saved preference position from the SharedPreferences.
   *
   * @param defaultPosition the default position of the preference.
   * @return saved preference position.
   */
  public int getPreferencePosition(int defaultPosition) {
    return MenuPreferenceManager.getInstance()
        .getPosition(getAdapter().getPreferenceName(), defaultPosition);
  }

  /**
   * sets the preference position name for persistence.
   *
   * @param defaultPosition the default position of the preference.
   */
  public void setPreferencePosition(int defaultPosition) {
    MenuPreferenceManager instance = MenuPreferenceManager.getInstance();
    if (instance != null && getPreferenceName() != null) {
      instance.setPosition(getPreferenceName(), defaultPosition);
    }
  }

  /**
   * sets a preference name for persistence.
   *
   * @param preferenceName preference name.
   */
  private void setPreferenceName(@NonNull String preferenceName) {
    getAdapter().setPreference(preferenceName);
  }

  /** clears the preference name of PowerMenu. */
  public void clearPreference() {
    if (getAdapter().getPreferenceName() != null) {
      MenuPreferenceManager.getInstance().clearPosition(getAdapter().getPreferenceName());
    }
  }

  /**
   * sets initialize rule by {@link Lifecycle.Event}.
   *
   * <p>There are three events(ON_CREATE, ON_START, ON_RESUME) working by lifecycle.
   *
   * @param event {@link Lifecycle.Event}.
   */
  private void setInitializeRule(@NonNull Lifecycle.Event event) {
    this.initializeRule = event;
  }

  /**
   * gets initialize rule by {@link Lifecycle.Event}.
   *
   * @return {@link Lifecycle.Event}.
   */
  private Lifecycle.Event getInitializeRule() {
    return this.initializeRule;
  }

  /**
   * sets the default position from the persistence.
   *
   * @param defaultPosition default position.
   */
  private void setDefaultPosition(int defaultPosition) {
    this.defaultPosition = defaultPosition;
  }

  /**
   * gets menu effect.
   *
   * @return {@link CircularEffect}.
   */
  public CircularEffect getCircularEffect() {
    return this.circularEffect;
  }

  /**
   * sets menu effects for showing popup more dynamically.
   *
   * @param circularEffect {@link CircularEffect}.
   */
  public void setCircularEffect(@NonNull CircularEffect circularEffect) {
    this.circularEffect = circularEffect;
  }
}
