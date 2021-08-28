package com.zpq.client;

import com.zpq.utilts.PropertiesUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

/**
 * @author 35147
 */
public class KeyConfigClient {
    public static String run() {
        System.out.println("请给我按键配置信息!");
        try {
            //从配置文件中 获取服务端ip地址和端口
            Map<String, String> map = PropertiesUtil.getMap("keyClient");
            Socket client = new Socket(map.get("serverIp"), Integer.parseInt(map.get("configPort")));
            OutputStream os = client.getOutputStream();
            InetAddress addr = InetAddress.getLocalHost();
            String msg = addr.getHostAddress();
            os.write(msg.getBytes());
            InputStream in = client.getInputStream();
            byte[] b = new byte[1024];
            int len = in.read(b);
            os.close();
            in.close();
            client.close();
            return new String(b,0,len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
