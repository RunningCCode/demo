package com.czc.demo.lambda;

import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenzhicong
 * @time 2019/3/27 9:51
 * @description
 */
@SuppressWarnings("all")
public class LambdaTest3 {
  static List<Student> students;
  static String[] gradeArr = Stream.of("大一", "大二", "大三", "大四").toArray(String[]::new);

  static {
    int[] i = new int[1];
    i[0] = 0;
    Random random = new Random();
    students =
        Stream.generate(
                () -> {
                  Student s = new Student();
                  s.setId(String.valueOf(i[0]));
                  s.setName("student" + i[0]);
                  s.setGrade(gradeArr[random.nextInt(4)]);
                  s.setScore(random.nextDouble() * 100);
                  i[0]++;
                  return s;
                })
            .limit(100)
            .collect(Collectors.toList());
  }
  // -------------------------中间操作-------------------------------

  // 1、过滤90分以上的学生
  @Test
  public void fun1() {
    List<Student> result =
        students.stream().filter(student -> student.getScore() > 90).collect(Collectors.toList());
    System.out.println(result);
  }

  // 2、返回学生名称的集合
  @Test
  public void fun2() {
    List<String> result =
        students.stream().map(student -> student.getName()).collect(Collectors.toList());
    System.out.println(result);
  }

  // 3、返回90分以上学生名称集合
  @Test
  public void fun3() {
    List<String> result =
        students.stream()
            .filter(student -> student.getScore() > 90)
            .map(student -> student.getName())
            .collect(Collectors.toList());
    System.out.println(result);
  }

  // 4、给定字符串列表，abc,ab,ab返回字符串列表中长度小于3的字符串、转换为小写、只保留唯一的
  @Test
  public void fun4() {
    String[] strArr = Stream.of("abc", "ab", "ab").toArray(String[]::new);
    List<String> result =
        Arrays.stream(strArr)
            .filter(str -> str.length() < 3)
            .map(str -> str.toLowerCase())
            .distinct()
            .collect(Collectors.toList());
    System.out.println(result);
  }

  // 5、过滤得到90分以上的学生，然后按分数从高到低排序，分数一样的，按名称排序
  @Test
  public void fun5() {
    List<Student> result =
        students.stream()
            .filter(student -> student.getScore() > 90)
            .sorted(
                Comparator.comparing(Student::getScore)
                    .reversed()
                    .thenComparing((Student student) -> student.getName()))
            .collect(Collectors.toList());
    System.out.println(result);
  }

  // 6、分页 将学生列表按照分数排序，返回第3名到第5名
  @Test
  public void fun6() {
    List<Student> result =
        students.stream()
            .sorted(Comparator.comparing(Student::getScore).reversed())
            .skip(2L)
            .limit(3L)
            .collect(Collectors.toList());
    System.out.println(result);
  }

  // 7、调试，获取90分以上学生的名称集合，并输出学生的名称
  @Test
  public void fun7() {
    List<Student> result =
        students.stream()
            .filter(student -> student.getScore() > 90)
            .peek(student -> System.out.println(student.getName()))
            .collect(Collectors.toList());
    System.out.println(result);
  }

  // -------------------------终端操作-------------------------------

  // 8、转换字符串数组[“1,2,3,4,5”,”3,4,5,6,7”,”4,5,6,7,8”]为一个集合，其中元素对应数组每个元素中按逗号分隔元素。
  @Test
  public void fun8() {
    String[] result =
        Stream.of("1,2,3,4,5", "3,4,5,6,7", "4,5,6,7,8")
            .flatMap(s -> Arrays.stream(s.split(",")))
            .toArray(String[]::new);
    System.out.println(result);
  }

  // 9、统计大于90分的学生个数
  @Test
  public void fun9() {
    long result = students.stream().filter(student -> student.getScore() > 90).count();
    System.out.println(result);
  }

  // 10、是否所有学生都及格
  @Test
  public void fun10() {
    boolean result = students.stream().allMatch(student -> student.getScore() > 60);
    System.out.println(result);
  }

  // 11、随便找一个不及格的学生
  @Test
  public void fun11() {
    Student result =
        students.stream()
            .filter(student -> student.getScore() < 60)
            .findAny()
            .orElseGet(() -> null);
    System.out.println(result);
  }

  // 12、逐行打印大于90分的学生
  @Test
  public void fun12() {
    students.stream()
        .filter(student -> student.getScore() > 90)
        .forEach(student -> System.out.println(student));
  }

  // 13、获取90分以上的学生数组
  @Test
  public void fun13() {
    Student[] result =
        students.stream().filter(student -> student.getScore() > 90).toArray(Student[]::new);
    System.out.println(result);
  }

  // 14、使用reduce求分数最高的学生
  @Test
  public void fun14() {
    Student result =
        students.stream()
            .reduce(
                (s1, s2) -> {
                  if (s2.getScore() > s1.getScore()) {
                    return s2;
                  } else {
                    return s1;
                  }
                })
            .orElseGet(() -> null);
    System.out.println(result);
  }

  // 15、使用reduce函数计算学生分数的和
  @Test
  public void fun15() {
    double result =
        students.stream()
            .reduce(0d, (count, student) -> count += student.getScore(), (d1, d2) -> d1 + d2);
    System.out.println(result);
  }

