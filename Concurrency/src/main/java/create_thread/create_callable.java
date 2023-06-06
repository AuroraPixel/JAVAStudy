package create_thread;

import java.util.concurrent.*;

public class create_callable {
    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            int i = 0;
            for (int i1 = 0; i1 < 20; i1++) {
                Thread.sleep(200);
                i++;
                System.out.println(Thread.currentThread().getName()+":第"+i1+"次执行!");
            }
            return "hello world"+i;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyCallable myCallable = new MyCallable();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> submit = executorService.submit(myCallable);
        try {
            String s = submit.get();
            System.out.println(s);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
