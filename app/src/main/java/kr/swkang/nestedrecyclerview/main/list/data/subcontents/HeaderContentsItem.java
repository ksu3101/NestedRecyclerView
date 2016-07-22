package kr.swkang.nestedrecyclerview.main.list.data.subcontents;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author KangSung-Woo
 * @since 2016/07/21
 */
public class HeaderContentsItem
    implements Parcelable {
  private int    position;
  private String imageUrl;
  private String linkUrl;

  public HeaderContentsItem(int position, String imageUrl, String linkUrl) {
    this.position = position;
    this.imageUrl = imageUrl;
    this.linkUrl = linkUrl;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.position);
    dest.writeString(this.imageUrl);
    dest.writeString(this.linkUrl);
  }

  protected HeaderContentsItem(Parcel in) {
    this.position = in.readInt();
    this.imageUrl = in.readString();
    this.linkUrl = in.readString();
  }

  public static final Parcelable.Creator<HeaderContentsItem> CREATOR = new Parcelable.Creator<HeaderContentsItem>() {
    @Override
    public HeaderContentsItem createFromParcel(Parcel source) {
      return new HeaderContentsItem(source);
    }

    @Override
    public HeaderContentsItem[] newArray(int size) {
      return new HeaderContentsItem[size];
    }
  };
}