  // 16、输出当前目录下所有普通文件的名字
  @Test
  public void fun16() {
    File thisFile = new File(".");
    Arrays.stream(thisFile.listFiles()).forEach(file -> System.out.println(file.getName()));
  }

  // 17、输出10个随机数
  @Test
  public void fun17() {
    Random random = new Random();
    Stream.generate(() -> Integer.valueOf(random.nextInt(100)))
        .limit(10)
        .forEach(i -> System.out.println(i));
  }

  // 18、输出100个递增的奇数
  @Test
  public void fun18() {
    Stream.iterate(1, (i) -> i + 2).limit(100).forEach(i -> System.out.println(i));
  }

  // -------------------------收集器--------------------------------

  // 19、转换为 学生id和学生对象的Map
  @Test
  public void fun19() {
    Map<String, Student> result =
        students.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
    System.out.println(result);
  }

  // 20、获取字符串与其长度的Map,"abc","hello","abc",忽略重复元素
  @Test
  public void fun20() {
    Map<String, Integer> result =
        Stream.of("abc", "hello", "abc")
            .collect(Collectors.toMap(Function.identity(), str -> str.length(), (v1, v2) -> v1));
    System.out.println(result);
  }

  // 21、按年级进行分组
  @Test
  public void fun21() {
    Map<String, List<Student>> result =
        students.stream().collect(Collectors.groupingBy(Student::getGrade));
    System.out.println(result);
  }

  // 22、统计每个年级的学生个数
  @Test
  public void fun22() {
    Map<String, Long> result =
        students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
    System.out.println(result);
  }

  // 23、统计一个单词流中每个单词的个数，按出现顺序排序,"hello","world","abc","hello"
  @Test
  public void fun23() {
    Map<String, Long> result =
        Stream.of("hello", "world", "abc", "hello")
            .collect(
                Collectors.groupingBy(
                    Function.identity(), LinkedHashMap::new, Collectors.counting()));
    System.out.println(result);
  }

  // 24、每个年级分数最高的一个学生
  @Test
  public void fun24_1() {
    Map<String, Optional<Student>> result =
        students.stream()
            .collect(
                Collectors.groupingBy(
                    Student::getGrade, Collectors.maxBy(Comparator.comparing(Student::getScore))));
    System.out.println(result);
  }

  @Test
  public void fun24_2() {
    Map<String, Student> result =
        students.stream()
            .collect(
                Collectors.groupingBy(
                    Student::getGrade,
                    Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(Student::getScore)), Optional::get)));
    System.out.println(result);
  }

  // 25、按年级统计学生分数信息
  @Test
  public void fun25() {
    Map<String, DoubleSummaryStatistics> result =
        students.stream()
            .collect(
                (Collectors.groupingBy(
                    Student::getGrade, Collectors.summarizingDouble(Student::getScore))));
    System.out.println(result);
  }

  // 26、对学生按年级分组，得到学生名称列表
  @Test
  public void fun26() {
    Map<String, List<String>> result =
        students.stream()
            .collect(
                Collectors.groupingBy(
                    Student::getGrade, Collectors.mapping(Student::getName, Collectors.toList())));
    System.out.println(result);
  }

  // 27、将学生按年级分组，分组内学生按照分数由高到低进行排序
  @Test
  public void fun27() {
    Map<String, List<Student>> result =
        students.stream()
            .collect(
                Collectors.groupingBy(
                    Student::getGrade,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        students -> {
                          students.sort(Comparator.comparing(Student::getScore).reversed());
                          return students;
                        })));
    System.out.println(result);
  }

  // 28、将学生按年级分组，分组后，每个分组只保留不及格的学生(低于60分)
  @Test
  public void fun28() {
    Map<String, List<Student>> result =
        students.stream()
            .collect(
                Collectors.groupingBy(
                    Student::getGrade,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        list ->
                            list.stream()
                                .filter(student -> student.getScore() < 60)
                                .collect(Collectors.toList()))));
    System.out.println(result);
  }

  // 29、将学生按年级分组，分组后，每个分组只保留前两名的学生
  @Test
  public void fun29() {
    Map<String, List<Student>> result =
        students.stream()
            .collect(
                Collectors.groupingBy(
                    Student::getGrade,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        students1 ->
                            students1.stream()
                                .sorted(Comparator.comparing(Student::getScore).reversed())
                                .limit(2)
                                .collect(Collectors.toList()))));
    System.out.println(result);
  }

  // 30、将学生按照是否及格(大于等于60分)分为两组
  @Test
  public void fun30() {
    Map<Boolean, List<Student>> result =
        students.stream().collect(Collectors.partitioningBy(student -> student.getScore() >= 60));
    System.out.println(result);
  }

  // 31、按是否及格分组后，计算每个分组的平均分
  @Test
  public void fun31() {
    Map<Boolean, Double> result =
        students.stream()
            .collect(
                Collectors.partitioningBy(
                    student -> student.getScore() >= 60,
                    Collectors.averagingDouble(Student::getScore)));
    System.out.println(result);
  }

  // 32、按年级对学生分组，分组后，再按照是否及格对学生进行分区
  @Test
  public void fun32() {
    Map<String, Map<Boolean, List<Student>>> result =
        students.stream()
            .collect(
                Collectors.groupingBy(
                    Student::getGrade,
                    Collectors.partitioningBy((Student student) -> student.getScore() > 60)));
    System.out.println(result);
  }
}
