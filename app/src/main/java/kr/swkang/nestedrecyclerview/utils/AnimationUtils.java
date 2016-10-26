package kr.swkang.nestedrecyclerview.utils;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

/**
 * @author KangSung-Woo
 * @since 2016/09/01
 */
public class AnimationUtils {
  private static final String TAG = AnimationUtils.class.getSimpleName();

  /**
   * 0.0f 에서 duration동안 1.0f로 증가 하는 ValueAnimator 를 만든다.
   *
   * @param duration       animation duration.
   * @param updateListener value animation이 진행 되는 동안의 해야 할 작업을 구현한 listner 인스턴스.
   * @return ValueAnimator
   * @see ValueAnimator
   */
  public static ValueAnimator createDefaultStartFloatValueAnimator(int duration, ValueAnimator.AnimatorUpdateListener updateListener) {
    return createFloatValueAnimator(duration, updateListener, null, 0.0f, 1.0f);
  }

  /**
   * 1.0f 에서 duration동안 0.0f로 감소 하는 ValueAnimator 를 만든다.
   *
   * @param duration       animation duration.
   * @param updateListener value animation이 진행 되는 동안의 해야 할 작업을 구현한 listner 인스턴스.
   * @return ValueAnimator
   * @see ValueAnimator
   */
  public static ValueAnimator createDefaultEndFloatValueAnimator(int duration, ValueAnimator.AnimatorUpdateListener updateListener) {
    return createFloatValueAnimator(duration, updateListener, null, 1.0f, 0.0f);
  }

  /**
   * values(float)값에 의해 start value부터 end value까지 duration동안 증가 혹은 감소하는 ValueAnimator를 만든다.
   * return 받은 ValueAnimator의 객체의 start()메소드를 이용하여 시작 하면 된다.
   *
   * @param duration         animation duration.
   * @param updateListener   value animation이 진행 되는 동안의 해야 할 작업을 구현한 listener 인스턴스.
   * @param animationAdapter value animation의 상황에 따라 불리어지는 콜백을 구현한 listener 인스턴스.
   * @param values           start value... end value.
   * @return ValueAnimator
   * @see ValueAnimator
   */
  public static ValueAnimator createFloatValueAnimator(int duration,
                                                       ValueAnimator.AnimatorUpdateListener updateListener,
                                                       AnimatorListenerAdapter animationAdapter,
                                                       float... values) {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(values);
    valueAnimator.setDuration(duration);
    if (updateListener != null) valueAnimator.addUpdateListener(updateListener);
    if (animationAdapter != null) valueAnimator.addListener(animationAdapter);
    return valueAnimator;
  }

  /**
   * 0.0f 에서 duration동안 1.0f로 증가 하는 ValueAnimator 를 만든다.
   *
   * @param duration       animation duration.
   * @param updateListener value animation이 진행 되는 동안의 해야 할 작업을 구현한 listner 인스턴스.
   * @return ValueAnimator
   * @see ValueAnimator
   */
  public static ValueAnimator createDefaultIntValueAnimator(
      int duration,
      ValueAnimator.AnimatorUpdateListener updateListener,
      int start, int end
  ) {
    return createIntValueAnimator(duration, updateListener, null, start, end);
  }

  /**
   * value(integer)값에 의해 start value부터 end value까지 duration동안 증가 혹은 감소하는 ValueAnimator를
   * 만든다.
   * return 받은 ValueAnimator객체의 start()메소드를 이용하여 시작 한다.
   *
   * @param duration         animation duration.
   * @param updateListener   value animation이 진행 되는 동안의 해야 할 작업을 구현한 listener 인스턴스.
   * @param animationAdapter value animation의 상황에 따라 불리어지는 콜백을 구현한 listener 인스턴스.
   * @param values           start value... end value.
   * @return ValueAnimator
   * @see ValueAnimator
   */
  public static ValueAnimator createIntValueAnimator(int duration,
                                                     ValueAnimator.AnimatorUpdateListener updateListener,
                                                     AnimatorListenerAdapter animationAdapter,
                                                     int... values) {
    ValueAnimator valueAnimator = ValueAnimator.ofInt(values);
    valueAnimator.setDuration(duration);
    if (updateListener != null) valueAnimator.addUpdateListener(updateListener);
    if (animationAdapter != null) valueAnimator.addListener(animationAdapter);
    return valueAnimator;
  }

