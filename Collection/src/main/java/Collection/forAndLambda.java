package Collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class forAndLambda {
    static class User {
        private String name;
        private Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
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
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) {
        Collection<User> users = new ArrayList<>();
        users.add(new User("a", 1));
        users.add(new User("b", 2));
        users.add(new User("c", 3));
        for (User user : users) {
            user.setName("1");
        }
        System.out.println(users);

    }

    public static void listMethod(){
        List<String> lists = new ArrayList<>();
        lists.add("aaa");
        lists.add("ddd");
        lists.add("ccc");
        ListIterator<String> stringListIterator = lists.listIterator();
    }
}
