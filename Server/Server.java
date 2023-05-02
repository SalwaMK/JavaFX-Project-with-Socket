package salwa.demo4;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    //private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(/*ServerSocket serverSocket,*/ Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try
        {
            //this.serverSocket = serverSocket;
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.printf("Error creating Server!");
        }
    }

    public Server(ServerSocket serverSocket) {
    }

    public void sendMessageToClient(String msg)
    {
        try
        {
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.printf("Error sending message to the client!");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromClient(VBox vb)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected())
                {
                    String msg = null;
                    try {
                        msg = bufferedReader.readLine();
                        CommunicatinControl.addLabel(msg, vb);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.printf("Error receiving message from the client!");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }

                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try
        {
            if(bufferedReader != null)
                bufferedReader.close();
            if(bufferedWriter != null)
                bufferedWriter.close();
            if(socket != null)
                socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
