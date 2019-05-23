package com.czc.demo.lambda;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenzhicong
 * @time 2019/3/23 11:10
 * @description
 */
public class LambdaTest2 {
    static List<Student> students;
    static String[] grades = Stream.of("大一","大二","大三","大四").toArray(String[]::new);
    static{
        Random random = new Random();
        int[] iArr = new int[1];
        iArr[0] = 1;
        students = Stream.generate(() -> {
            Student student = new Student();
            student.setName("student" + iArr[0]);
            student.setId(String.valueOf(iArr[0]));
            iArr[0] += 1;
            student.setScore(random.nextDouble() * 100);
            student.setGrade(grades[random.nextInt(4)]);
            return student;
        }).limit(100).collect(Collectors.toList());
    }
    //转换为 学生id和学生对象的Map

   /* public static void main(String[] args){
        Map<String,Student> map =students.stream().collect(Collectors.toMap(Student :: getId, Function.identity()));
        System.out.println(map);
    }*/

    //获取字符串与其长度的Map,"abc","hello","abc",忽略重复元素

   /* public static void main(String[] args){
        Map<String,Integer> map = Stream.of("abc","hello","abc")
                .collect(Collectors.toMap(Function.identity(),String::length,(l1,l2) -> l1));
        System.out.println(map);
    }*/

  /* public static void main(String[] args){
        System.out.println(Stream.of("我","你").collect(Collectors.joining("爱","❥(^_-)","❥(-_^)")));
   }*/

  //按年级进行分组

 /* public static void main(String[] args){
    Map<String,List<Student>> studentMap = students.stream().collect(Collectors.groupingBy(Student :: getGrade));
      studentMap.forEach((key,value) -> {
          System.out.println(key+":"+value);
      });
  }*/

 //统计每个年级的学生个数

   /* public static void main(String[] args){
        Map<String,Long> studentCountByGrade = students.stream()
                .collect(Collectors.groupingBy(Student :: getGrade,Collectors.counting()));
        System.out.println(studentCountByGrade);
    }*/

   //统计一个单词流中每个单词的个数，按出现顺序排序,"hello","world","abc","hello"

 /*   public static void main(String[] args){
        Map<String,Long> seeingTimesByWord = Stream.of("hello","world","abc","hello")
                .collect(Collectors.groupingBy(Function.identity(),LinkedHashMap :: new,Collectors.counting()));
        System.out.println(seeingTimesByWord);
    }*/

    //每个年级分数最高的一个学生


    /*public static void main(String[] args){
       *//* Map<String,Optional<Student>> StudentMaxScoreByGrade = students.stream()
                .collect(Collectors.groupingBy(Student :: getGrade,Collectors.maxBy(Comparator.comparing(Student :: getScore))));
      *//*
       Map<String, Student> topStudentMap = students.stream().collect(
                Collectors.groupingBy(Student::getGrade,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Student::getScore)),
                                Optional::get)));
      System.out.println(topStudentMap);

    }*/

    //按年级统计学生分数信息

    /*public static void main(String[] args){
        Map<String, DoubleSummaryStatistics> gradeScoreStat =
                students.stream().collect(
                        Collectors.groupingBy(Student::getGrade,
                                Collectors.summarizingDouble(Student::getScore)));
        System.out.println(gradeScoreStat);
    }*/

    //对学生按年级分组，得到学生名称列表

/*    public static void main(String[] args){
        Map<String,List<String>> gradeNameMap = students.stream()
                .collect(Collectors.groupingBy(
                        Student :: getGrade,Collectors.mapping(Student :: getName,Collectors.toList())
                ));
        System.out.println(gradeNameMap);
    }*/


    //定义一个收集元素再进行排序的方法

    public static <T> Collector<T, ?, List<T>> collectingAndSort(
            Collector<T, ?, List<T>> downstream,
            Comparator<? super T> comparator){
        return Collectors.collectingAndThen(downstream, (r) -> {
            r.sort(comparator);
            return r;
        });
    }

    //将学生按年级分组，分组内学生按照分数由高到低进行排序

    /*public static void main(String[] args){
        Map<String,List<Student>> gradeStudents = students.stream()
                .collect(Collectors.groupingBy(
                        Student :: getGrade,Collectors.collectingAndThen(
                                Collectors.toList(),students1 -> {
                                   students1.sort(Comparator.comparing(Student :: getScore).reversed());
                                   return students1;
                                })
                ));
        System.out.println(gradeStudents);
    }*/

    //收集完再过滤，可以定义如下方法

    public static <T>  Collector<T,?,List<T>> collectorAndFilter(Collector<T,?,List<T>> downStream, Predicate<T> predicate){
        return Collectors.collectingAndThen(downStream,
                (list) ->  list.stream().filter(predicate).collect(Collectors.toList()));
    }

    //将学生按年级分组，分组后，每个分组只保留不及格的学生(低于60分)

/*    public static void main(String[] args){
        Map<String,List<Student>> gradeStudentNoPass = students.stream()
                .collect(
                        Collectors.groupingBy(Student :: getGrade,
                                collectorAndFilter(Collectors.toList(),(student -> student.getScore() < 60)))
                );
        System.out.println(gradeStudentNoPass);

    }*/

    //收集完，只返回特定区间的结果，可以定义如下方法：

    public static <T> Collector<T,?,List<T>> collectorAndSkipLimit(Collector<T,?,List<T>> downStream,long skip,long limit){
       return Collectors.collectingAndThen(downStream,(list) -> list.stream().skip(skip).limit(limit).collect(Collectors.toList()));
    }

    //将学生按年级分组，分组后，每个分组只保留前两名的学生

   /* public static void main(String[] args){
        Map<String,List<Student>> map = students.stream().collect(Collectors.groupingBy(
                Student :: getGrade,
                collectorAndSkipLimit(Collectors.toList(),0,2)
        ));
        System.out.println(map);
    }*/

   public static void main(String[] args){
       Map<Boolean, List<Student>> byPass = students.stream().collect(
               Collectors.partitioningBy(t->t.getScore()>=60));
       System.out.println(byPass);
   }





}
