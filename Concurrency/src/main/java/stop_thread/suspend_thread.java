package stop_thread;

import java.util.concurrent.TimeUnit;

public class suspend_thread {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+":执行"+i);

            }
        });
        thread.start();
        //执行两秒暂停
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("开始停止线程");
        thread.suspend();
        //执行两秒暂停
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("开始执行线程");
        //恢复执行
        thread.resume();

    }
}
