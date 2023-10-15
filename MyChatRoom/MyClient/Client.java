package MyChatRoom.MyClient;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",10086);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=========欢迎来到LYChatRoom=========");
            System.out.println("请选择您要操作的功能序号");
            System.out.println("1-登录");
            System.out.println("2-注册");
            System.out.println("3-退出");
            int Choose = scanner.nextInt();
            switch (Choose){
                case 1:
                    Login(socket);
                    continue;
                case 2:
                    System.out.println("请输入您要注册的用户名");
                    String inputregisterName = scanner.nextLine();
                    System.out.println("请输入您要注册的密码");
                    String inputregisterPassword = scanner.nextLine();
                    continue;
                case 3:
                    break;
                default:
                    System.out.println("没有此选项,请输入功能序号");
            }
        }
    }
    public static void Login(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名");
        String inputLoginName = scanner.nextLine();
        System.out.println("请输入密码");
        String inputLoginPassword = scanner.nextLine();

        StringBuilder stringBuilder = new StringBuilder();
        //向服务器发送username=123&password=123
        stringBuilder.append("username=").append(inputLoginName).append("&password=").append(inputLoginPassword);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        bufferedWriter.write("login".toString());
        bufferedWriter.newLine();
        bufferedWriter.flush();

        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.newLine();
        bufferedWriter.flush();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String message = bufferedReader.readLine();
        System.out.println(message);
        if (message.equals("1")){
            new Thread(new MyClientRunnable(socket)).start();  //启动线程
            SendMessage2Server(bufferedWriter);
        }else if (message.equals("2")){
            System.out.println("密码错误");
        }else {
            System.out.println("用户不存在");
        }
    }
    public static void SendMessage2Server(BufferedWriter bufferedWriter) throws IOException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("你要说啥");
            String message = scanner.nextLine();
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }
}

