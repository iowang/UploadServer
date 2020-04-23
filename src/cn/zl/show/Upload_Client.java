package cn.zl.show;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Properties;


import javax.swing.*;

public class Upload_Client extends JFrame {
    private static int ClientPort;
    private static String ClientIp;
    private static JButton button1;
    private static JButton button2;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("file.properties"));
            ClientIp = properties.getProperty("clientIp");
            ClientPort = Integer.parseInt(properties.getProperty("clientPort"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Upload_Client() {
        button1 = new JButton("连接服务器");
        button2 = new JButton("传送文件");
        this.setBounds(700, 400, 400, 300);
        this.setLayout(new FlowLayout());
        this.add(button1);
        this.add(button2);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "服务器连接成功");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jFileChooser.showDialog(new Label(), "选择文件");
                Socket socket = null;
                try {
                    socket = new Socket(ClientIp, ClientPort);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintStream ps = new PrintStream(socket.getOutputStream());
                    File file = jFileChooser.getSelectedFile();
                    if (!file.exists()) {
                        System.out.println("路径输入有误，请重新输入");
                    } else if (file.isDirectory()) {
                        JOptionPane.showMessageDialog(null,"不可以上传文件夹哦，请重新上传");
                    } else {
                        ps.println(file.getName());
                    }
                    String filename = file.getAbsolutePath();
                    String result = br.readLine();
                    System.out.println(result);
                    if ("文件已经存在".equals(result)) {
                        JOptionPane.showMessageDialog(null,"文件已经存在");
                        socket.close();
                        return;
                    } else {
                        FileInputStream fis = new FileInputStream(filename);
                        byte[] bytes = new byte[1024];
                        int len;
                        while ((len = fis.read(bytes, 0, bytes.length)) != -1) {
                            ps.write(bytes, 0, len);
                            ps.flush();
                        }
                        fis.close();
                    }

                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
//        获取和客户端的输入流

            }
        });
    }
    /*  public static void main(String[] args) throws IOException {
//        建立连接
        Socket socket = new Socket(ClientIp, ClientPort);
//        获取和客户端的输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        Scanner sc = new Scanner(System.in);
//        选择要上传的文件
        String filename;
//        判断文件是否为文件夹，是否存在
        while (true) {
            System.out.println("请输入要上传的文件");
            filename = sc.nextLine();
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("路径输入有误，请重新输入");
            } else if (file.isDirectory()) {
                System.out.println("不可以上传文件夹哦，请重新输入");
            } else {
                ps.println(file.getName());
                break;
            }
        }
//        等待服务器返回结果，判断是否需要上传
        String result = br.readLine();
        System.out.println(result);
        if ("文件已经存在".equals(result)) {
            socket.close();
            return;
        } else {
            FileInputStream fis = new FileInputStream(filename);
            byte[] bytes = new byte[1024];
            int len;
            while ((len=fis.read(bytes,0,bytes.length))!=-1){
                ps.write(bytes,0,len);
                ps.flush();
            }
            fis.close();
        }
        socket.close();
    }

     */
}
