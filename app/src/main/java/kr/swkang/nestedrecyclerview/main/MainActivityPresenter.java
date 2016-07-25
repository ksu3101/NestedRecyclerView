package kr.swkang.nestedrecyclerview.main;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodySection;
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

  public MainActivityPresenter(@NonNull MainActivity activity) {
    this.activity = activity;
    this.view = activity;
    this.model = new MainActivityModel();
  }

  public void retrieveMainListDatas(final boolean isLoadMore) {
    if (model != null) {
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
    }
  }

  interface View
      extends BaseView {
    void onRetriveMainListItems(@NonNull List<Contents> list, boolean isLoadMore);
  }

}