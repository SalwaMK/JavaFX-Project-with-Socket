package salwa.demo4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import salwa.demo4.Admin;
import salwa.demo4.LoginControl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Login {
    @FXML
    TextField username;    //fx:id
    @FXML
    TextField fullname;
    @FXML
    TextField position;
    @FXML
    PasswordField password;
    @FXML
    Button btnSignin;
    @FXML
    Label lblMsg;
    @FXML
    ComboBox year;
    Connection conn;
    Alert alert;
    PreparedStatement ps;
    ResultSet rs;


    Admin admin = new Admin();
    LoginControl lc = new LoginControl();
    public void isSigned(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        admin.setUsername(username.getText());
        admin.setPwd(password.getText());

        if(lc.exist(admin))
        {
            Node node = (Node) e.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Autosyntirisi");
            stage.show();
        }
        else lblMsg.setText("Data entered is wrong! Try again");
    }

    public void signUp(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Autosyntirisi");
        stage.show();

    }

    public void signIn(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Autosyntirisi");
        stage.show();
    }

    public void add() throws SQLException, ClassNotFoundException {
        String insertData = "insert into admins (username, password) "+"values(?,?)";

        conn = ConnectionDB.verify();

        if(fullname.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty() || position.getText().isEmpty())
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields!");
            alert.showAndWait();
        }
        else
        {
            try
            {
                String checkData = "select username from admins where username= '"+username.getText()+"'";

                ps = conn.prepareStatement(checkData);
                rs = ps.executeQuery();

                if(rs.next())
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Agency Name: "+username.getText()+" is already taken");
                    alert.showAndWait();
                }
                else
                {
                    insertData = "insert into admins (username, password) " + "values(?,?)";
                    ps = conn.prepareStatement(insertData);
                    ps.setString(1, fullname.getText());
                    ps.setString(2, username.getText());
//                    ps.setString(3, password.getText());
//                    ps.setString(4, position.getText());

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added");
                    alert.showAndWait();

                    ps.executeUpdate();
                }

            }
            catch(Exception e) {e.printStackTrace();
                //System.out.printf(e.toString());
            }
        }
    }

}

