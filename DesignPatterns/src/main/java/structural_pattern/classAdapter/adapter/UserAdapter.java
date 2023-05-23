package structural_pattern.classAdapter.adapter;

import structural_pattern.classAdapter.UserInterface.UserService;
import structural_pattern.classAdapter.adaptee.User;

public class UserAdapter extends User implements UserService {
    @Override
    public void Message2() {
        System.out.println("重构的方法");
    }
}
