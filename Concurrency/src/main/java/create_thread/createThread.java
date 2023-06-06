package create_thread;


public class createThread {
    public static void main(String[] args) {
//        MyThread a = new MyThread("a");
//        MyThread b = new MyThread("b");
//        a.start();
//        b.start();
        MyThread1 c = new MyThread1("c");
        Thread thread = new Thread(c, "c");
        Thread.yield();
        new Thread(c,"d").start();


        new Thread(()->{
            for (int i = 20; i > 0; i--) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+"e:第"+i+"次执行!");
            }
        }, "e").start();

    }

    public static class MyThread extends Thread{
        private String name;

        public MyThread(String name){
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 20; i > 0; i--) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+name+":第"+i+"次执行!");
            }
        }
    }

    public static class MyThread1 implements Runnable {

        private String name;

        public MyThread1(String name){
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 20; i > 0; i--) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+name+":第"+i+"次执行!");
            }
        }
    }
}
