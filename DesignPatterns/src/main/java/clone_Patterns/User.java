package clone_Patterns;

import java.util.List;

public class User implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Integer age;
    private String name;

    private List<User> actions;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getActions() {
        return actions;
    }

    public void setActions(List<User> actions) {
        this.actions = actions;
    }
}
