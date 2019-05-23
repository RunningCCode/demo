package com.czc.demo.lambda;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author chenzhicong
 * @time 2019/3/19 20:40
 * @description
 */
public class LambdaTest {
    private static List<Student> students = new ArrayList<>();
    static {
        Random random = new Random();
        for(int i=0;i<200;i++){
            Student student = new Student();
            student.setName("student"+i);
            student.setScore(random.nextDouble()*100);
            students.add(student);
        }
    }
    //过滤
  /*  public static void main(String[] args){
        List<Student> above90List = students.stream()
                .filter(t->t.getScore()>90)
                .collect(Collectors.toList());
        System.out.println(above90List);
    }*/
    //转换 返回学生名称集合
  /*  public static void main(String[] args){
        List<String> nameList = students.stream().map((s) -> s.getName()).collect(Collectors.toList());
        System.out.println(nameList);
    }*/
    //基本的过滤和转换组合 返回90分以上的学生名称列表
  /*  public static void main(String[] args){
        List<String> nameList= students.stream().filter((s)-> s.getScore() > 90).map(Student::getName).collect(Collectors.toList());
        System.out.println(nameList);
    }*/
    //排重 返回字符串列表中长度小于3的字符串、转换为小写、只保留唯一的
/*    public static void main(String[] args){
        List<String> list = Arrays.asList(new String[]{"abc","def","hello","Abc"});
        list = list.stream().filter((str) -> str.length() <= 3).map((str) -> str.toLowerCase()).distinct().collect(Collectors.toList());
        System.out.println(list);
    }*/
    //过滤得到90分以上的学生，然后按分数从高到低排序，分数一样的，按名称排序
   /* public static void main(String[] args){
        List<Student> list = students.stream().filter((student -> student.getScore() > 90))
                .sorted(Comparator.comparing((Student student) -> student.getScore())
                .reversed().thenComparing(student -> student.getName())).collect(Collectors.toList());
        System.out.println(list);
    }*/
    //分页 将学生列表按照分数排序，返回第3名到第5名
  /* public static void main(String[] args){
        List<Student> list = students.stream().sorted(Comparator.comparing(Student::getScore).reversed())
                .skip(2l).limit(3l).collect(Collectors.toList());
        System.out.println(list);
   }*/

  //调试，获取90分以上学生的名称集合，并输出学生的名称
   /* public static void main(String[] args){
        List<String> above90Names = students.stream()
                .filter(t->t.getScore()>90)
                .peek(System.out::println)
                .map(Student::getName)
                .collect(Collectors.toList());
    }*/
//mapToLong/mapToInt/mapToDouble
    /*//public static void main(String[] args){
        double scoreSum = students.stream().mapToDouble(Student::getScore).sum();
        System.out.println(scoreSum);
    }*/
  //一对多转换流
/*public static void main(String[] args){
    List<String> lines = Arrays.asList(new String[]{
            "hello abc",
            "老马  编程"
    });
    List<String> words = lines.stream()
            .flatMap(line -> Arrays.stream(line.split("\\s+")))
            .collect(Collectors.toList());
    System.out.println(words);
}*/
//统计大于90分的学生个数
/*public static void main(String[] args){
    long count = students.stream().filter(student -> student.getScore() > 90d).count();
    System.out.println(count);
}*/
 /*   *//**
     *  是否所有学生都及格
     *//*
    public static void main(String[] args){
        boolean isAllPass = students.stream().allMatch(student -> student.getScore()>60);
        System.out.println(isAllPass);
    }*/
    //随便找一个不及格的学生

    /*public static void main(String[] args){
        Student student = students.stream().filter(student1 -> student1.getScore() < 60).findAny().orElseGet(null);
        System.out.println(student);
    }*/

    //逐行打印大于90分的学生

  /*  public static void main(String[] args){
        students.stream().filter(student -> student.getScore() > 90).forEach(System.out :: println);
    }*/

    //获取90分以上的学生数组
   /* public static void main(String[] args){
        Student[] studentsArr = students.stream().filter(student -> student.getScore() > 90).toArray(Student[]::new);
        System.out.println(Arrays.toString(studentsArr));
    }*/

   //使用reduce求分数最高的学生

   /* public static void main(String[] args){
        Student student = students.stream().reduce((student1, student2) -> {
            if(student2.getScore() > student1.getScore()){
                return student2;
            }else{
                return student1;
            }
        }).orElseGet(null);
        System.out.println(student);
    }*/

   //使用reduce函数计算学生分数的和

 /*   public static void main(String[] args){
        double scoreCount = students.parallelStream().reduce(0d,(Double count,Student student)->count += student.getScore(),
        (count1,count2) -> count1 += count2);
        System.out.println(scoreCount);
    }*/

 //输出当前目录下所有普通文件的名字

    /*public static void main(String[] args){
        File[] files = new File(".").listFiles();
        Arrays.stream(files).forEach(file -> System.out.println(file.getName()));
    }*/

    //输出10个随机数
 /*   public static void main(String[] args){
        Stream.generate(Math::random).limit(10).forEach(System.out :: println);
    }*/

    //输出100个递增的奇数
   /* public static void main(String[] args){
        Stream.iterate(1,(t) -> t+2).limit(10).forEach(System.out :: println);
    }*/







}
