package kr.swkang.nestedrecyclerview.main.header;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import kr.swkang.nestedrecyclerview.main.list.model.subcontents.HeaderContentsItem;

/**
 * @author KangSung-Woo
 * @since 2016/07/21
 */
public class HeaderViewPagerAdapter
    extends FragmentPagerAdapter {

  private List<HeaderContentsItem> list;

  public HeaderViewPagerAdapter(FragmentManager fm, List<HeaderContentsItem> list) {
    super(fm);
    this.list = new ArrayList<>();
    this.list = list;
  }

  @Override
  public int getCount() {
    return (list != null ? list.size() : 0);
  }

  @Override
  public Fragment getItem(int position) {
    Bundle args = new Bundle();
    args.putParcelable(HeaderFragment.BUNDLE_HEADER_ITEM, list.get(position));
    return HeaderFragment.newInstance(args);
  }

}
