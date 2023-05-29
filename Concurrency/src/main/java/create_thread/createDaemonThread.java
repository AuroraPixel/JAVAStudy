package create_thread;

public class createDaemonThread {
    public static void main(String[] args) {
        //创建一个守护线程
        Thread damon = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+":执行了");
            }
        }, "守护线程");
        damon.setDaemon(true);
        Thread thread1 = new Thread(() -> {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ":执行了" + i + "次");
            }
        });
        damon.start();
        thread1.start();
    }
}
