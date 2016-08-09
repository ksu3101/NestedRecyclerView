package kr.swkang.nestedrecyclerview.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.utils.BaseActivity;
import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;

/**
 * @author KangSung-Woo
 * @since 2016/08/09
 */
public class DetailActivity
    extends BaseActivity
    implements DetailActivityPresenter.View {
  private static final String TAG = DetailActivity.class.getSimpleName();

  public static final String BUNDLE_KEY_CONTENTS_THUMBNAIL = TAG + "_BUNDLE_KEY_CONTENTS_THUMBNAIL";
  public static final String BUNDLE_KEY_CONTENTS_TITLE     = TAG + "_BUNDLE_KEY_CONTENTS_TITLE";
  public static final String BUNDLE_KEY_CONTENTS_CATEGORY  = TAG + "_BUNDLE_KEY_CONTENTS_CATEGORY";

  private DetailActivityPresenter presenter;

  @Override
  public BasePresenter attachPresenter() {
    presenter = new DetailActivityPresenter();
    return presenter;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.detail_activity);

    Intent intent = getIntent();

    final String imgThumbnail = intent.getStringExtra(BUNDLE_KEY_CONTENTS_THUMBNAIL);
    final String title = intent.getStringExtra(BUNDLE_KEY_CONTENTS_TITLE);
    final String category = intent.getStringExtra(BUNDLE_KEY_CONTENTS_CATEGORY);

    TextView tvTitle = (TextView) findViewById(R.id.detail_tv_title);
    tvTitle.setText(title != null ? title : "");

    TextView tvCategory = (TextView) findViewById(R.id.detail_tv_category);
    tvCategory.setText(category != null ? category : "");

    ImageView iv = (ImageView) findViewById(R.id.detail_iv);
    if (imgThumbnail != null) {
      Picasso.with(this)
             .load(imgThumbnail)
             .fit()
             .centerCrop()
             .into(iv);
    }

  }

}
