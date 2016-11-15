package kr.swkang.nestedrecyclerview.utils.common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;

/**
 * @author Kang-SungWoo
 * @since 2016-11-15
 */

public abstract class BaseFragment
    extends Fragment {
  private BasePresenter basePresenter;
  private Unbinder      unbinder;

  // - - Abstract methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @Nullable
  public abstract BasePresenter attachPresenter();

  @LayoutRes
  public abstract int getLayoutResId();

  // - - Life cycle methods  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutResId(), container, false);
    unbinder = ButterKnife.bind(this, view);
    this.basePresenter = attachPresenter();
    return view;
  }

  @Override
  public void onDestroy() {
    if (unbinder != null) {
      unbinder.unbind();
    }
    if (basePresenter != null) {
      basePresenter.destroy();
    }
    super.onDestroy();
  }

}
