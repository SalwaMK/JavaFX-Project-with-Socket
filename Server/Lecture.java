package salwa.demo4;

import java.io.BufferedReader;
import java.io.IOException;

public class Lecture extends Thread{
    BufferedReader br;

    public Lecture(BufferedReader br) {
        this.br = br;
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                System.out.println(br.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
