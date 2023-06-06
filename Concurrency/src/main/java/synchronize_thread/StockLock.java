package synchronize_thread;

import java.util.concurrent.CountDownLatch;

public class StockLock {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(30);
        stockNum stockNum = new stockNum(2000, 100, start, countDownLatch);
        for (int i = 0; i < 30; i++) {
            new Thread(stockNum).start();
        }
        System.out.println("开始减库存");
        Thread.sleep(2000);
        System.out.println("开始");
        start.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("最终库存：" + stockNum.getStockNum());
    }

    static class stockNum implements Runnable {
        private int stockNum;
        private final int subNum;

        private CountDownLatch start;

        private CountDownLatch end;

        public stockNum(int stockNum, int subNum, CountDownLatch start, CountDownLatch end) {
            this.stockNum = stockNum;
            this.subNum = subNum;
            this.start = start;
            this.end = end;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void subStockNum() {
            this.stockNum -= subNum;
        }

        @Override
        public void run() {
            //等待
            try {
                start.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (stockNum - subNum > 0) {
                subStockNum();
            } else {
                System.out.println("库存不足");
            }
            //计数器减一
            end.countDown();
        }
    }

}
