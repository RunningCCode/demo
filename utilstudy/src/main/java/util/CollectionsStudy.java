package util;

import org.apache.commons.collections4.*;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenzhicong
 * @time 2019/5/21 11:30
 * @description
 */
public class CollectionsStudy {
  /** bag接口 */
  public void testBag() {
    Bag<String> bag = new HashBag<>();

    // add "a" two times to the bag.
    bag.add("a", 2);

    // add "b" one time to the bag.
    bag.add("b");

    // add "c" one time to the bag.
    bag.add("c");

    // add "d" three times to the bag.
    bag.add("d", 3);

    // get the count of "d" present in bag.
    System.out.println("d is present " + bag.getCount("d") + " times.");
    System.out.println("bag: " + bag);

    // get the set of unique values from the bag
    System.out.println("Unique Set: " + bag.uniqueSet());

    // remove 2 occurrences of "d" from the bag
    bag.remove("d", 2);
    System.out.println("2 occurences of d removed from bag: " + bag);
    System.out.println("d is present " + bag.getCount("d") + " times.");
    System.out.println("bag: " + bag);
    System.out.println("Unique Set: " + bag.uniqueSet());
  }
  /**
    * IterableMap接口
    */
  @Test
  public void testIterableMap(){
    IterableMap<String, String> map = new HashedMap<>();

    map.put("1", "One");
    map.put("2", "Two");
    map.put("3", "Three");
    map.put("4", "Four");
    map.put("5", "Five");

    MapIterator<String, String> iterator = map.mapIterator();
    while (iterator.hasNext()) {
      Object key = iterator.next();
      Object value = iterator.getValue();

      System.out.println("key: " + key);
      System.out.println("Value: " + value);

      iterator.setValue(value + "_");
    }
  }

  /**
   * BidiMap接口
   */
  @Test
  public void testBidiMap(){
    BidiMap<String, String> bidi = new TreeBidiMap<>();

    bidi.put("One", "1");
    bidi.put("Two", "2");
    bidi.put("Three", "3");

    System.out.println(bidi.get("One"));
    System.out.println(bidi.getKey("1"));
    System.out.println("Original Map: " + bidi);

    bidi.removeValue("1");
    System.out.println("Modified Map: " + bidi);
    BidiMap<String, String> inversedMap = bidi.inverseBidiMap();
    System.out.println("Inversed Map: " + inversedMap);
    HashMap hashMap = new HashMap();
  }

  /**
    * OrderedMap接口
    */
  @Test
  public void testOrderedMap(){
    OrderedMap<String, String> map = new LinkedMap<String, String>();
    map.put("One", "1");
    map.put("Two", "2");
    map.put("Three", "3");

    System.out.println(map.firstKey());
    System.out.println(map.nextKey("One"));
    System.out.println(map.nextKey("Two"));

    LinkedHashMap map2 = new LinkedHashMap();

  }

  //----------------------collectionsUtil ----------------------
  @Test
  /**
    * 避免集合被添加进null元素
    */
  public void testAddIgnoreNull(){
    List<String> list = new LinkedList<String>();
    Optional.ofNullable("a").ifPresent(a->list.add(a));
    CollectionUtils.addIgnoreNull(list, null);
    CollectionUtils.addIgnoreNull(list, "a");
    System.out.println(list);

    if(list.contains(null)) {
      System.out.println("Null value is present");
    } else {
      System.out.println("Null value is not present");
    }

  }
  /**
    *合并两个排序列表
    */
  @Test
  public void testCollate(){
    List<String> sortedList1 = Arrays.asList("A","C","E");
    List<String> sortedList2 = Arrays.asList("B","D","F");
    List<String> mergedList = CollectionUtils.collate(sortedList1, sortedList2);
    List<String> mergedList2 = Stream.concat(sortedList1.stream(),sortedList2.stream()).sorted().collect(Collectors.toList());
    System.out.println(mergedList);
    System.out.println(mergedList2);
  }
  /**
   * 换结果
   */
  @Test
  public void testCollect(){
    List<String> stringList = Arrays.asList("1","2","3");

    List<Integer> integerList = (List<Integer>) CollectionUtils.collect(stringList,
            Integer::parseInt);
    System.out.println(integerList);
    //jdk1.8
    List<Integer> integerList2 = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());

  }
  /**
   * 过滤
   */
  @Test
  public void testFilter(){

    List<Integer> integerList = new ArrayList<Integer>();
    integerList.addAll(Arrays.asList(1,2,3,4,5,6,7,8));

    System.out.println("Original List: " + integerList);
    CollectionUtils.filter(integerList, (i)->i%2==0);

    System.out.println("Filtered List (Even numbers): " + integerList);

    //jdk1.8
    integerList.stream().filter((i)->i%2==0).collect(Collectors.toList());
  }
  /**
    * 检查集合是否为空（集合为null返回false）
    */
  @Test
  public void testIsNotEmpty(){
    List<String> list = getList();
    System.out.println("Non-Empty List Check: " + checkNotEmpty1(list));
    System.out.println("Non-Empty List Check: " + checkNotEmpty1(list));
  }
  static List<String> getList() {
    return null;
  }

  static boolean checkNotEmpty1(List<String> list) {
    return !(list == null || list.isEmpty());
  }

  static boolean checkNotEmpty2(List<String> list) {
    return CollectionUtils.isNotEmpty(list);
  }

  /**
   * 检查是否是子集
   */
  @Test
  public void  testIsSubCollection(){
    //checking inclusion
    List<String> list1 = Arrays.asList("A","A","A","C","B","B");
    List<String> list2 = Arrays.asList("A","A","B","B");

    System.out.println("List 1: " + list1);
    System.out.println("List 2: " + list2);
    System.out.println("Is List 2 contained in List 1: "
            + CollectionUtils.isSubCollection(list2, list1));
    //jdk1.8
    list1.stream().allMatch(s->list2.contains(s));

  }

  /**
   * 求交集
   */
  @Test
  public void testIntersection(){
    //checking inclusion
    List<String> list1 = Arrays.asList("A","A","A","C","B","B");
    List<String> list2 = Arrays.asList("A","A","B","B");

    System.out.println("List 1: " + list1);
    System.out.println("List 2: " + list2);
    System.out.println("Commons Objects of List 1 and List 2: "
            + CollectionUtils.intersection(list1, list2));
  }

  /**
   * 求差集
   */
  @Test
  public void testSubtract(){
    List<String> list1 = Arrays.asList("A","A","A","C","B","B");
    List<String> list2 = Arrays.asList("A","A","B","B");

    System.out.println("List 1: " + list1);
    System.out.println("List 2: " + list2);
    System.out.println("List 1 - List 2: "
            + CollectionUtils.subtract(list1, list2));

  }
  /**
   * 求联合集
   */
  @Test
  public void testUnion(){
    //checking inclusion
    List<String> list1 = Arrays.asList("A","A","A","C","B","B");
    List<String> list2 = Arrays.asList("A","A","B","B");
    System.out.println("List 1: " + list1);
    System.out.println("List 2: " + list2);
    System.out.println("Union of List 1 and List 2: "
            + CollectionUtils.union(list1, list2));

  }





}
