package kr.swkang.nestedrecyclerview.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.swkang.nestedrecyclerview.recyclerview.data.Content;
import kr.swkang.nestedrecyclerview.utils.OnViewClickListener;
import kr.swkang.nestedrecyclerview.utils.SwRecyclerViewAdapter;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public class MainRvAdapter
    extends SwRecyclerViewAdapter<Content> {

  public MainRvAdapter(@NonNull Context context, @NonNull ArrayList<Content> list, OnViewClickListener clickListener) {
    super(context, list, clickListener);
  }

  @Override
  protected View createView(Context context, ViewGroup viewGroup, int viewType) {
    return null;
  }

  @Override
  protected void bindView(Content item, ViewHolder viewHolder) {

  }

}
