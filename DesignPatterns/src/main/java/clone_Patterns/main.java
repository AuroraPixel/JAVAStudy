package clone_Patterns;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
      UserClone();
    }

    public static User  createUser(){
        User user = new User();
        user.setAge(13);
        user.setName("aaa");
        List<User> actions = new ArrayList<>();
        User user1 = new User();
        user.setAge(13);
        user.setName("aaa");
        User user2 = new User();
        user.setAge(14);
        user.setName("bbb");
        actions.add(user1);
        actions.add(user2);
        user.setActions(actions);
        return user;
    }


    public static void UserClone(){
        User user = createUser();
        User clone;
        try {
            clone =(User) user.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(user.getActions());
        System.out.println(clone.getActions());
    }

    public static void UserDeepClone(){

    }
}
