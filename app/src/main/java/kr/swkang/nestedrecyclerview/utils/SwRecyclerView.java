package kr.swkang.nestedrecyclerview.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author KangSung-Woo
 * @since 2016/07/27
 */
public class SwRecyclerView
    extends RecyclerView {
  private View emptyView;
  private AdapterDataObserver observer = new AdapterDataObserver() {
    @Override
    public void onChanged() {
      Adapter<?> adapter = getAdapter();
      if (adapter != null && emptyView != null) {
        if (adapter.getItemCount() == 0) {
          emptyView.setVisibility(View.VISIBLE);
          SwRecyclerView.this.setVisibility(View.GONE);
        }
        else {
          emptyView.setVisibility(View.GONE);
          SwRecyclerView.this.setVisibility(View.VISIBLE);
        }
      }
    }
  };

  public SwRecyclerView(Context context) {
    super(context);
  }

  public SwRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SwRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public void setAdapter(Adapter adapter) {
    super.setAdapter(adapter);
    adapter.registerAdapterDataObserver(observer);
    observer.onChanged();
  }

  public void setEmptyView(@NonNull View view) {
    this.emptyView = view;
    if (observer != null) {
      observer.onChanged();
    }
  }

}
