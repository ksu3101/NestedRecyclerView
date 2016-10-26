package kr.swkang.nestedrecyclerview.utils.widgets;

import android.support.annotation.NonNull;

import kr.swkang.nestedrecyclerview.utils.widgets.rvs.SwRecyclerViewAdapter;

/**
 * @author KangSung-Woo
 * @since 2016/07/11
 */
public interface OnViewClickListener {
  void onClicked(@NonNull SwRecyclerViewAdapter.ViewHolder viewHolder, int position);
}
