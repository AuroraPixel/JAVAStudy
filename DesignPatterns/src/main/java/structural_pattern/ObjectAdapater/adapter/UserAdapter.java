package structural_pattern.ObjectAdapater.adapter;

import structural_pattern.ObjectAdapater.adaptee.User;
import structural_pattern.ObjectAdapater.userInterface.UserInterface;

public class UserAdapter implements UserInterface {
    private User user;
    public UserAdapter(User user){
        this.user = user;
    }

    public void method(){
        user.method();
    }

    public void method1(){
        System.out.println("适配扩展的方法");
    }
}
