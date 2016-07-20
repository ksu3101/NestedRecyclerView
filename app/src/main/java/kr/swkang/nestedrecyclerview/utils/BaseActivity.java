package kr.swkang.nestedrecyclerview.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
      basePresenter.destroy();
    }
    super.onDestroy();
  }

  @Override
  public void onError(String message) {
    Log.e("BaseActivity", message);
  }

}
