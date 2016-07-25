package kr.swkang.nestedrecyclerview.main.list;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kr.swkang.nestedrecyclerview.R;

/**
 * @author KangSung-Woo
 * @since 2016/07/25
 */
public class SubHorRvItemDecoration
    extends RecyclerView.ItemDecoration {

  private int gapSize;

  public SubHorRvItemDecoration(@NonNull Context context) {
    this.gapSize = context.getResources().getDimensionPixelSize(R.dimen.subrv_hor_gap_size);
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    int position = parent.getChildAdapterPosition(view);

    outRect.left = (position > 0 ? gapSize / 2 : 0);
    outRect.right = (position < parent.getAdapter().getItemCount() - 1 ? gapSize / 2 : 0);
  }

}