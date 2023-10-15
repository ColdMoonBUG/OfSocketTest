package MyChatRoom.MyServer;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
public class MyRunnable implements Runnable{
    Socket socket;
    Properties properties;
    public MyRunnable(Socket socket, Properties properties) {
        this.socket = socket;
        this.properties = properties;
    }
    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String UserChoose = bufferedReader.readLine();
                switch (UserChoose){
                    case "login"-> login(bufferedReader);
                    case "register"->System.out.println("一个用户正在进行注册");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void login(BufferedReader bufferedReader) throws IOException {
        System.out.println("一个用户正在登录");
        String UserInput = bufferedReader.readLine();
        //接受客户端发送的:例：username=123&password=123
        String[] UserInputArr = UserInput.split("&");
        String[] UserInputNameArr = UserInputArr[0].split("=");
        String UserInputName = UserInputNameArr[1];
        String[] UserInputPassWordArr = UserInputArr[1].split("=");
        String UserInputPassWord = UserInputPassWordArr[1];
        System.out.println("用户输入的用户名是："+UserInputName+"\t"+UserInputPassWord);
        if (properties.containsKey(UserInputName)){  //判断用户名是否存在(判断的是客户输入的用户名的key是否存在properties的key中)
            String rightPassword = (String) properties.get(UserInputName); //(如何用户名存在,那就拿key对应的value值，此value值便是正确的密码)
            if (rightPassword.equals(UserInputPassWord)){
                WriteClientMessage("1");
                Server.list.add(socket);
                Receive(bufferedReader,UserInputName);
            } else{
                WriteClientMessage("2");
            }
        }else {
            //用户名不存在
            WriteClientMessage("3");
        }
    }
    private void Receive(BufferedReader bufferedReader,String UserName) throws IOException {
        while (true) {
            String message = bufferedReader.readLine();
            System.out.println(UserName+"发送一条消息："+message);
            for (Socket socket1 : Server.list) {
                WriteClientMessage(socket1,UserName+"发送一条消息："+message);
            }
        }
    }
    public void WriteClientMessage(String message) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    public void WriteClientMessage(Socket socket,String message) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
}
