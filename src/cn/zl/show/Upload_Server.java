package cn.zl.show;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Upload_Server {

    private static int ServerPort;

    static {
        Properties properties=new Properties();
        try {
            properties.load(new FileReader("file.properties"));
            ServerPort= Integer.parseInt(properties.getProperty("serverPort"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(ServerPort);
        System.out.println("服务器启动=================");
        while (true) {
            final Socket socket = serverSocket.accept();
            new Thread() {
                @Override
                public void run() {
                    try {
//                        建立IO流
                        InputStream in = socket.getInputStream();
                        BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
                        PrintStream ps = new PrintStream(socket.getOutputStream());
//                        获取上传文件名
                        String filename = buffer.readLine();
//                        建立本地的文件
                        File dir = new File("Upload");
                        dir.mkdir();
                        File file = new File(dir, filename);
                        if (file.exists()) {
                            ps.println("文件已经存在");
                            socket.close();
                            return;
                        } else {
                            ps.println("文件不存在");
                        }
//                        保存文件写入file中
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] bytes = new byte[1024];
                        int len;
                        while ((len = in.read(bytes, 0, bytes.length)) != -1) {
                            fos.write(bytes, 0, len);
                            fos.flush();
                        }
                        fos.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }
}
