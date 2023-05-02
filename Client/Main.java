package salwa.client1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Client launched");
        try
        {
            Socket s = new Socket("127.0.0.1", 9001);
            Scanner sc = new Scanner(System.in);
            System.out.println("I'm the client, and i'm connected to the server");
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String ligne = br.readLine();
            System.out.println(ligne);
            String msg = sc.nextLine();
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            pw.println(msg);
            pw.flush();

            /***/
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String msg1 = null;
            msg1 = br.readLine();
            System.out.println(msg1);


            new Lecture(br).start();
            new Ecriture(pw).start();
        }
        catch(SocketException e)
        {
            System.out.println(e.getMessage());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}