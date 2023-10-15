package MyChatRoom.MyClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MyClientRunnable implements Runnable{
    Socket socket;
    public MyClientRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = bufferedReader.readLine();
                System.out.println(message);
                System.out.println("=============================");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
