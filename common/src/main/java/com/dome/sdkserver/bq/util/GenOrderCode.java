package com.dome.sdkserver.bq.util;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生成唯一ID
 *
 * @author Administrator
 */
public class GenOrderCode {

    private static Lock lock = new ReentrantLock();

    private static Lock outOrderNoLock = new ReentrantLock();

    public static synchronized String next() {
        Date date = new Date();
        StringBuilder buf = new StringBuilder();
        buf.delete(0, buf.length());
        date.setTime(System.currentTimeMillis());
        String str = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tL%2$06d", date, genSixNum());
        return str;
    }

    /**
     * 生成冰穹订单号
     * @param prefix
     * @return
     */
    public static String next(String prefix) {
        lock.lock();
        try {
            return generateOrderNo(prefix);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 生成订单=时间戳+6位随机数
     *
     * @param prefix
     * @return
     */
    private static String generateOrderNo(String prefix) {
        Date date = new Date();
        StringBuilder buf = new StringBuilder();
        buf.delete(0, buf.length());
        date.setTime(System.currentTimeMillis());
        String str = String.format("%1$ty%1$tm%1$td%1$tH%1$tM%1$tS%2$06d", date, genSixNum());
        return prefix + str;
    }

    /**
     * 生成外部订单号
     * @param prefix
     * @return
     */
    public static String genOutOrderNo(String prefix) {
        outOrderNoLock.lock();
        try {
            return generateOrderNo(prefix);
        } finally {
            outOrderNoLock.unlock();
        }
    }


    /**
     * 随机生成6位数
     *
     * @return
     */
    private static int genSixNum() {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < 6; i++)
            result = result * 10 + array[i];

        return result;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(GenOrderCode.next(""));
                }
            });
        }
        service.shutdownNow();
    }
}
