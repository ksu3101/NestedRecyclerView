package kr.swkang.nestedrecyclerview.main.header;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.main.MainActivity;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.HeaderContentsItem;
import kr.swkang.nestedrecyclerview.utils.common.BaseFragment;
import kr.swkang.nestedrecyclerview.utils.mvp.BasePresenter;

/**
 * @author KangSung-Woo
 * @since 2016/07/21
 */
public class HeaderFragment
    extends BaseFragment {
  public static final String TAG                = HeaderFragment.class.getSimpleName();
  public static final String BUNDLE_HEADER_ITEM = TAG + "_BUNDLE_HEADER_ITEM";

  @BindView(R.id.main_item_h_vp_child_iv) ImageView imageView;

  public static HeaderFragment newInstance(Bundle args) {
    HeaderFragment fragment = new HeaderFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public BasePresenter attachPresenter() {
    return null;
  }

  @Override
  public int getLayoutResId() {
    return R.layout.main_item_header_vp_childfragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    HeaderContentsItem item = getArguments().getParcelable(BUNDLE_HEADER_ITEM);
    if (item != null) {
      if (!TextUtils.isEmpty(item.getImageUrl())) {
        Picasso.with(getContext())
               .load(item.getImageUrl())
               .fit()
               .centerCrop()
               .into(imageView);
      }
    }

  }

}
