package com.sheygam.java_18_15_03_18;

import android.os.Handler;

import java.util.Random;

/**
 * Created by gregorysheygam on 15/03/2018.
 */

class Worker extends Thread{
    private Handler handler;

    public Worker(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(MainActivity.START);
        int count = (new Random().nextInt(15))+1;
        handler.sendMessage(handler.obtainMessage(MainActivity.TOTAL_COUNT,count,-1));
        for (int i = 1; i < count; i++) {
            handler.sendMessage(handler.obtainMessage(MainActivity.CURRENT_FILE,i,-1));
            for (int j = 0; j < 100; j++) {
                handler.sendMessage(handler.obtainMessage(MainActivity.CURRENT_PROGRESS,j+1,-1));
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        handler.sendEmptyMessage(MainActivity.END);
    }
}
