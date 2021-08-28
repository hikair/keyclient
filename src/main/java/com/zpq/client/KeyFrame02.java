package com.zpq.client;

import com.zpq.utilts.KeyConfigMsg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 35147
 */
public class KeyFrame02 extends JFrame implements ActionListener {

    private Container container;
    //分别为左侧按钮区,查看按键区,日志区,联系区,投食区
    private static JPanel p_menu,p_view,p_log,p_contact,p_feed;
    private JScrollPane js,js2;
    private JButton b_view,b_log,b_contact,b_feed;
    private JTextArea t_view,t_log,t_contact;
    private SystemTray systemTray;
    private TrayIcon trayIcon=null;

    public JTextArea getT_log() {
        return t_log;
    }

    public KeyFrame02(){
        super("键盘监听");
        //获得一个容器
        container = getContentPane();
        container.setBackground(new Color(200, 245, 228));
        setVisible(true);
        setLocation(400, 200);
        setSize(300,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //任务栏图标，最小化窗口
        final String iconpath="img/key.png";
        //关键，设定关闭窗口无操作，用于后面的窗体监听事件
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                dispose();
                //获取默认的图片
                Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(iconpath));
                if (SystemTray.isSupported()) {// 判断系统是否支持系统托盘
                    if (systemTray==null) {
                        systemTray=SystemTray.getSystemTray();//创建系统托盘
                        if (trayIcon!=null) {
                            systemTray.remove(trayIcon);
                        }
                    }
                    //创建弹出式菜单
                    PopupMenu popup=new PopupMenu();
                    //主界面选项
//                    MenuItem mainMenuItem=new MenuItem("open");
//                    mainMenuItem.addActionListener(new ActionListener(){
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            setVisible(true);
//                        }
//                    });
                    //退出程序选项
                    MenuItem exitMenuItem=new MenuItem("exit");
                    exitMenuItem.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                    popup.add(exitMenuItem);
                    trayIcon=new TrayIcon(image, "按键监听", popup);
                    trayIcon.setImageAutoSize(true);
                    trayIcon.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // TODO Auto-generated method stub
                            setVisible(true);
                            systemTray.remove(trayIcon);
                        }
                    });
                    try {
                        systemTray.add(trayIcon);
                    } catch (AWTException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        p_menu=new JPanel();
        p_view=new JPanel();
        p_log=new JPanel();
        p_contact=new JPanel();
        p_feed=new JPanel();
        container.setLayout(null);
        p_menu.setLayout(null);
        p_view.setLayout(null);
        p_menu.setBounds(0,0,100,400);
        p_menu.setBackground(new Color(255, 246, 155));
        p_view.setBounds(100,0,200,400);
        p_view.setBackground(new Color(255, 218, 221));

        p_log.setBounds(100,0,200,400);
        p_log.setBackground(new Color(255, 218, 221));

        p_contact.setBounds(100,0,200,400);
        p_contact.setBackground(new Color(255, 218, 221));

        p_feed.setBounds(100,0,200,400);
        p_feed.setBackground(new Color(255, 218, 221));

        container.add(p_menu);
        container.add(p_view);
        container.add(p_log);
        container.add(p_contact);
        container.add(p_feed);

        b_view = new JButton("查看");
        b_log = new JButton("日志");
        b_contact = new JButton("联系");
        b_feed = new JButton("投食");
        b_view.addActionListener(this);
        b_log.addActionListener(this);
        b_contact.addActionListener(this);
        b_feed.addActionListener(this);
        b_view.setBackground(new Color(170, 255, 154));
        b_view.setBounds(0,0,100,50);
        b_log.setBackground(new Color(170, 255, 154));
        b_log.setBounds(0,100,100,50);
        b_contact.setBackground(new Color(170, 255, 154));
        b_contact.setBounds(0,200,100,50);
        b_feed.setBackground(new Color(170, 255, 154));
        b_feed.setBounds(0,300,100,50);
        p_menu.add(b_view);
        p_menu.add(b_log);
        p_menu.add(b_contact);
        p_menu.add(b_feed);

        t_view = new JTextArea(20,20);
        //设置只读
        t_view.setEditable(false);
        //设置自动换行
        t_view.setLineWrap(true);
        //放入jScrollPane中就能显示垂直滚动条了
        js=new JScrollPane(t_view);
        js.setSize(200,400);
        p_view.add(js);

        t_log = new JTextArea(20,20);
        t_log.setEditable(false);
        t_log.setLineWrap(true);
        js2=new JScrollPane(t_log);
        js2.setSize(200,400);
        p_log.add(js2);

        t_contact = new JTextArea();
        t_contact.setEnabled(false);
        t_contact.setLineWrap(true);
        p_contact.add(t_contact);

        //分别设置水平和垂直滚动条总是出现
        js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        js2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        t_view.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,24));
        t_view.setBounds(0,0,200,400);
        t_view.setBackground(new Color(255, 218, 221));
        t_view.append("监听的按键:\n");
        for (String key : KeyConfigMsg.keys) {
            t_view.append(key);
            t_view.append("\n");
        }

        t_log.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,12));
        t_log.setBounds(0,0,200,400);
        t_log.setBackground(new Color(255, 218, 221));
        t_log.append("监听日志:\n");

        t_contact.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,24));
        t_contact.setBounds(0,0,200,400);
        t_contact.setBackground(new Color(255, 218, 221));
        t_contact.setText("\n\n\n");
        t_contact.append("如遇bug请联系:\n");
        t_contact.append("微信:zpq3514\n");
        t_contact.append("电话:18857665495\n");

        p_view.setVisible(false);
        p_log.setVisible(true);
        p_contact.setVisible(false);
        p_feed.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b_view){
            change(1);
        }else if(e.getSource()==b_log){
            change(2);
        }else if(e.getSource()==b_contact){
            change(3);
        }
    }

    public static void change(int i){
        p_view.setVisible(false);
        p_log.setVisible(false);
        p_contact.setVisible(false);
        p_feed.setVisible(false);
        switch (i){
            case 1:p_view.setVisible(true);return;
            case 2:p_log.setVisible(true);return;
            case 3:p_contact.setVisible(true);return;
            case 4:p_feed.setVisible(true);return;
            default:break;
        }
    }
}
