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

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.main.MainActivity;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.HeaderContentsItem;

/**
 * @author KangSung-Woo
 * @since 2016/07/21
 */
public class HeaderFragment
    extends Fragment {
  public static final String TAG                = HeaderFragment.class.getSimpleName();
  public static final String BUNDLE_HEADER_ITEM = TAG + "_BUNDLE_HEADER_ITEM";

  private MainActivity parentActivity;
  private View         rootView;

  public static HeaderFragment newInstance(Bundle args) {
    HeaderFragment fragment = new HeaderFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Activity activity) {
    if (activity != null && activity instanceof MainActivity) {
      this.parentActivity = (MainActivity) activity;
    }
    super.onAttach(activity);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.main_item_header_vp_childfragment, container, false);
    return rootView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    ImageView iv = (ImageView) rootView.findViewById(R.id.main_item_h_vp_child_iv);

    HeaderContentsItem item = getArguments().getParcelable(BUNDLE_HEADER_ITEM);
    if (item != null) {
      if (!TextUtils.isEmpty(item.getImageUrl())) {
        Picasso.with(getContext())
               .load(item.getImageUrl())
               .fit()
               .centerCrop()
               .into(iv);
      }
    }

  }

}
