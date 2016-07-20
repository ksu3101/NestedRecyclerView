package kr.swkang.nestedrecyclerview.utils;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author KangSung-Woo
 * @since 2016/07/11
 */
public interface OnViewClickListener {
  void onClick(@NonNull View v, int position);
}
