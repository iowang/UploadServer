import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Upload_Client {
    public static void main(String[] args) throws IOException {
//        建立连接
        Socket socket = new Socket("localhost", 54321);
//        获取和客户端的输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        Scanner sc = new Scanner(System.in);
//        选择要上传的文件
        String filename;
//        判断文件是否为文件夹，是否存在
        while (true) {
            System.out.println("请输入要上传的文件夹");
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
}
