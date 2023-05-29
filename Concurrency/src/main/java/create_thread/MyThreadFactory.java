package create_thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        TestThreadFactory testThreadFactory = new TestThreadFactory(5, "测试线程组", "测试业务一");
        for (int i = 6; i > 0; i--) {
            testThreadFactory.newThread(()->{
                for (int i1 = 0; i1 < 30; i1++) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("线程组-"+Thread.currentThread().getThreadGroup().getName()+"  线程名-"+Thread.currentThread().getName()+":开始执行"+i1);
                }
            }).start();
        }
        countDownLatch.await();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
    }

    public static class TestThreadFactory implements ThreadFactory{
        /**
         * 最大线程数
         */
        private int maxThreadCount;
        /**
         * 线程组名称
         */
        private String threadGroupName;

        /**
         * 线程名前缀
         */
        private String threadPrefixName;

        /**
         * 线程组
         */
        private ThreadGroup threadGroup;

        /**
         * 总线程数
         */
        private final AtomicInteger count = new AtomicInteger(0);

        /**
         * 线程队列数
         */
        private final AtomicInteger threadSeq = new AtomicInteger(0);

        public TestThreadFactory(int maxThreadCount,String threadGroupName,String threadPrefixName){
            this.maxThreadCount = maxThreadCount;
            this.threadGroupName=threadGroupName;
            this.threadPrefixName=threadPrefixName;
            this.threadGroup = new ThreadGroup(this.threadGroupName);
        }

        @Override
        public Thread newThread(Runnable r) {
            //线程增加
            int i = count.incrementAndGet();
            if(i>maxThreadCount){
                return null;
            }
            Thread thread = new Thread(threadGroup, r, threadPrefixName+"-"+threadSeq.incrementAndGet());
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }


    public static class TestThreadFactory1 implements ThreadFactory{
        private final AtomicInteger count =  new AtomicInteger(0);
        private final AtomicInteger threadSeq = new AtomicInteger(0);

        private  Integer maxThreadSize;

        private  String threadGroupName;

        private  String threadName;

        private  ThreadGroup threadGroup;

        TestThreadFactory1(Integer maxThreadSize,String threadGroupName,String threadName){
            this.maxThreadSize = maxThreadSize;
            this.threadGroupName = threadGroupName;
            this.threadName = threadName;
            this.threadGroup = new ThreadGroup(this.threadGroupName);
        }

        @Override
        public Thread newThread(Runnable r) {
            int i = count.incrementAndGet();
            if(i>maxThreadSize){
                return null;
            }
            return new Thread(threadGroup,r,threadName+"-"+threadSeq.incrementAndGet());
        }
    }
}
