package kr.swkang.nestedrecyclerview.main.list;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodySection;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.HeaderContents;

/**
 * @author KangSung-Woo
 * @since 2016/07/25
 */
public class MainRvItemDecoration
    extends RecyclerView.ItemDecoration {

  private int defaultLeftMargin;
  private int defaultRightMargin;
  private int defaultTopMargin;
  private int defaultBottomMargin;

  public MainRvItemDecoration(@NonNull Context context) {
    this.defaultLeftMargin = context.getResources().getDimensionPixelSize(R.dimen.rv_def_left_margin);
    this.defaultRightMargin = context.getResources().getDimensionPixelSize(R.dimen.rv_def_right_margin);
    this.defaultTopMargin = context.getResources().getDimensionPixelSize(R.dimen.rv_def_top_margin);
    this.defaultBottomMargin = context.getResources().getDimensionPixelSize(R.dimen.rv_def_bottom_margin);
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    int position = parent.getChildAdapterPosition(view);
    if (position >= 0) {
      int viewType = parent.getAdapter().getItemViewType(position);

      if (viewType == HeaderContents.VIEWTYPE_VALUE) {
        // 0 == Header contents
      }
      else if (viewType == BodySection.FULL_VIEWTYPE_VALUE) {
        // BODY contents (Span 2)
        // top, bottom margin (paddin on inside Horizontal-RecyclerView)
        outRect.top = defaultTopMargin;
        outRect.bottom = defaultBottomMargin;
      }
      else if (viewType == BodySection.HALF_VIEWTYPE_VALUE) {
        // BODY contents (Span 1)
        // top, bottom margin
        outRect.top = (defaultTopMargin / 2);
        outRect.bottom = (defaultBottomMargin / 2);

        RecyclerView.Adapter a = parent.getAdapter();
        if (a instanceof MainRvAdapter) {
          MainRvAdapter adapter = (MainRvAdapter) a;
          // if it last item
          int count = adapter.getItemCount();
          if (adapter.isFooter(count - 1)) {
            // has Footer
            count = count - 2;
          }
          if (position > count - 2) {
            outRect.bottom = defaultBottomMargin;
          }

          final int firstHalfBodyPosition = adapter.getFirstHalfBodyContentsPosition();
          if (firstHalfBodyPosition <= position) {
            if (position % 2 == 0) {
              // on Left item (left margin, and half right margin)
              outRect.left = defaultLeftMargin;
              outRect.right = (defaultRightMargin / 2);
            }
            else {
              // on Right item (half left margin, and right margin)
              outRect.left = (defaultLeftMargin / 2);
              outRect.right = defaultRightMargin;
            }
          }

        }
      }
      else if (viewType == MainRvAdapter.FOOTER_LOADMORE) {
        // list.size() + 1 == Footer contents
      }
      else {
        // Section Headers
        // left, right, top, bottom margin
        outRect.left = defaultLeftMargin;
        outRect.right = defaultRightMargin;
        outRect.top = defaultTopMargin;
        outRect.bottom = defaultBottomMargin;
      }
    }
  }

}
