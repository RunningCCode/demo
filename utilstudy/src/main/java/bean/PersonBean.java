package bean;

/**
 * @author chenzhicong
 * @time 2019/5/21 9:51
 * @description
 */
public class PersonBean {
    private  String name;
    private Integer age;
    public PersonBean(){

    }
    public PersonBean(String name,Integer age){
        this.setName(name);
        this.setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
