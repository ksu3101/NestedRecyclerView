package kr.swkang.nestedrecyclerview.main.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.main.header.HeaderViewPagerAdapter;
import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.ContentsType;
import kr.swkang.nestedrecyclerview.main.list.data.SectionHeader;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodyItems;
import kr.swkang.nestedrecyclerview.main.list.data.subcontents.BodySection;
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

  private FragmentManager        fm;
  private SubHorRvItemDecoration subHorRvItemDecoration;

  public MainRvAdapter(@NonNull Context context, @NonNull FragmentManager fm, @NonNull ArrayList<Contents> list, OnViewClickListener clickListener) {
    super(context, list, clickListener);
    this.fm = fm;
    this.subHorRvItemDecoration = new SubHorRvItemDecoration(context);
  }

  @Override
  protected View createView(Context context, ViewGroup viewGroup, int viewType) {
    if (viewType == HeaderContents.VIEWTYPE_VALUE) {
      // 0 == Header contents
      return LayoutInflater.from(context).inflate(R.layout.main_item_header, viewGroup, false);
    }
    else if (viewType == BodySection.FULL_VIEWTYPE_VALUE) {
      // BODY contents (Span 2)
      return LayoutInflater.from(context).inflate(R.layout.main_item_body_full, viewGroup, false);
    }
    else if (viewType == BodySection.HALF_VIEWTYPE_VALUE) {
      // BODY contents (Span 1)
      return LayoutInflater.from(context).inflate(R.layout.main_item_body_half, viewGroup, false);
    }
    else if (viewType == FOOTER_LOADMORE) {
      // list.size() + 1 == Footer contents
      return LayoutInflater.from(context).inflate(R.layout.main_item_footer, viewGroup, false);
    }
    else {
      // Section Headers
      return LayoutInflater.from(context).inflate(R.layout.main_item_section_header, viewGroup, false);
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

    else if (viewType == BodySection.FULL_VIEWTYPE_VALUE || viewType == BodySection.HALF_VIEWTYPE_VALUE) {
      // BODYs
      if (item instanceof BodySection) {
        BodySection bodyItem = (BodySection) item;

        RecyclerView rv = (RecyclerView) viewHolder.getView(R.id.main_item_body_rv_horizontal);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rv.removeItemDecoration(subHorRvItemDecoration);
        rv.addItemDecoration(subHorRvItemDecoration);

        SectionRvAdapter adapter = new SectionRvAdapter(context, bodyItem.getBodyItemses());
        rv.setAdapter(adapter);
      }
      else {
        BodyItems bodyItems = (BodyItems) item;

        // Span 1 size Bodys.
        TextView tvTitle = (TextView) viewHolder.getView(R.id.main_item_h_section_tv_title);
        tvTitle.setText(bodyItems.getTitle() != null ? bodyItems.getTitle() : "");

        ImageView ivBg = (ImageView) viewHolder.getView(R.id.main_item_h_section_bg_iv);
        if (!TextUtils.isEmpty(bodyItems.getThumbnailImgUrl())) {
          Picasso.with(context)
                 .load(bodyItems.getThumbnailImgUrl())
                 .fit()
                 .centerCrop()
                 .into(ivBg);
        }

        TextView tvDesc = (TextView) viewHolder.getView(R.id.main_item_h_section_tv_desc);
        tvDesc.setText(bodyItems.getDesc() != null ? bodyItems.getDesc() : "");

      }
    }

    else if (viewType == FOOTER_LOADMORE) {
      // FOOTER -> Load more (Contents item is Null)
    }

    else {
      // Section Header
      SectionHeader sectionHeader = (SectionHeader) item;

      TextView tvHeader = (TextView) viewHolder.getView(R.id.main_item_sh_tv_title);
      tvHeader.setText(sectionHeader.getTitle() != null ? sectionHeader.getTitle() : "");
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
    else if (getItem(position) instanceof BodySection) {
      return BodySection.FULL_VIEWTYPE_VALUE;
    }
    else if (getItem(position) instanceof BodyItems) {
      return BodySection.HALF_VIEWTYPE_VALUE;
    }
    return SectionHeader.VIEWTYPE_VALUE;
  }

  public int getFirstHalfBodyContentsPosition() {
    for (int i = 0; i < getItemCount(); i++) {
      if (getItem(i).getContentType() == ContentsType.BODY_HALF) {
        return i;
      }
    }
    return -1;
  }

  public void showFooter() {

  }

  public void hideFooter() {

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
