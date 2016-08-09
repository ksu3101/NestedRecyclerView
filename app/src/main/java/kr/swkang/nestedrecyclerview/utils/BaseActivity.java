package kr.swkang.nestedrecyclerview.utils;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Map;

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.detail.DetailActivity;
import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;
import kr.swkang.nestedrecyclerview.utils.mvp.BaseView;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public abstract class BaseActivity
    extends AppCompatActivity
    implements BaseView {
  private BasePresenter basePresenter;

  // - - Abstract methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  public abstract BasePresenter attachPresenter();

  // - - Life cycle methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.basePresenter = attachPresenter();
  }

  @Override
  protected void onDestroy() {
    if (basePresenter != null) {
      // unscribe registered Subscriptions
      basePresenter.destroy();
    }
    super.onDestroy();
  }

  // - - Implements methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @CallSuper
  @Override
  public void onError(String tag, String message) {
    Log.e(tag != null ? tag : "BaseActivity", message != null ? message : "Message is null.");
  }

  // - - Common methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @SuppressWarnings("unchecked")
  public final void startActivity_DetailContents(
      @NonNull View sharedTransitionImageView, String imgUrl,
      @NonNull View sharedTransitionTitleTextView, String title,
      @NonNull View sharedTransitionCategoryTextView, String category) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_THUMBNAIL, imgUrl);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_TITLE, title);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_CATEGORY, category);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      ActivityOptionsCompat options =
          ActivityOptionsCompat.makeSceneTransitionAnimation(
              this,
              new Pair<View, String>(sharedTransitionImageView, getString(R.string.transition_name_thumbnail)),
              new Pair<View, String>(sharedTransitionTitleTextView, getString(R.string.transition_name_title)),
              new Pair<View, String>(sharedTransitionCategoryTextView, getString(R.string.transition_name_category))
          );
      ActivityCompat.startActivity(this, intent, options.toBundle());
    }
    else {
      startActivity(intent);
    }
  }


}
