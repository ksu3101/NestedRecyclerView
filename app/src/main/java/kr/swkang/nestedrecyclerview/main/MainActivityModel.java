package kr.swkang.nestedrecyclerview.main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodyContents;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.HeaderContents;
import kr.swkang.nestedrecyclerview.utils.mvp.BaseModel;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public class MainActivityModel
    extends BaseModel {

  public void retrieveListDatas(Subscriber<ArrayList<Contents>> subscriber, final boolean isLoadMore) {
    Observable<ArrayList<Contents>> observable = Observable.create(
        new Observable.OnSubscribe<ArrayList<Contents>>() {
          @Override
          public void call(Subscriber<? super ArrayList<Contents>> subscriber) {
            ArrayList<Contents> result = new ArrayList<>();

            if (isLoadMore) {
              // add dummy body datas
              for (int i = 1; i <= 4; i++) {
                result.add(new BodyContents(BodyContents.HALF_VIEWTYPE_VALUE, "NEW " + i));
              }
            }
            else {
              // reset

              // header
              result.add(new HeaderContents());

              // add default 2 sections (span 2)
              for (int i = 0; i < 2; i++) {
                result.add(new BodyContents(BodyContents.FULL_VIEWTYPE_VALUE, "SPAN 2 BODY " + i));
              }

              for (int i = 1; i <= 4; i++) {
                result.add(new BodyContents(BodyContents.HALF_VIEWTYPE_VALUE, String.valueOf(i)));
              }
            }

            subscriber.onNext(result);
          }
        }
    );
    observable.delay(1500, TimeUnit.MILLISECONDS)
              .subscribeOn(Schedulers.computation())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(subscriber);
  }

}
