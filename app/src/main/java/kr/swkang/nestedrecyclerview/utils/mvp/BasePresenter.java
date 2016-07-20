package kr.swkang.nestedrecyclerview.utils.mvp;

import android.support.annotation.NonNull;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * @author KangSung-Woo
 * @since 2016/07/07
 */
public class BasePresenter {
  private CompositeSubscription compositeSubscriptionl;

  public BasePresenter() {
    this.compositeSubscriptionl = new CompositeSubscription();
  }

  public <T> void addSubscriber(@NonNull Subscriber<T> subscriber) {
    if (compositeSubscriptionl != null) {
      compositeSubscriptionl.add(subscriber);
    }
  }

  public void destroy() {
    if (compositeSubscriptionl != null) {
      compositeSubscriptionl.unsubscribe();
    }
  }

}
