package kr.swkang.nestedrecyclerview.utils.widgets.viewpagers.pagerindicator;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kr.swkang.nestedrecyclerview.R;

/**
 * @author KangSung-Woo
 * @since 2016/07/22
 */
public class ViewPagerIndicator
    extends LinearLayout
    implements PageIndicator {

  private ViewPager            vp;
  private ArrayList<ImageView> indicators;
  private int                  size;
  private int                  currentPosition;
  private int                  circleMargin;

  @DrawableRes
  private int selectedItemDrawable = R.drawable.vp_indicator_selected;
  @DrawableRes
  private int normalItemDrawable   = R.drawable.vp_indicator_normal;

  public ViewPagerIndicator(Context context) {
    super(context);
  }

  public ViewPagerIndicator(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected Parcelable onSaveInstanceState() {
    Bundle bundle = new Bundle();
    bundle.putParcelable("instanceState", super.onSaveInstanceState());
    bundle.putInt("currentPosition", currentPosition);
    return bundle;
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    if (state instanceof Bundle) {
      Bundle bundle = (Bundle) state;
      currentPosition = bundle.getInt("currentPosition");
    }
    super.onRestoreInstanceState(state);
  }

  private boolean checkValidations() {
    if (vp == null) {
      throw new NullPointerException("ViewPager can not be null..");
    }
    if (vp.getAdapter() == null) {
      throw new NullPointerException("ViewPagers adapter can not be null..");
    }
    return true;
  }

  public void setSelectedItemDrawable(@DrawableRes int selectedItemDrawableResId) {
    this.selectedItemDrawable = selectedItemDrawableResId;
  }

  public void setNormalItemDrawable(@DrawableRes int normalItemDrawableResId) {
    this.normalItemDrawable = normalItemDrawableResId;
  }

  public void setCircleMarginPX(int circleMargin) {
    this.circleMargin = circleMargin;
  }

  public void setCircleMarginDP(int circleMargin) {
    this.circleMargin = dp2px((float) circleMargin);
  }

  public void setCircleMarginResId(@DimenRes int circleMarginResId) {
    this.circleMargin = getContext().getResources().getDimensionPixelOffset(circleMarginResId);
  }

  @Override
  public void setViewPager(ViewPager vp) {
    setViewPager(vp, (vp != null && vp.getAdapter() != null ? vp.getAdapter().getCount() : -1));
  }

  @Override
  public void setViewPager(ViewPager vp, int realCount) {
    this.vp = vp;
    if (realCount == -1) {
      throw new IllegalStateException("wrong RealCount number. [-1]");
    }
    else if (checkValidations()) {
      this.size = realCount;
      vp.removeOnPageChangeListener(this);
      vp.addOnPageChangeListener(this);
      this.currentPosition = (vp.getCurrentItem() & size);

      initializeIndicators();
    }
  }

  private void initializeIndicators() {
    setClipChildren(false);
    setClipToPadding(false);

    if (indicators == null) {
      indicators = new ArrayList<>();
    }

    removeAllViews();
    indicators.clear();

    if (size > 0 && currentPosition >= 0) {
      for (int i = 0; i < size; i++) {
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        if (i > 0) lParams.leftMargin = circleMargin;

        ImageView iv = new ImageView(getContext());
        iv.setLayoutParams(lParams);
        if (currentPosition == i) {
          iv.setImageResource(selectedItemDrawable);
        }
        else {
          iv.setImageResource(normalItemDrawable);
        }
        addView(iv);
        indicators.add(iv);
      }// for loop
    }
  }

  @Override
  public void setCurrentItem(int item) {
    if (checkValidations()) {
      vp.setCurrentItem(item);
    }
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    this.currentPosition = position;
  }

  @Override
  public void onPageSelected(int position) {
    this.currentPosition = position;
    for (int i = 0; i < indicators.size(); i++) {
      indicators.get(i).setImageResource(i == (currentPosition % size) ? selectedItemDrawable : normalItemDrawable);
    }
  }

  @Override
  public void onPageScrollStateChanged(int state) {
  }

  private int dp2px(float dp) {
    final float scale = getContext().getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }
}
