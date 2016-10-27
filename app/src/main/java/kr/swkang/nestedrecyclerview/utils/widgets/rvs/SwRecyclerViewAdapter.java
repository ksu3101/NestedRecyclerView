package kr.swkang.nestedrecyclerview.utils.widgets.rvs;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;
import kr.swkang.nestedrecyclerview.utils.widgets.OnViewClickListener;

/**
 * @author KangSung-Woo
 * @since 2016/07/09
 */
public abstract class SwRecyclerViewAdapter<T>
  extends RecyclerView.Adapter<SwRecyclerViewAdapter.ViewHolder> {
  protected List<T>             list;
  protected Context             context;
  protected OnViewClickListener clickListener;
  private   Object              tagObj;

  public SwRecyclerViewAdapter(@NonNull Context context, @NonNull List<T> list) {
    this(context, list, null);
  }

  public SwRecyclerViewAdapter(@NonNull Context context, @NonNull List<T> list, OnViewClickListener clickListener) {
    this(context, list, null, clickListener);
  }

  public SwRecyclerViewAdapter(@NonNull Context context, @NonNull List<T> list, Object tag, OnViewClickListener clickListener) {
    super();
    this.context = context;
    this.clickListener = clickListener;
    this.list = new ArrayList<>();
    setTag(tag);
    setItem(list, false);
  }

  /**
   * viewType에 따라서 ViewHolder를만들고 bind할 View의 인스턴스를 반환 한다.
   */
  protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

  /**
   * createView()에서 생성한 View와 position의 Data를 기반으로 뷰를 업데이트 한다.
   */
  protected abstract void bindView(int viewType, T item, ViewHolder viewHolder);

  public Object getTag() {
    return tagObj;
  }

  public void setTag(Object tagObj) {
    this.tagObj = tagObj;
  }

  public void clearItems() {
    if (list != null && !list.isEmpty()) {
      list.clear();
      notifyDataSetChanged();
    }
  }

  public void setItem(@NonNull List<T> list, boolean isClaearList) {
    if (this.list != null) {
      if (isClaearList) {
        clearItems();
      }
      this.list = list;
      notifyDataSetChanged();
    }
  }

  public void addItems(@NonNull List<T> addItems) {
    if (list != null) {
      final int startPos = list.size();
      list.addAll(addItems);
      notifyItemRangeInserted(startPos, addItems.size());
    }
  }

  public void addItem(@NonNull T addItem) {
    if (list != null) {
      list.add(addItem);
      notifyItemInserted(list.size());
    }
  }

  public void addItem(int position, @NonNull T insertItem) {
    if (list != null) {
      list.add(position, insertItem);
      notifyItemRangeInserted(position, position + 1);
    }
  }

  public T removeItem(int position) {
    if (list != null) {
      if (position >= 0 && position < list.size()) {
        T removedItem = list.remove(position);
        notifyItemRemoved(position);
        return removedItem;
      }
    }
    return null;
  }

  public T removeLast() {
    return removeItem(list != null ? list.size() - 1 : -1);
  }

  public void replaceItem(int position, T replaceItem) {
    if (list != null) {
      if (position >= 0 && position < list.size()) {
        if (list.set(position, replaceItem) != null) {
          notifyItemRemoved(position);
        }
      }
    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(createView(context, parent, viewType), tagObj, clickListener);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    final int viewType = getItemViewType(position);
    bindView(viewType, getItem(position), holder);
  }

  @Override
  public int getItemCount() {
    return (list != null ? list.size() : 0);
  }

  public T getItem(@IntRange(from = 0) int position) {
    return ((list != null && position < list.size()) ? list.get(position) : null);
  }

  public boolean isEmptyList() {
    return (list == null || list.isEmpty());
  }

  public static class ViewHolder
    extends RecyclerView.ViewHolder
    implements View.OnClickListener {
    private Map<Integer, View>  views;
    private OnViewClickListener clickListener;
    private Object              tag;

    public ViewHolder(View view, Object tag, OnViewClickListener clickListener) {
      super(view);
      this.clickListener = clickListener;
      this.tag = tag;
      if (this.clickListener != null) {
        view.setOnClickListener(this);
      }
      views = new HashMap<>();
      // insert RootView
      views.put(0, view);
    }

    public Object getTag() {
      return tag;
    }

    public View findViewById(@IdRes int id) {
      // 헷갈려서 넣음.
      return getView(id);
    }

    public View getView(@IdRes int id) {
      if (!views.containsKey(id)) {
        initViewById(id);
      }
      return views.get(id);
    }

    public void initViewById(@IdRes int id) {
      // get RootView
      View view = (getView(0) != null ? getView(0).findViewById(id) : null);
      if (view != null) {
        if (view.isClickable()) {
          view.setOnClickListener(this);
        }
        views.put(id, view);
      }
    }

    @Override
    public void onClick(View v) {
      if (clickListener != null) {
        clickListener.onClicked(this, v, getAdapterPosition());
      }
    }

  }// end of ViewHolder class

}
