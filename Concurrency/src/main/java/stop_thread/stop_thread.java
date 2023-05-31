package stop_thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class stop_thread {
    static class creatThreadFactory implements ThreadFactory{
        private final Integer size;

        private final String name;

        private final AtomicInteger count = new AtomicInteger(0);

        private final AtomicInteger seq = new AtomicInteger(0);

        private final ThreadGroup threadGroup;

        creatThreadFactory(Integer size,String name){
            this.size = size;
            this.threadGroup = new ThreadGroup(name);
            this.name = name;
        }
        @Override
        public Thread newThread(Runnable r) {
            int i = count.incrementAndGet();
            if(i>size){
                throw new RuntimeException("超出队列长度");
            }
            return new Thread(threadGroup,r,name+"-"+seq.incrementAndGet());
        }
    }

    static class threadWithSync  implements Runnable{

        @Override
        public void run() {
            synchronized (this){
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+"-正在执行:"+i);
                }
            }
        }
    }

    static class threadWithLock implements Runnable{
        private final ReentrantLock reentrantLock = new ReentrantLock();
        @Override
        public void run() {
            reentrantLock.lock();
            try {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+"-正在执行:"+i);
                }
            }finally {
                reentrantLock.unlock();
            }
        }
    }

    /**
     * user类
     */
    static class User{
        Integer id;

        Integer money;

        User(){}

        User(int id,int money){
            this.id = id;
            this.money = money;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMoney() {
            return money;
        }

        public void setMoney(Integer money) {
            this.money = money;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", money=" + money +
                    '}';
        }
    }


    static class TransferThread implements Runnable {
        private User userA;
        private User userB;

        private Integer money;

        TransferThread(User userA,User userB,Integer money){
            this.userA = userA;
            this.userB = userB;
            this.money = money;
        }

        public void subtractUserBalance(User user,int money) throws InterruptedException {
            //user减少钱
            user.money = user.money-money;
            Thread.sleep(1000L);
        }

        public void addUserBalance(User user,int money) throws InterruptedException {
            //user加钱
            user.money = user.money+money;
            Thread.sleep(1000L);
        }

        public void transFerA2B() throws InterruptedException {
            subtractUserBalance(userA,money);
            addUserBalance(userB,money);
        }

        public void show(){
            System.out.println("userA:"+userA);
            System.out.println("userB:"+userB);
        }

        @Override
        public void run() {
            try {
                transFerA2B();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        creatThreadFactory stopLock = new creatThreadFactory(5, "stop_lock测试");
//        threadWithLock threadWithLock = new threadWithLock();
//        threadWithSync threadWithSync = new threadWithSync();
//        Thread thread = stopLock.newThread(threadWithSync);
//        thread.start();
//        Thread.sleep(2000L);
//        thread.stop();
//        stopLock.newThread(threadWithSync).start();
//        stopLock.newThread(threadWithLock).start();
        User userA = new User(10001, 100);
        User userB = new User(10002, 100);
        TransferThread transferThread = new TransferThread(userA, userB, 50);
        Thread thread = stopLock.newThread(transferThread);
        thread.start();
        Thread.sleep(200);
        //Thread.sleep(2000);
        thread.stop();
        transferThread.show();
    }
}
