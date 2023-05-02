package salwa.demo4;

import java.net.Socket;
import java.util.Date;

public class Account {
    String id_source;
    Socket s;
    //String id_destination;
    Date time_connection;

    public Account(String id_source, Socket s, Date time_connection) {
        this.id_source = id_source;
        this.s = s;
        this.time_connection = time_connection;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id_source='" + id_source + '\'' +
                ", s=" + s +
                ", time_connection=" + time_connection +
                '}';
    }
}
