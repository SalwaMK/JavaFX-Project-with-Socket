package salwa.client1;

import java.io.PrintWriter;
import java.util.Scanner;

public class Ecriture extends Thread{
    PrintWriter pw;

    public Ecriture(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public void run() {
        while(true)
        {
            Scanner sc = new Scanner(System.in);
            pw.println(sc.nextLine());
            pw.flush();
        }
    }
}