  /**
   * Fade In 애니메이션을 생성한다.
   *
   * @param duration 애니메이션이 진행될 ms시간.
   * @return {@link AlphaAnimation}
   */
  public static AlphaAnimation createFadeInAnimation(@IntRange(from = 0) int duration) {
    AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
    fadeIn.setDuration(duration);
    fadeIn.setFillAfter(true);
    fadeIn.setInterpolator(new AccelerateInterpolator());
    return fadeIn;
  }

  /**
   * Fade In 애니메이션을 생성한다.
   *
   * @param duration 애니메이션이 진행될 ms시간.
   * @param listener 애니메이션 콜백 리스너.
   * @return {@link AlphaAnimation}
   */
  public static AlphaAnimation createFadeInAnimation(@IntRange(from = 0) int duration, Animation.AnimationListener listener) {
    AlphaAnimation fadeIn = createFadeInAnimation(duration);
    if (listener != null) {
      fadeIn.setAnimationListener(listener);
    }
    return fadeIn;
  }

  /**
   * Fade Out 애니메이션을 생성한다.
   *
   * @param duration 애니메이션이 진행될 ms시간.
   * @return {@link AlphaAnimation}
   */
  public static AlphaAnimation createFadeOutAnimation(@IntRange(from = 0) int duration) {
    AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
    fadeOut.setDuration(duration);
    fadeOut.setFillAfter(false);
    fadeOut.setInterpolator(new DecelerateInterpolator());
    return fadeOut;
  }

  /**
   * Fade Out 애니메이션을 생성한다.
   *
   * @param duration 애니메이션이 진행될 ms시간.
   * @param listener 애니메이션 콜백 리스너.
   * @return {@link AlphaAnimation}
   */
  public static AlphaAnimation createFadeOutAnimation(@IntRange(from = 0) int duration, Animation.AnimationListener listener) {
    AlphaAnimation fadeOut = createFadeOutAnimation(duration);
    if (listener != null) {
      fadeOut.setAnimationListener(listener);
    }
    return fadeOut;
  }

  /**
   * Drawable1과 Drawable2간의 트랜지션 에니메이션을 적용한 TransitionDrawable을 만든다.
   * 만들어진 TransitionDrawable의 객채의 startTransition(durationMillis)메소드를 이용하여
   * 애니메이션을 시작 한다.
   *
   * @param layer1 before Drawable.
   * @param layer2 after Drawable.
   * @return {@link TransitionDrawable}
   */
  public static TransitionDrawable createTransitionDrawable(@NonNull Drawable layer1, @NonNull Drawable layer2) {
    TransitionDrawable td = new TransitionDrawable(new Drawable[]{layer1, layer2});
    td.setCrossFadeEnabled(true);
    return td;
  }

  /**
   * Drawable1(resId1) 과 Drawable2(resId2)간의 트랜지션 애니메이션을 적용한 TransitionDrwable을 만든다.  만들어진
   * TransitionDrwable 객체의 startTransition(durationMillis)를 이용하여 애니메이션을 시작한다.
   *
   * @param res         Resources (from Context)
   * @param layerResId1 before Drawable Resource ID.
   * @param layerResId2 after Drawable Resource ID.
   * @return {@link TransitionDrawable}
   */
  public static TransitionDrawable createTransitionDrawable(@NonNull Resources res,
                                                            int layerResId1,
                                                            int layerResId2) {
    TransitionDrawable td = new TransitionDrawable(new Drawable[]{res.getDrawable(layerResId1),
        res.getDrawable(layerResId2)});
    td.setCrossFadeEnabled(true);
    return td;
  }

}
