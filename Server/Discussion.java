package salwa.demo4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.sql.*;

public class Discussion extends Thread{
    Socket s;
    public Discussion(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
        PrintWriter pw = null;  //3ibara copitha fel ram
        pw = new PrintWriter(s.getOutputStream());
        pw.println("What is the agency you want to know its details:  ");
        pw.flush();  //tabaath
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String ligne = null;
        ligne = br.readLine();
        /***from db***/
        Connection conn;
        PreparedStatement ps;
        ResultSet rs = null;
        System.out.println(ligne);
        try {
            conn = ConnectionDB.verify();
            String req = "select * from agencies_info where name= '"+ligne+"'";
            ps = ((Connection) conn).prepareStatement(req);
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Name: "+rs.getString("name")+"\nAddress: "+rs.getString("address")+"\nYear of foundation: "+rs.getInt("year"));
                pw = new PrintWriter(s.getOutputStream());
                String msg1 = "Name: "+rs.getString("name")+"\nAddress: "+rs.getString("address")+"\nYear of foundation: "+rs.getInt("year");
                pw.println(msg1);
                pw.flush();
                // Agency sData = new Agency(rs.getString("name"), rs.getString("address"), rs.getInt("year"));
                // listData.add(sData);
            }
            // tableView.setItems(listData);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }

        //System.out.println("Name: "+rs.getString("name")+"\n Address: "+rs.getString("address")+"\nYear of foundation: "+rs.getInt("year"));

        new Ecriture(pw).start();
        new Lecture(br).start();

        //Server1.listAccount.add(new Account(ligne, s, new Date())); //id=ligne
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
