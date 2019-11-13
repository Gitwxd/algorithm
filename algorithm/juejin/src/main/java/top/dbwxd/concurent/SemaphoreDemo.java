package top.dbwxd.concurent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @description:
 * @author: wxd
 * @date: 2019/11/13
 * @time: 下午1:00
 * Copyright (C) 2019
 * All rights reserved
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 创建2个许可证，表示同一时刻只允许两个线程访问数据库
        Semaphore semaphore = new Semaphore(2);
        List<Thread> threads = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            int index = i;
            threads.add(new Thread(() -> {
                try {
                    // 在获取数据库连接之前先要获取到许可，这样就能保证统一时刻最大允许鬼固定的线程获取到数据库资源
                    semaphore.acquire();
                    // 获取数据链接
                    // 保存数据
                    // 让当前线程睡眠两秒，模拟业务处理的时间
                    Thread.sleep(2000);
                    System.out.println("线程T" + index + "操作成功");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }, "T" + i));
        }
        for (Thread thread : threads) {
            thread.start();
        }

    }


}
