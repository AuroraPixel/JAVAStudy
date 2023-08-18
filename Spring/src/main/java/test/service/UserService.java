package test.service;

import core.Autowired;
import core.BeanNameAware;
import core.Component;
import core.Scope;

@Component("userService")
@Scope("protoType") // 默认是单例模式
public class UserService implements BeanNameAware {

    @Autowired
    private TestService testService;

    private String beanName;

    public void test() {
        testService.test();
        System.out.println("UserService beanName: " + beanName);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
