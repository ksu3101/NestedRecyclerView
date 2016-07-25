package kr.swkang.nestedrecyclerview.main;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;
import kr.swkang.nestedrecyclerview.utils.mvp.BaseView;
import rx.Subscriber;

/**
 * @author KnagSung-Woo
 * @since 2016/07/20
 */
public class MainActivityPresenter
    extends BasePresenter {

  private MainActivity      activity;
  private MainActivityModel model;
  private View              view;
  private boolean           isLoading;

  public MainActivityPresenter(@NonNull MainActivity activity) {
    this.activity = activity;
    this.view = activity;
    this.model = new MainActivityModel();
    this.isLoading = false;
  }

  public boolean isLoading() {
    return isLoading;
  }

  public void setLoading(boolean loading) {
    isLoading = loading;
  }

  public void retrieveMainListDatas(final boolean isLoadMore) {
    if (model != null) {
      if (!isLoading) {
        final Subscriber subscriber = new Subscriber<ArrayList<Contents>>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
            if (view != null) {
              view.onError(e != null ? e.getMessage() : "ERROR");
            }
          }

          @Override
          public void onNext(ArrayList<Contents> resultList) {
            if (view != null) {
              view.onRetriveMainListItems(resultList, isLoadMore);
            }
          }
        };
        model.retrieveMainListDatas(subscriber, isLoadMore);
        addSubscriber(subscriber);
        isLoading = true;
      }
      else {
        Log.w(MainActivityPresenter.class.getSimpleName(), "// plz wait. is loading..");
      }
    }
  }

  interface View
      extends BaseView {
    void onRetriveMainListItems(@NonNull List<Contents> list, boolean isLoadMore);
  }

}