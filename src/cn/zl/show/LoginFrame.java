package cn.zl.show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import cn.zl.show.Upload_Client;

public class LoginFrame {
    private static JFrame jFrame;

    private JButton login;
    private JButton cancel;

    private JTextField admin;
    private JPasswordField password;

    private JLabel b1,b2;
    public LoginFrame(){
        jFrame=new JFrame();
        jFrame.setBounds(700,400,425,200);
        jFrame.setVisible(true);
        jFrame.setLayout(new FlowLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login=new JButton("登录");
        cancel=new JButton("取消");
        admin=new JTextField(12);
        password=new JPasswordField(12);
        b1=new JLabel("用户名：");
        b2=new JLabel("密码：");
        jFrame.add(b1);
        jFrame.add(admin);
        jFrame.add(b2);
        jFrame.add(password);
        jFrame.add(login);
        jFrame.add(cancel);

        login.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(admin.getText().trim().equals("zl")&&password.getText().trim().equals("123456")){
                    JOptionPane.showMessageDialog(null,"登录成功");
                    new Upload_Client().setVisible(true);
                }else {
                    JOptionPane.showMessageDialog(null,"登录失败");
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginFrame();
        jFrame.setVisible(true);
    }
}
