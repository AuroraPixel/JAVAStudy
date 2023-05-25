package create_thread;

public class MyThreadGroup {
    public static void main(String[] args) {
        DbThreadGroup();
        ServiceThread();

    }

    public static void DbThreadGroup(){
        ThreadGroup dbThreadGroup = new ThreadGroup("db线程组");
        //创建多线程
        new Thread(dbThreadGroup,()->{
            for (int i = 10; i > 0; i--) {
                //休眠半秒
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("线程组-"+Thread.currentThread().getThreadGroup().getName()+"  线程名-"+Thread.currentThread().getName()+"-"+i+":开始执行");
            }
        },"db连接").start();
    }

    public static void ServiceThread(){
        ThreadGroup serviceGroup = new ThreadGroup("service线程组");
        //创建多线程
        new Thread(serviceGroup,()->{
            for (int i = 10; i > 0; i--) {
                //休眠半秒
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("线程组-"+Thread.currentThread().getThreadGroup().getName()+"  线程名-"+Thread.currentThread().getName()+"-"+i+":开始执行");
            }
        },"service服务").start();
    }
}
