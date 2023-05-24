package create_thread;

public class createThread {
    public static void main(String[] args) {
        MyThread a = new MyThread("a");
        MyThread b = new MyThread("b");
        a.start();
        b.start();

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
                System.out.println(name+":第"+i+"次执行!");
            }
        }
    }
}
