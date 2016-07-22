package kr.swkang.nestedrecyclerview.main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodyContents;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodyContentsItem;
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

  private static String[] imgs = new String[]{
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

  private static String[] txts = new String[]{
      "Lose", "away", "offset", "가나다라", "father", "API demo", "Shine on you",
      "Kids", "Number one", "Plastic", "Bawlings", "Dsco", "Soda pop confusion",
      "습관", "겨울은 가고", "내 손을 잡아줘", "ordinary joe", "safe and sound",
      "Lightning", "Refeeling", "Romance", "헤픈엔딩",
      "Lose", "away", "offset", "가나다라", "father", "API demo", "Shine on you",
      "Kids", "Number one", "Plastic", "Bawlings", "Dsco", "Soda pop confusion",
  };

  public void retrieveMainListDatas(Subscriber<ArrayList<Contents>> subscriber, final boolean isLoadMore) {
    Observable<ArrayList<Contents>> observable = Observable.create(
        new Observable.OnSubscribe<ArrayList<Contents>>() {
          @Override
          public void call(Subscriber<? super ArrayList<Contents>> subscriber) {
            ArrayList<Contents> result = new ArrayList<>();

            if (isLoadMore) {
              // add dummy body datas
              for (int i = 1; i <= 2; i++) {
                result.add(new BodyContents(BodyContents.HALF_VIEWTYPE_VALUE, "NEW " + i));
              }
            }
            else {
              // reset

              // header
              result.add(new HeaderContents());

              // add default 2 sections (span 2)
              for (int i = 0; i < 2; i++) {
                result.add(new BodyContents(BodyContents.FULL_VIEWTYPE_VALUE, "" + i));
              }

              // add items (span 1)
              for (int i = 1; i <= 8; i++) {
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

  public void retrieveSectionListDatas(Subscriber<ArrayList<BodyContents>> subscriber) {
    Observable<ArrayList<BodyContents>> observable = Observable.create(
        new Observable.OnSubscribe<ArrayList<BodyContents>>() {
          @Override
          public void call(Subscriber<? super ArrayList<BodyContents>> subscriber) {
            ArrayList<BodyContents> result = new ArrayList<>();

            ArrayList<BodyContentsItem> firstSectionResult = new ArrayList<>();
            // [1] get dummy datas
            for (int i = 0; i < imgs.length; i++) {
              firstSectionResult.add(new BodyContentsItem(
                  i + 1, imgs[i], txts[i], ""));
            }
            BodyContents firstSection = new BodyContents(BodyContents.FULL_VIEWTYPE_VALUE, "Section 1");
            firstSection.setBodyContentsItems(firstSectionResult);
            result.add(firstSection);

            ArrayList<BodyContentsItem> secondSectionResult = new ArrayList<>();
            // [2] get dummy datas
            for (int i = 0; i < imgs.length; i++) {
              secondSectionResult.add(new BodyContentsItem(
                  i + 1, imgs[Math.abs(imgs.length - i - 1)], txts[Math.abs(imgs.length - i - 1)], ""));
            }
            BodyContents secondSection = new BodyContents(BodyContents.FULL_VIEWTYPE_VALUE, "Section 2");
            secondSection.setBodyContentsItems(secondSectionResult);
            result.add(secondSection);

            subscriber.onNext(result);
          }
        }
    );
    observable.subscribeOn(Schedulers.computation())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(subscriber);
  }

}
