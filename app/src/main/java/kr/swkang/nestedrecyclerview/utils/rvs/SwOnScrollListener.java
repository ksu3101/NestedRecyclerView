package kr.swkang.nestedrecyclerview.utils.rvs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import kr.swkang.nestedrecyclerview.main.MainActivity;

/**
 * @author KangSung-Woo
 * @since 2016/08/01
 */
public abstract class SwOnScrollListener
    extends RecyclerView.OnScrollListener {
  private Picasso  picasso;
  private Context  context;
  private Runnable settlingResumeRunnable;

  public SwOnScrollListener(@NonNull Context context) {
    this.context = context;
    this.picasso = Picasso.with(context);
    this.settlingResumeRunnable = null;
  }

  @Override
  public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    if (this.picasso != null) {
      if (newState == RecyclerView.SCROLL_STATE_IDLE) {
        if (settlingResumeRunnable != null) recyclerView.removeCallbacks(settlingResumeRunnable);
        picasso.resumeTag(context);
      }
      else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
        settlingResumeRunnable = new Runnable() {
          @Override
          public void run() {
            picasso.resumeTag(context);
          }
        };
      }
      else {
        picasso.pauseTag(context);
      }
    }
  }

}
