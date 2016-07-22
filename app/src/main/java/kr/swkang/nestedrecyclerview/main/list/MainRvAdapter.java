package kr.swkang.nestedrecyclerview.main.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.main.header.HeaderViewPagerAdapter;
import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.ContentsType;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodyContents;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.HeaderContents;
import kr.swkang.nestedrecyclerview.utils.OnViewClickListener;
import kr.swkang.nestedrecyclerview.utils.SwRecyclerViewAdapter;
import kr.swkang.nestedrecyclerview.utils.viewpagers.InfinitePagerAdapter;
import kr.swkang.nestedrecyclerview.utils.viewpagers.InfiniteViewPager;
import kr.swkang.nestedrecyclerview.utils.viewpagers.pagerindicator.ViewPagerIndicator;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public class MainRvAdapter
    extends SwRecyclerViewAdapter<Contents> {
  public static final int FOOTER_LOADMORE = 99;

  private FragmentManager fm;

  public MainRvAdapter(@NonNull Context context, @NonNull FragmentManager fm, @NonNull ArrayList<Contents> list, OnViewClickListener clickListener) {
    super(context, list, clickListener);
    this.fm = fm;
  }

  @Override
  protected View createView(Context context, ViewGroup viewGroup, int viewType) {
    if (viewType == HeaderContents.VIEWTYPE_VALUE) {
      // 0 == Header contents
      return LayoutInflater.from(context).inflate(R.layout.main_item_header, viewGroup, false);
    }
    else if (viewType == BodyContents.FULL_VIEWTYPE_VALUE) {
      // BODY contents (Span 2)
      return LayoutInflater.from(context).inflate(R.layout.main_item_body_full, viewGroup, false);
    }
    else if (viewType == BodyContents.HALF_VIEWTYPE_VALUE) {
      // BODY contents (Span 1)
      return LayoutInflater.from(context).inflate(R.layout.main_item_body_half, viewGroup, false);
    }
    else {
      // list.size() + 1 == Footer contents
      return LayoutInflater.from(context).inflate(R.layout.main_item_footer, viewGroup, false);
    }
  }

  @Override
  protected void bindView(int viewType, Contents item, ViewHolder viewHolder) {
    if (viewType == HeaderContents.VIEWTYPE_VALUE) {
      if (item instanceof HeaderContents) {
        HeaderContents contents = (HeaderContents) item;

        InfiniteViewPager vp = (InfiniteViewPager) viewHolder.getView(R.id.main_item_header_viewpager);
        InfinitePagerAdapter wrrappedAdapter = new InfinitePagerAdapter(new HeaderViewPagerAdapter(fm, contents.getItemList()));
        vp.setAdapter(wrrappedAdapter);
        vp.setTag(wrrappedAdapter);

        ViewPagerIndicator indicator = (ViewPagerIndicator) viewHolder.getView(R.id.main_item_header_vp_indicator);
        indicator.setCircleMarginDP(6);
        indicator.setViewPager(vp, wrrappedAdapter.getRealCount());

      }
    }

    else if (viewType == BodyContents.FULL_VIEWTYPE_VALUE || viewType == BodyContents.HALF_VIEWTYPE_VALUE) {
      // BODYs
      if (item instanceof BodyContents) {
        BodyContents contents = (BodyContents) item;

        TextView tv = (TextView) viewHolder.getView(R.id.main_item_body_tv_section);
        tv.setText(contents.getSectionHeader() != null ? contents.getSectionHeader() : "");

        if (viewType == BodyContents.FULL_VIEWTYPE_VALUE) {
          RecyclerView rv = (RecyclerView) viewHolder.getView(R.id.main_item_body_rv_horizontal);
          rv.setHasFixedSize(true);
          rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

          SectionRvAdapter adapter = new SectionRvAdapter(context, contents.getBodyContentsItems());
          rv.setAdapter(adapter);

        }
        else {
          //

        }
      }
    }

    else {
      // FOOTER -> Load more (Contents item is Null)
    }
  } // end of bindView() methods

  @Override
  public int getItemCount() {
    int count = super.getItemCount();
    return (count > 0 ? ++count : count); // list가 비어있지 않을 경우 footer를 붙임.
  }

  @Override
  public int getItemViewType(int position) {
    if (isHeader(position)) {
      return HeaderContents.VIEWTYPE_VALUE;
    }
    else if (isFooter(position)) {
      return FOOTER_LOADMORE;
    }
    return getBodyViewType(position);
  }

  public int getBodyViewType(int position) {
    return (!isEmptyList() ? getItem(position).getContentType().getValue() : BodyContents.FULL_VIEWTYPE_VALUE);
  }

  public boolean hasHeader() {
    return (!isEmptyList() && getItem(0).getContentType() == ContentsType.HEADER);
  }

  public boolean isHeader(int position) {
    return (!isEmptyList() && position == 0 && getItem(position).getContentType() == ContentsType.HEADER);
  }

  public boolean isFooter(int position) {
    return (!isEmptyList() && position == getFooterPosition());
  }

  public int getFooterPosition() {
    return (isEmptyList() ? 0 : list.size());
  }

}
