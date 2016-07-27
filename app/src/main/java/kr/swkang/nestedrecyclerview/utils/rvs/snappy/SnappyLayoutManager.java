package kr.swkang.nestedrecyclerview.utils.rvs.snappy;

/**
 * 참고 : http://stackoverflow.com/questions/26370289/snappy-scrolling-in-recyclerview
 *
 * @since 2016/07/27
 */
public interface SnappyLayoutManager {
  /**
   * @return the resultant position from a fling of the given velocity.
   */
  int getPositionForVelocity(int velocityX, int velocityY);

  /**
   * @return the position this list must scroll to to fix a state where the
   * views are not snapped to grid.
   */
  int getFixScrollPos();
}
