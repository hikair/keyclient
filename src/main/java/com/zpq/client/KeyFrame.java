package com.zpq.client;

import com.zpq.utilts.KeyConfigMsg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * @author 35147
 */
public class KeyFrame extends JFrame implements ActionListener {

    private JButton register,view,button;
    private JPanel panel,panel1,panel2,p1,p2,p3,p4;
    private Container container;
    private JTextField jTextField;
    private JTextArea jTextArea,viewTextArea;
    public KeyFrame(){
        super("Test Panel");
        //获得一个容器
        container = getContentPane();
        container.setBackground(new Color(200, 245, 228));
        setTitle("键盘监听");
        setVisible(true);
        setLocation(400, 200);
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        //左侧功能区域
        panel = new JPanel();
        //右侧区域
        panel1 = new JPanel();
        panel2 = new JPanel();

        container.setLayout(null);
        panel.setLayout(null);
        //panel1.setLayout(null);


        //panel设置坐标，相对于frame
        panel.setBounds(60,40,100,470);
        panel.setBackground(new Color(245, 245, 245));

        panel1.setBounds(180,40,370,470);
        panel1.setBackground(new Color(245, 245, 245));

        panel2.setBounds(180,40,370,470);
        panel2.setBackground(new Color(245, 245, 245));
        panel2.setVisible(false);
        panel1.setVisible(false);


        //录入按钮区域
        p1 = new JPanel();
        p1.setBounds(0,0,100,200);
        p1.setBackground(new Color(245, 245, 245));
        //查看按钮区域
        p2 = new JPanel();
        p2.setBounds(0,270,100,200);
        p2.setBackground(new Color(245, 245, 245));
        panel.add(p1);
        panel.add(p2);

        container.add(panel);
        container.add(panel1);
        container.add(panel2);
        container.setVisible(true);

        //画按钮
        register = new JButton("录入");
        view = new JButton("查看");
        register.addActionListener(this);
        view.addActionListener(this);

        p1.add(register);
        p2.add(view);

        jTextField=new JTextField(10);
        jTextArea=new JTextArea("输入你要监听的按键,每次输入一个。\n目前支持：A-Z、0-9\n" +
                "enter、backspace(退格)、\ndel(删除)、tab(制表)、shift\n" +
                "、ctrl、alt、caps、（空格）、esc、pageup、pagedown\n" +
                "、end、home、leftarrow、rightarrow、downarrow\n" +
                "、insert、num、;、-_、/、`、[、]、|");
        viewTextArea = new JTextArea();
        panel2.add(viewTextArea);
        button = new JButton("确认");
        button.addActionListener(this);

        p3 = new JPanel();
        p4 = new JPanel();
        p3.setBounds(0,270,100,50);
        p4.setBounds(0,500,100,150);

        p3.add(jTextField);
        p3.add(button);
        p4.add(jTextArea);
        panel1.add(p3);

        panel1.add(p4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==register){
            System.out.println("register");
            panel2.setVisible(false);
            panel1.setVisible(true);
        }
        if(e.getSource()==view){
            System.out.println("view");
            panel1.setVisible(false);
            panel2.setVisible(true);
            //查看所有按键监听

            viewTextArea.setText("");
            List<String> keys = KeyConfigMsg.keys;
            for (String key : keys) {
                viewTextArea.append(key+"\n");
            }
        }
        //确定按钮触发事件
        if(e.getSource()==button){
            //拿到用户输入的值
            String text = jTextField.getText();
            System.out.println(text);
        }
    }

}

class MyDialogDemo extends JDialog{
    public MyDialogDemo(String msg){
        this.setVisible(true);
        this.setBounds(100,100,500,500);
        Container container = this.getContentPane();
        container.setLayout(null);
        JLabel l = new JLabel(msg);
        l.setBounds(110,110,200,200);
        container.add(l);
    }
}
