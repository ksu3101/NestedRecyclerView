package kr.swkang.nestedrecyclerview.utils.widgets.rvs.snappy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 참고 : http://stackoverflow.com/questions/26370289/snappy-scrolling-in-recyclerview
 *
 * @since 2016/07/27
 */
public class SnapRecyclerView
    extends RecyclerView {

  public SnapRecyclerView(Context context) {
    super(context);
  }

  public SnapRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SnapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public boolean fling(int velocityX, int velocityY) {
    final LayoutManager layoutManager = getLayoutManager();

    if (layoutManager != null) {
      if (layoutManager instanceof SnappyLayoutManager) {
        super.smoothScrollToPosition(((SnappyLayoutManager) getLayoutManager()).getPositionForVelocity(velocityX, velocityY));
        return true;
      }
    }
    return super.fling(velocityX, velocityY);
  }

  @Override
  public boolean onTouchEvent(MotionEvent e) {
    // We want the parent to handle all touch events--there's a lot going on there,
    // and there is no reason to overwrite that functionality--bad things will happen.
    final boolean ret = super.onTouchEvent(e);
    final LayoutManager lm = getLayoutManager();

    if (lm instanceof SnappyLayoutManager
        && (e.getAction() == MotionEvent.ACTION_UP ||
        e.getAction() == MotionEvent.ACTION_CANCEL)
        && getScrollState() == SCROLL_STATE_IDLE) {
      // The layout manager is a SnappyLayoutManager, which means that the
      // children should be snapped to a grid at the end of a drag or
      // fling. The motion event is either a user lifting their finger or
      // the cancellation of a motion events, so this is the time to take
      // over the scrolling to perform our own functionality.
      // Finally, the scroll state is idle--meaning that the resultant
      // velocity after the user's gesture was below the threshold, and
      // no fling was performed, so the view may be in an unaligned state
      // and will not be flung to a proper state.
      smoothScrollToPosition(((SnappyLayoutManager) lm).getFixScrollPos());
    }

    return ret;
  }

}
