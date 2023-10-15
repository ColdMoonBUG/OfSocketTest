package MyChatRoom.MyServer;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class Server {
     static ArrayList<Socket> list = new ArrayList<>();
     public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10086);
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(".\\src\\MyChatRoom\\User.txt");
        properties.load(fileInputStream);
        fileInputStream.close();
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("有客户端连接");
            new Thread(new MyRunnable(socket,properties)).start();
        }
    }
}
