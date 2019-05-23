package util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author chenzhicong
 * @time 2019/5/23 17:40
 * @description 太多了，参考博客：https://so.csdn.net/so/search/s.do?q=commons-lang3&t=blog&u=u012240455
 */
public class langStudy {
  boolean[] array = new boolean[] {};

  @Test
  public void testArrayUtils() {

    System.out.println(StringUtils.containsOnly("abab", "abc"));
  }
}
