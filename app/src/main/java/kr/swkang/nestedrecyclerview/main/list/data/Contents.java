package kr.swkang.nestedrecyclerview.main.list.data;

/**
 * @author KangSung-Wo1o
 * @since 2016/07/20
 */
public class Contents {
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

}
