package kr.swkang.nestedrecyclerview.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.main.list.MainRvAdapter;
import kr.swkang.nestedrecyclerview.main.list.MainRvItemDecoration;
import kr.swkang.nestedrecyclerview.main.list.SectionRvAdapter;
import kr.swkang.nestedrecyclerview.main.list.model.Contents;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.BodyItems;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.BodySection;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.HeaderContents;
import kr.swkang.nestedrecyclerview.utils.common.BaseActivity;
import kr.swkang.nestedrecyclerview.utils.widgets.OnViewClickListener;
import kr.swkang.nestedrecyclerview.utils.widgets.rvs.SwOnScrollListener;
import kr.swkang.nestedrecyclerview.utils.widgets.rvs.SwRecyclerView;
import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;
import kr.swkang.nestedrecyclerview.utils.widgets.rvs.SwRecyclerViewAdapter;

public class MainActivity
    extends BaseActivity
    implements MainActivityPresenter.View, OnViewClickListener {
  private static final String TAG = MainActivity.class.getSimpleName();

  private static Picasso               picasso;
  private        MainActivityPresenter presenter;
  private        SwipeRefreshLayout    refreshLayout;
  private        SwRecyclerView        rv;
  private        MainRvAdapter         adapter;

  @Override
  public BasePresenter attachPresenter() {
    this.presenter = new MainActivityPresenter(this);
    return presenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    refreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swiperefresh);
    refreshLayout.setOnRefreshListener(
        new SwipeRefreshLayout.OnRefreshListener() {
          @Override
          public void onRefresh() {
            if (presenter != null) {
              presenter.retrieveMainListDatas(false);
            }
          }
        }
    );

    rv = (SwRecyclerView) findViewById(R.id.main_recyclerview);
    rv.setHasFixedSize(false);

    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    gridLayoutManager.setSpanSizeLookup(
        new GridLayoutManager.SpanSizeLookup() {
          @Override
          public int getSpanSize(int position) {
            if (adapter != null) {
              final int viewType = adapter.getItemViewType(position);
              if (viewType == HeaderContents.VIEWTYPE_VALUE) {
                // HEADER
                return 2;
              }
              else if (viewType == BodySection.FULL_VIEWTYPE_VALUE) {
                // BODY / span 2
                return 2;
              }
              else if (viewType == BodySection.HALF_VIEWTYPE_VALUE) {
                // BODY / span 1
                return 1;
              }
              else {
                // FOOTER, SECTION HEADERS
                return 2;
              }
            }
            // default 2
            return 2;
          }
        }
    );

    rv.setLayoutManager(gridLayoutManager);
    MainRvItemDecoration itemDeco = new MainRvItemDecoration(this);
    rv.removeItemDecoration(itemDeco);
    rv.addItemDecoration(itemDeco);

    ArrayList<Contents> datas = new ArrayList<>();
    adapter = new MainRvAdapter(this, getSupportFragmentManager(), datas, this);
    rv.setAdapter(adapter);

    rv.setEmptyView(findViewById(R.id.main_emptyview_container));

    rv.addOnScrollListener(
        new SwOnScrollListener(this) {
          @Override
          public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (adapter != null && recyclerView.getLayoutManager() != null && recyclerView.getLayoutManager() instanceof GridLayoutManager) {
              GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
              if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
                // load next page..
                Log.d(TAG, "///// START LOAD MORE");
                retrieveMainListDatas(true);
              }
            }
          }
        }
    );

    TextView tvRefresh = (TextView) findViewById(R.id.main_emptyview_btn_refresh);
    tvRefresh.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            // refresh datas
            retrieveMainListDatas(false);
          }
        }
    );

    // retrieve list datas
    //retrieveMainListDatas(false);

  }

  private void retrieveMainListDatas(boolean isLoadMore) {
    if (adapter != null) {
      adapter.showLoadMore();
      presenter.retrieveMainListDatas(isLoadMore);
    }
  }

  @Override
  public void onRetriveMainListItems(@NonNull List<Contents> list, boolean isLoadMore) {
    if (adapter != null) {
      if (isLoadMore) {
        adapter.addItems(list);
      }
      else {
        //Toast.makeText(MainActivity.this, "Refresh Complete", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRetriveMainListItems() // Refresh Complete. ");
        adapter.setItem(list, true);
      }
    }
    if (refreshLayout != null) {
      refreshLayout.setRefreshing(false);
    }
  }

  @Override
  public void startLoadMore() {
    if (adapter != null) {
      adapter.showLoadMore();
    }
  }

  @Override
  public void loadMoreCompleted() {
    if (adapter != null) {
      adapter.hideLoadMore();
    }
    if (presenter != null) {
      presenter.setLoading(false);
    }
  }

  @Override
  public void onClicked(@NonNull SwRecyclerViewAdapter.ViewHolder viewHolder, int position) {
    Log.d(TAG, "// Touched Item [" + position + "]");
    if (viewHolder.getTag() != null) {
      Object viewHolderTag = viewHolder.getTag();
      if (viewHolderTag instanceof String) {
        String tagStr = (String) viewHolderTag;
        if (tagStr.equals(MainRvAdapter.TAG)) {
          if (adapter != null) {
            Contents c = adapter.getItem(position);
            if (c != null) {
              if (c instanceof BodyItems) {
                BodyItems bodyItems = (BodyItems) c;
                startActivity_DetailContents(
                    viewHolder.getView(R.id.main_item_h_section_bg_iv), bodyItems.getThumbnailImgUrl(),
                    bodyItems.getTitle(),
                    bodyItems.getDesc()
                );
                /*
                startActivity_DetailContents(
                    viewHolder.getView(R.id.main_item_h_section_bg_iv), bodyItems.getThumbnailImgUrl(),
                    viewHolder.getView(R.id.main_item_h_section_tv_title), bodyItems.getTitle(),
                    viewHolder.getView(R.id.main_item_h_section_tv_desc), bodyItems.getDesc()
                );
                */
              }
            }
          }
        }
        else if (tagStr.equals(SectionRvAdapter.TAG)) {
          ImageView ivBg = (ImageView) viewHolder.getView(R.id.main_item_f_section_bg_iv);
          if (ivBg != null) {
            Object tagObj = ivBg.getTag();

            if (tagObj != null && tagObj instanceof BodyItems) {
              BodyItems bodyItems = (BodyItems) tagObj;
              startActivity_DetailContents(
                  ivBg, bodyItems.getThumbnailImgUrl(),
                  bodyItems.getTitle(),
                  bodyItems.getDesc() != null ? bodyItems.getDesc() : ""
              );
              /*
              startActivity_DetailContents(
                  ivBg, bodyItems.getThumbnailImgUrl(),
                  viewHolder.getView(R.id.main_item_f_section_bg_tv), bodyItems.getTitle(),
                  null, null
              );
              */
            }

          }
        }
      }
    }
  }

}
