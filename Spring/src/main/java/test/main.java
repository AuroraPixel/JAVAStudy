package test;

import core.ApplicationContext;
import test.service.UserService;

public class main {
    public static void main(String[] args) {
        //创建容器
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.test();
    }
}
