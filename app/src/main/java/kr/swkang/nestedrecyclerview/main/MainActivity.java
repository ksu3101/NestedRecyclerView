package kr.swkang.nestedrecyclerview.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.main.list.MainRvAdapter;
import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodyContents;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.HeaderContents;
import kr.swkang.nestedrecyclerview.utils.BaseActivity;
import kr.swkang.nestedrecyclerview.utils.OnViewClickListener;
import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;

public class MainActivity
    extends BaseActivity
    implements MainActivityPresenter.View, OnViewClickListener {
  private static final String TAG = MainActivity.class.getSimpleName();

  private MainActivityPresenter presenter;
  private SwipeRefreshLayout    refreshLayout;
  private RecyclerView          rv;
  private MainRvAdapter         adapter;

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
              presenter.retrieveListDatas(false);
            }
          }
        }
    );

    rv = (RecyclerView) findViewById(R.id.main_recyclerview);
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
              else if (viewType == BodyContents.FULL_VIEWTYPE_VALUE) {
                // BODY / span 2
                return 2;
              }
              else if (viewType == BodyContents.HALF_VIEWTYPE_VALUE) {
                // BODY / span 1
                return 1;
              }
              else {
                // FOOTER
                return 2;
              }
            }
            // default 2
            return 2;
          }
        }
    );

    rv.setLayoutManager(gridLayoutManager);

    ArrayList<Contents> datas = new ArrayList<>();
    adapter = new MainRvAdapter(this, datas, this);
    rv.setAdapter(adapter);

    // retrieve list datas
    presenter.retrieveListDatas(false);

  }

  @Override
  public void onRetriveListItems(@NonNull List<Contents> list, boolean isLoadMore) {
    Log.d(TAG, "onRetriveListItems() // " + (isLoadMore ? "IsLoadMore" : "Refresh") + " // list.size() = " + list.size());
    if (adapter != null) {
      if (isLoadMore) {
        adapter.addItems(list);
      }
      else {
        Toast.makeText(MainActivity.this, "Refresh Complete", Toast.LENGTH_SHORT).show();
        adapter.setItem(list, true);
      }
    }
    if (refreshLayout != null) {
      refreshLayout.setRefreshing(false);
    }
  }


  @Override
  public void onClick(@NonNull View v, int position) {

  }


}
