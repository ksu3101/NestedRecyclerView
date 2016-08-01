package kr.swkang.nestedrecyclerview.main.list.model;

import android.os.Parcel;

/**
 * @author KangSung-Woo
 * @since 2016/07/22
 */
public class SectionHeader
    extends Contents {
  public static final int                    VIEWTYPE_VALUE = 999;
  public static final Creator<SectionHeader> CREATOR        = new Creator<SectionHeader>() {
    @Override
    public SectionHeader createFromParcel(Parcel source) {
      return new SectionHeader(source);
    }

    @Override
    public SectionHeader[] newArray(int size) {
      return new SectionHeader[size];
    }
  };
  private String title;

  public SectionHeader(String title) {
    super(ContentsType.SECTION_HEADER);
    this.title = title;
  }

  protected SectionHeader(Parcel in) {
    super(in);
    this.title = in.readString();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.title);
  }
}

