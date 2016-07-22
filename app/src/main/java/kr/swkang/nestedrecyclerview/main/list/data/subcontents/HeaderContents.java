package kr.swkang.nestedrecyclerview.main.list.data.subcontents;

import java.util.ArrayList;

import kr.swkang.nestedrecyclerview.main.list.data.Contents;
import kr.swkang.nestedrecyclerview.main.list.data.ContentsType;

/**
 * @author KangSung-Woo
 * @since 2016/07/20
 */
public class HeaderContents
    extends Contents {
  public static final int VIEWTYPE_VALUE = 0;

  private ArrayList<HeaderContentsItem> itemList;

  public HeaderContents() {
    super(ContentsType.HEADER);
    this.itemList = new ArrayList<>();

    itemList.add(new HeaderContentsItem(0, "http://burkdog.cafe24.com/wp/wp-content/uploads/2015/11/IMG_1291.jpg", ""));
    itemList.add(new HeaderContentsItem(1, "http://burkdog.cafe24.com/wp/wp-content/uploads/2015/11/PharPhotoApocalyse.jpg", ""));
    itemList.add(new HeaderContentsItem(2, "http://burkdog.cafe24.com/wp/wp-content/uploads/2015/11/IMG_1299.jpg", ""));
    itemList.add(new HeaderContentsItem(3, "http://burkdog.cafe24.com/wp/wp-content/uploads/2015/11/IMG_1302.jpg", ""));
    itemList.add(new HeaderContentsItem(4, "http://burkdog.cafe24.com/wp/wp-content/uploads/2015/11/IMG_1333.jpg", ""));
    itemList.add(new HeaderContentsItem(5, "http://burkdog.cafe24.com/wp/wp-content/uploads/2015/11/IMG_1340.jpg", ""));
  }

  public ArrayList<HeaderContentsItem> getItemList() {
    return itemList;
  }

  public void setItemList(ArrayList<HeaderContentsItem> itemList) {
    this.itemList = itemList;
  }
}
