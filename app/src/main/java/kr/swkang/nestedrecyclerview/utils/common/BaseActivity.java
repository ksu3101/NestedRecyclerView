package kr.swkang.nestedrecyclerview.utils.common;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.Map;

import butterknife.ButterKnife;
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

  @Nullable
  public abstract BasePresenter attachPresenter();

  @LayoutRes
  public abstract int getLayoutResId();

  // - - Life cycle methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(getLayoutResId());
    ButterKnife.bind(this);
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

  @CallSuper
  @Override
  public void onError(String tag, Throwable t) {
    onError(tag, t != null ? t.getMessage() : "ERROR");
  }

  // - - Common methods - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  public final void startActivity_DetailContents(
      @NonNull View sharedTransitionImageView, String imgUrl, String title, String category) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_THUMBNAIL, imgUrl);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_TITLE, title);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_CATEGORY, category);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      ActivityOptionsCompat options =
          ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedTransitionImageView, getString(R.string.transition_name_thumbnail));
      ActivityCompat.startActivity(this, intent, options.toBundle());
    }
    else {
      startActivity(intent);
    }
  }

  @Deprecated
  @SuppressWarnings("unchecked")
  public final void startActivity_DetailContents(
      @NonNull View sharedTransitionImageView, String imgUrl,
      @NonNull View sharedTransitionTitleTextView, String title,
      View sharedTransitionCategoryTextView, String category) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_THUMBNAIL, imgUrl);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_TITLE, title);
    intent.putExtra(DetailActivity.BUNDLE_KEY_CONTENTS_CATEGORY, category);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      ActivityOptionsCompat options;
      if (sharedTransitionCategoryTextView != null && TextUtils.isEmpty(category)) {
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            new Pair<View, String>(sharedTransitionImageView, getString(R.string.transition_name_thumbnail)),
            new Pair<View, String>(sharedTransitionTitleTextView, getString(R.string.transition_name_title)),
            new Pair<View, String>(sharedTransitionCategoryTextView, getString(R.string.transition_name_category))
        );
      }
      else {
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            new Pair<View, String>(sharedTransitionImageView, getString(R.string.transition_name_thumbnail)),
            new Pair<View, String>(sharedTransitionTitleTextView, getString(R.string.transition_name_title))
        );
      }
      ActivityCompat.startActivity(this, intent, options.toBundle());
    }
    else {
      startActivity(intent);
    }
  }


}
