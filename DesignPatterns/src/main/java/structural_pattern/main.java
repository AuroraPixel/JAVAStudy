package structural_pattern;

import structural_pattern.ObjectAdapater.adaptee.User;
import structural_pattern.ObjectAdapater.userInterface.UserInterface;
import structural_pattern.classAdapter.UserInterface.UserService;
import structural_pattern.classAdapter.adapter.UserAdapter;

public class main {
    public static void main(String[] args) {
        classAdapter();
        ObjectAdapter();

    }

    public static void classAdapter(){
        UserService userService = new UserAdapter();
        userService.Message();
        userService.Message2();
    }

    public static void ObjectAdapter(){
        UserInterface userInterface = new structural_pattern.ObjectAdapater.adapter.UserAdapter(new User());
        userInterface.method();
        userInterface.method1();
    }
}
