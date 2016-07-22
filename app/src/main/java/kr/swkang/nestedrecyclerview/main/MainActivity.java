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
import kr.swkang.nestedrecyclerview.main.list.data.ContentsType;
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
              presenter.retrieveMainListDatas(false);
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
    adapter = new MainRvAdapter(this, getSupportFragmentManager(), datas, this);
    rv.setAdapter(adapter);

    // retrieve list datas
    presenter.retrieveMainListDatas(false);

  }


  @Override
  public void onRetriveMainListItems(@NonNull List<Contents> list, boolean isLoadMore) {
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
  public void onRetriveSectionListDatas(@NonNull ArrayList<BodyContents> bodyList) {
    Log.w(TAG, "// onRetriveSectionListDatas() // bodyList size() = " + bodyList.size());
    if (adapter != null) {
      for (int i = 1; i <= bodyList.size(); i++) {
        if (i - 1 < adapter.getItemCount()) {
          BodyContents c = bodyList.get(i - 1);
          Log.w(TAG, "// onRetriveSectionListDatas() // [" + i + "] " + c.getBodyContentsItems().size() + ", " + c.getContentType().name());
          if (c.getContentType() == ContentsType.BODY_FULL) {
            // 이미 추가된 포지션에 Full body일 경우 -> replace
            adapter.replaceItem(i, c);
          }
          else {
            // 새로운 body추가
            adapter.addItem(i, bodyList.get(i - 1));
          }
        }
      }
    }
  }

  @Override
  public void onClick(@NonNull View v, int position) {
    Toast.makeText(MainActivity.this, "Touched Item [" + position + "]", Toast.LENGTH_SHORT).show();
  }

}
