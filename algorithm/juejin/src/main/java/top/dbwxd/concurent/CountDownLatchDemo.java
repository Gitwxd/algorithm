package top.dbwxd.concurent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: wxd
 * @date: 2019/11/13
 * @time: 下午1:27
 * Copyright (C) 2019
 * All rights reserved
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        List<String> teachers = Arrays.asList("语文老师", "数学老师", "英语老师", "物理老师", "化学老师", "生物老师");
        Random random = new Random();
        // 创建6个线程，模拟6个科目的老师同时开始阅卷
        List<Thread> threads = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            threads.add(new Thread(() -> {
                try {
                    int workTime = random.nextInt(6) + 1;
                    // 让线程睡眠一段时间，模拟老师的阅卷时间
                    Thread.sleep(workTime * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "阅卷完成");
                // 每位老师阅卷完成后，就让计数器减1
                countDownLatch.countDown();
            }, teachers.get(i)));
        }
        for (Thread thread : threads) {
            System.out.println(Thread.currentThread().getName()+"线程遍历");
            thread.start();
        }
        // 让主线程等待所有老师阅卷完成后，再开始计算总分，进行排名
        try {
            System.out.println("await 之前");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有科目的老师均已阅卷完成");
        System.out.println("开始计算总分，然后排名");
    }

}
