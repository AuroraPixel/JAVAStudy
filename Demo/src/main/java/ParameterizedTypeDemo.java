

public class ParameterizedTypeDemo {
    public static void main(String[] args) {
        System.out.println(6<<1);
    }

    interface TestParameterizeTest<T> {
        String getName(T a);
    }

    static class SomeClass implements TestParameterizeTest<String> {

        @Override
        public String getName(String a) {
            return null;
        }
    }
}
