package stop_thread;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class interrupt_thread {

    public static void main(String[] args) {
//        Thread normal = new Thread(new NormalThread(),"normal");
//        Thread lock = new Thread(new ThreadWithLock(),"lock");
//        Thread whileThread = new Thread(new WhileThread(),"while");
//        normal.start();
//        lock.start();
//        whileThread.start();
//        try {
//            Thread.sleep(3000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        normal.interrupt();
//        lock.interrupt();
//        whileThread.interrupt();

    }

    static class NormalThread implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("普通执行:"+i);
            }
        }
    }

    static class ThreadWithLock implements Runnable{
        private final ReentrantLock reentrantLock = new ReentrantLock();
        @Override
        public void run() {
            reentrantLock.lock();
            try {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("lock执行:"+i);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    static class WhileThread implements Runnable {

        @Override
        public void run() {
            int i =0;
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(500L);
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("while执行:"+i);
            }
        }
    }

    static class MyThreadPool{
        public void createThreadPool(){
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(new NormalThread());
            executorService.shutdown();
        }
    }
}
