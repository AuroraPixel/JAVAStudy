package create_thread;

public class uncaughtException {
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            System.out.println(10/0);
        });
        thread.setUncaughtExceptionHandler((a,b)->{
            System.out.println(a.getName());
            System.out.println(b.getMessage());
        });
        thread.start();

        ThreadGroup a = new ThreadGroup("a");
    }
}
