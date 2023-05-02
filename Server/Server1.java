package salwa.demo4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class Server1 {
    public static void main(String[] args)
    {
        System.out.println("Lancement d'un Serveur");
        try {
            ServerSocket server = new ServerSocket(9001);
            System.out.println("en écoute ...");
            int nbClient=0;
            while(nbClient<3)
            {
                Socket s = server.accept();
                System.out.println("un client est connecté");
                nbClient++;
                Discussion d = new Discussion(s);
                d.start();
            }
        }
        catch(SocketException e)
        {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void diffusion(String ch) throws IOException {
        for(int i=0;i<listAccount.size();i++)
        {
            PrintWriter pw = new PrintWriter(listAccount.get(i).s.getOutputStream());
            pw.println(ch);
        }
    }

    public static ArrayList<Account> listAccount = new ArrayList<Account>();
}