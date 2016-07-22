package kr.swkang.nestedrecyclerview.main.list.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author KangSung-Wo1o
 * @since 2016/07/20
 */
public class Contents
    implements Parcelable {
  protected ContentsType contentType;

  public Contents(ContentsType contentType) {
    this.contentType = contentType;
  }

  public ContentsType getContentType() {
    return contentType;
  }

  public void setContentType(ContentsType contentType) {
    this.contentType = contentType;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(contentType.getValue());
  }

  protected Contents(Parcel in) {
    this.contentType = ContentsType.parseFromValue(in.readInt());
  }

}
