package test.service;

import core.*;

@Component("userService")
@Scope("protoType") // 默认是单例模式
public class UserService implements BeanNameAware , InitializingBean {

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

    @Override
    public void afterPropertiesSet() {
        System.out.println("UserService 初始化");
    }
}
