package kr.swkang.nestedrecyclerview.main.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kr.swkang.nestedrecyclerview.R;
import kr.swkang.nestedrecyclerview.main.list.model.subcontents.BodyItems;
import kr.swkang.nestedrecyclerview.utils.widgets.OnViewClickListener;
import kr.swkang.nestedrecyclerview.utils.widgets.rvs.SwRecyclerViewAdapter;

/**
 * Span 2 section / RecyclerView adapter
 *
 * @author KangSung-Woo
 * @since 2016/07/22
 */
public class SectionRvAdapter
    extends SwRecyclerViewAdapter<BodyItems> {
  public static final String TAG = SectionRvAdapter.class.getSimpleName();

  public SectionRvAdapter(@NonNull Context context, @NonNull List<BodyItems> list, @NonNull OnViewClickListener clickListener) {
    super(context, list, TAG, clickListener);
  }

  @Override
  protected View createView(Context context, ViewGroup viewGroup, int viewType) {
    return LayoutInflater.from(context).inflate(R.layout.main_item_body_card, viewGroup, false);
  }

  @Override
  protected void bindView(int viewType, BodyItems item, ViewHolder viewHolder) {
    if (item != null) {
      ImageView ivBg = (ImageView) viewHolder.getView(R.id.main_item_f_section_bg_iv);
      final String imgUrl = item.getThumbnailImgUrl();
      if (!TextUtils.isEmpty(imgUrl)) {
        Picasso.with(context)
               .load(imgUrl)
               .fit()
               .centerCrop()
               .into(ivBg);
      }
      ivBg.setTag(item);

      TextView tvTitle = (TextView) viewHolder.getView(R.id.main_item_f_section_bg_tv);
      tvTitle.setText(item.getTitle() != null ? item.getTitle() : "");

    }
  }

}
