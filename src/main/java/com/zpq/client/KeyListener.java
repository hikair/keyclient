package com.zpq.client;

import com.zpq.utilts.KeyConfigMsg;
import com.zpq.utilts.KeyLogs;
import com.zpq.utilts.KeyUtil;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author 35147
 */
public class KeyListener {
    private static Robot robot;
    private KeyFrame02 keyFrame;

    public KeyListener(KeyFrame02 keyFrame){
        this.keyFrame=keyFrame;
    }

    static{
        try {
            robot = new Robot();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 添加热键
     */
    public void addKey() {
        List<String> keys = KeyConfigMsg.keys;
        for(int i=0;i<keys.size();i++){
            String s=keys.get(i);
            //判断是单键还是组合键
            if(s.contains("+")){
                //组合键
                //判断有几个组合键
                String[] split = s.split("\\+");
                if(split.length==2){
                    //一个组合键
                    //split[1]分为两种情况:1是数字(48-57)或字母(65-90),2是其他特殊字符如:回车
                    int code = KeyUtil.getKeyCode(split[1]);
                    boolean isNumOrWord = (code>47 && code<58)||(code>64 && code<91);
                    if(isNumOrWord){
                        //直接把字符串放进来
                        JIntellitype.getInstance().registerHotKey(i,s);
                    }else {
                        JIntellitype.getInstance().registerHotKey(i,KeyUtil.getModNum(split[0]),code);
                    }
                }else {
                    //两个及以上组合键,最后一个必定是数字或字母,已经在服务端做了判断。
                    JIntellitype.getInstance().registerHotKey(i,s);
                }
            }else{
                //单键
                if("ctrl".equals(s) || "shift".equals(s) || "alt".equals(s)){
                    JIntellitype.getInstance().registerHotKey(i,s);
                }else {
                    JIntellitype.getInstance().registerHotKey(i,0, KeyUtil.getKeyCode(s));
                }
            }
        }
    }

    /**
     * 卸载热键
     */
    public void clearKey(int keyCode){
        JIntellitype.getInstance().unregisterHotKey(keyCode);
    }


    public void startListen() {
        addKey();
        // 添加热键监听器
        // 第二步：添加热键监听器
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
            @Override
            public void onHotKey(int markCode) {
                handle(markCode);
            }
        });
    }

    /**
     * 处理监听事件
     * 因为jna监听键盘会导致会被捕获,等于按了某个键实际并不会输出出来,因此需要一个方法模拟按下弹出这个键
     * @param markCode 监听的标识符,等于KeyConfigMsg里的keys的下标
     */
    public void handle(int markCode){
        List<String> list = KeyConfigMsg.keys;
        for(int i=0;i<list.size();i++){
            String s = list.get(i);
            //i是markCode,s是监听键名
            //主要干两件事一个模拟原按键功能(删除热键,模拟,恢复热键),一个是记录日志

            //监听到了事件
            if(markCode == i){
                moni(markCode,s);
            }
        }
    }

    /**
     * @param markCode 监听的标识符,等于KeyConfigMsg里的keys的下标
     * @param s 监听键名
     */
    public void moni(int markCode,String s){
        //去除热键
        clearKey(markCode);
        //模拟按键按下
        robot.delay(50);
        String[] split = s.split("\\+");
        for (String s1 : split) {
            robot.keyPress(KeyUtil.getKeyEventCode(s1));
        }
        robot.delay(50);
        for (String s1 : split) {
            robot.keyRelease(KeyUtil.getKeyEventCode(s1));
        }
        addKey();
        //记录日志
        writeLog(s);
    }

    public void writeLog(String keyName){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Timestamp(System.currentTimeMillis()));
            InetAddress addr = InetAddress.getLocalHost();
            String log = date + "|" + keyName + "|" + addr.getHostAddress() + "|" + addr.getHostName() + "|"+"\n";
            System.out.println(log);
            KeyLogs.logs.append(log);
            keyFrame.getT_log().append(new SimpleDateFormat("HH:mm:ss").format(new Timestamp(System.currentTimeMillis()))+" "+keyName+"\n");
            keyFrame.getT_log().setCaretPosition(keyFrame.getT_log().getDocument().getLength());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
