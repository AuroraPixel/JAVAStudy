package core;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private Class<?> configClass;

    private final ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>(); //单例池
    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(); //bean定义池

    public ApplicationContext(){

    }

    public ApplicationContext(Class<?> configClass){
        this.configClass = configClass;
        //解析配置类注解
        scan(configClass);
        //创建非懒加载单例bean
        for (String key : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(key);
            if(beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(key,beanDefinition);
                singletonObjects.put(key, bean);
            }
        }

    }

    //创建Bean
    private Object createBean(String beaName,BeanDefinition beanDefinition){
        Class<?> declaringClass = beanDefinition.getClazz();
        Object o = null;
        try {
             o = declaringClass.getDeclaredConstructor().newInstance();
            //依赖注入
            Field[] fields = declaringClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    //获取属性名
                    String fieldName = field.getName();
                    //获取Bean对象
                    Object bean = getBean(fieldName);
                    //注入
                    field.setAccessible(true);
                    field.set(o, bean);
                }
            }
            //Aware回调
            if(o instanceof BeanNameAware){
                ((BeanNameAware) o).setBeanName(beaName);
            }
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    //解析配置类创建bean的定义
    private void scan(Class<?> configClass) {
        //判断类上是否有Component注解
        boolean annotationPresentComponentScan = configClass.isAnnotationPresent(ComponentScan.class);
        if(!annotationPresentComponentScan){
            throw new RuntimeException("请使用ComponentScan注解指定扫描路径");
        }
        ComponentScan componentScan = configClass.getDeclaredAnnotation(ComponentScan.class);
        //获取扫描路径
        String path = componentScan.value();
        //扫描路径下的所有类
        //类型加载器
        // bootstrap classloader->根加载器：jre/lib
        // ext classloader ->扩展类加载器：jre/lib/ext
        // app classloader ->应用类加载器：classpath
        //获取类加载器
        ClassLoader classLoader = this.getClass().getClassLoader();
        //根据扫描路径获取资源
        URL resource = classLoader.getResource(path.replace(".", "/"));
        File file = new File(resource.getFile());
        //判断是否是文件夹
        if(file.isDirectory()){
            //获取文件夹下的所有文件
            File[] files = file.listFiles();
            for (File itemFile : files) {
                //判断是否是class文件
                String absolutePath = itemFile.getAbsolutePath();
                if(absolutePath.endsWith(".class")){
                    //获取类名
                    String fileName = absolutePath.split("classes\\\\")[1].replace("\\", ".");
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    //加载这个类
                    Class<?> aClass;
                    try {
                        aClass = classLoader.loadClass(className);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    //判断类上是否有Component注解
                    boolean annotationPresentComponent = aClass.isAnnotationPresent(Component.class);
                    if(annotationPresentComponent){
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setClazz(aClass);
                        Component componentAnnotation = aClass.getDeclaredAnnotation(Component.class);
                        String beanName = componentAnnotation.value();
                        //判断scope
                        boolean annotationPresentScope = aClass.isAnnotationPresent(Scope.class);
                        if(annotationPresentScope){
                            Scope declaredAnnotation = aClass.getDeclaredAnnotation(Scope.class);
                            beanDefinition.setScope(declaredAnnotation.value());
                        } else {
                            beanDefinition.setScope("singleton");
                        }
                        //存入bean定义
                        beanDefinitionMap.put(beanName, beanDefinition);
                    }

                }

            }
        }
    }

    public Object getBean(String beanName){
        //先从单例池中获取
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition.getScope().equals("singleton")){
            return singletonObjects.get(beanName);
        }else {
            return createBean(beanName,beanDefinition);
        }
    }
}
