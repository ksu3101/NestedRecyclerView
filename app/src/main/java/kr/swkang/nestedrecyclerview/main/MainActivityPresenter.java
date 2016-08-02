package kr.swkang.nestedrecyclerview.main;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import kr.swkang.nestedrecyclerview.main.list.model.Contents;
import kr.swkang.nestedrecyclerview.main.list.model.SectionHeader;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.BodyItems;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.BodySection;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.HeaderContents;
import kr.swkang.nestedrecyclerview.utils.SwObservable;
import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;
import kr.swkang.nestedrecyclerview.utils.mvp.BaseView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author KnagSung-Woo
 * @since 2016/07/20
 */
public class MainActivityPresenter
    extends BasePresenter {
  private static final String TAG = MainActivityPresenter.class.getSimpleName();

  private static String[] imgs       = new String[]{
      "http://burkdog.cafe24.com/wp/wp-content/uploads/2016/06/rxjk.jpg",
      "https://d13yacurqjgara.cloudfront.net/users/46315/screenshots/936973/shot.gif",
      "http://designmodo.com/wp-content/uploads/2013/07/Long-Shadows.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/fox-icon.jpg",
      "https://d13yacurqjgara.cloudfront.net/users/72548/screenshots/976228/flat-ui-kit.png",
      "http://designmodo.com/wp-content/uploads/2013/07/clock.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/long.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/man_of_steel_icon.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/safari_icon.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/designmodo_long_shadow.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/m.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/rubee.jpg",
      "https://d13yacurqjgara.cloudfront.net/users/46315/screenshots/936548/retina.png",
      "http://designmodo.com/wp-content/uploads/2013/07/iosicons_flat.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/Long-Shadows.jpg",
      "http://burkdog.cafe24.com/wp/wp-content/uploads/2016/06/rxjk.jpg",
      "https://d13yacurqjgara.cloudfront.net/users/46315/screenshots/936973/shot.gif",
      "http://designmodo.com/wp-content/uploads/2013/07/Long-Shadows.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/fox-icon.jpg",
      "https://d13yacurqjgara.cloudfront.net/users/72548/screenshots/976228/flat-ui-kit.png",
      "http://designmodo.com/wp-content/uploads/2013/07/clock.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/long.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/man_of_steel_icon.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/safari_icon.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/designmodo_long_shadow.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/m.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/rubee.jpg",
      "https://d13yacurqjgara.cloudfront.net/users/46315/screenshots/936548/retina.png",
      "http://designmodo.com/wp-content/uploads/2013/07/iosicons_flat.jpg",
      "http://designmodo.com/wp-content/uploads/2013/07/Long-Shadows.jpg"
  };
  private static String[] txts       = new String[]{
      "Lose", "away", "offset", "가나다라", "father", "API demo", "Shine on you",
      "Kids", "Number one", "Plastic", "Bawlings", "Dsco", "Soda pop confusion",
      "습관", "겨울은 가고", "내 손을 잡아줘", "ordinary joe", "safe and sound",
      "Lightning", "Refeeling", "Romance", "헤픈엔딩",
      "Lose", "away", "offset", "가나다라", "father", "API demo", "Shine on you",
      "Kids", "Number one", "Plastic", "Bawlings", "Dsco", "Soda pop confusion",
  };
  private static String[] categories = new String[]{
      "Game", "Movie", "Utility", "Photo", "Video", "Study", "For kids"
  };
  private MainActivity activity;
  private View         view;
  private boolean      isLoading;

  public MainActivityPresenter(@NonNull MainActivity activity) {
    this.activity = activity;
    this.view = activity;
    this.isLoading = false;
  }

  public boolean isLoading() {
    return isLoading;
  }

  public void setLoading(boolean loading) {
    isLoading = loading;
  }

  public void retrieveMainListDatas(final boolean isLoadMore) {
    if (!isLoading) {
      SwObservable observable = new SwObservable(
          this,
          Observable.create(
              new Observable.OnSubscribe<ArrayList<Contents>>() {
                @Override
                public void call(Subscriber<? super ArrayList<Contents>> subscriber) {
                  ArrayList<Contents> result = new ArrayList<>();

                  if (isLoadMore) {
                    // add dummy body datas
                    for (int i = 1; i <= 10; i++) {
                      result.add(getRandomBodyItems(i, new Random()));
                    }
                    // some running operations
                    try {
                      Thread.sleep(TimeUnit.SECONDS.toMillis(2));

                    } catch (InterruptedException ie) {
                      subscriber.onError(ie);
                    } finally {
                      subscriber.onNext(result);
                    }
                  }
                  else {
                    // refresh list items [ DUMMY DATAS ]
                    result = retrieveDummyDatas();
                    subscriber.onNext(result);
                  }
                }
              }
          ).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
      );
      observable.subscribe(
          new Subscriber<ArrayList<Contents>>() {
            @Override
            public void onCompleted() {
              isLoading = false;
              if (view != null && isLoadMore) {
                view.loadMoreCompleted();
              }
            }

            @Override
            public void onError(Throwable e) {
              isLoading = false;
              if (view != null) {
                view.onError(TAG, e != null ? e.getMessage() : "ERROR");
                if (isLoadMore) view.loadMoreCompleted();
              }
            }

            @Override
            public void onNext(ArrayList<Contents> resultList) {
              onCompleted();
              if (view != null) {
                view.onRetriveMainListItems(resultList, isLoadMore);
              }
            }
          }
      );
      isLoading = true;
      if (view != null) {
        view.startLoadMore();
      }
    }
    else {
      Log.w(MainActivityPresenter.class.getSimpleName(), "// plz wait. is loading..");
    }
  }

  private ArrayList<Contents> retrieveDummyDatas() {
    ArrayList<Contents> result = new ArrayList<>();
    Random r = new Random();

    // put header
    result.add(new HeaderContents());

    // add first section header
    result.add(new SectionHeader("Section 1"));

    // insert first section body items.
    ArrayList<BodyItems> firstSectionResult = new ArrayList<>();
    // [1] get dummy datas
    for (int i = 0; i < imgs.length; i++) {
      firstSectionResult.add(new BodyItems(
          i + 1, imgs[i], txts[i], ""));
    }
    BodySection firstSection = new BodySection();
    firstSection.setBodyItemses(firstSectionResult);
    result.add(firstSection);   // put result array list

    // add second section header
    result.add(new SectionHeader("Section 2"));

    // insert second section body items.
    ArrayList<BodyItems> secondSectionResult = new ArrayList<>();
    // [2] get dummy datas
    for (int i = 0; i < imgs.length; i++) {
      secondSectionResult.add(new BodyItems(
          i + 1, imgs[Math.abs(imgs.length - i - 1)], txts[Math.abs(imgs.length - i - 1)], ""));
    }
    BodySection secondSection = new BodySection();
    secondSection.setBodyItemses(secondSectionResult);
    result.add(secondSection);    // put result array list

    // add third section header
    result.add(new SectionHeader("Section 3"));

    // add default normal items  [SPAN 1]
    for (int i = 1; i <= 10; i++) {
      result.add(getRandomBodyItems(i, r));
    }

    return result;
  }

  private BodyItems getRandomBodyItems(int i, @NonNull Random r) {
    return new BodyItems(i, imgs[r.nextInt(imgs.length)], txts[r.nextInt(txts.length)], categories[r.nextInt(categories.length)]);
  }

  interface View
      extends BaseView {
    void onRetriveMainListItems(@NonNull List<Contents> list, boolean isLoadMore);

    void startLoadMore();

    void loadMoreCompleted();
  }

}