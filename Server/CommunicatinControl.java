package salwa.demo4;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class CommunicatinControl implements Initializable {
    @FXML
    private Button send;
    @FXML
    private TextField tf_msg;
    @FXML
    private ScrollPane sp_main;
    @FXML
    private VBox vb_msg;
    /*@FXML
    private Server server;*/

    public CommunicatinControl() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {


    }
    ServerSocket serverSocket = new ServerSocket(9001);
    Socket socket  = new Socket("localhost", 9001);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    Server server = new Server(socket, bufferedReader, bufferedWriter);
    public void send()
    {
        try
        {
            //ServerSocket server = new ServerSocket(9001);
            //server = new Server (new ServerSocket(9001));
            Server server = new Server(socket, bufferedReader, bufferedWriter);
            System.out.println("server waiting ... ");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            String msg = tf_msg.getText();
            if(!msg.isEmpty())
            {
                HBox hb = new HBox();
                hb.setAlignment(Pos.CENTER_RIGHT);
                hb.setPadding(new Insets(5, 5, 5, 10));

                Text text = new Text(msg);
                TextFlow textFlow = new TextFlow(text);

                //String css = getClass().getResource("styles.css").toExternalForm();
                //scene.getStylesheets().add(css);
                //textFlow.getStyleClass().add("text-flow");

                    /*textFlow.setStyle("-fx-color: rgb(239, 242, 255)" +
                            "-fx-background-color: rgb(15, 125, 242)"+
                            "-fx-background-radius: 20px");*/

                textFlow.setPadding(new Insets(5, 10,5, 10));
                text.setFill(Color.color(0.934, 0.945, 0.996));

                hb.getChildren().add(textFlow);
                vb_msg.getChildren().add(hb);

                server.sendMessageToClient(msg);
                tf_msg.clear();

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.printf("Error creating Server!");
        }

        /*vb_msg.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sp_main.setVvalue((Double) t1);
            }
        });*/
        vb_msg.heightProperty().addListener((observableValue, number, t1) -> sp_main.setVvalue((Double) t1));

        server.receiveMessageFromClient(vb_msg);

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String msg = tf_msg.getText();
                if(!msg.isEmpty())
                {
                    HBox hb = new HBox();
                    hb.setAlignment(Pos.CENTER_RIGHT);
                    hb.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(msg);
                    TextFlow textFlow = new TextFlow(text);

                    //String css = getClass().getResource("styles.css").toExternalForm();
                    //scene.getStylesheets().add(css);
                    //textFlow.getStyleClass().add("text-flow");

                    /*textFlow.setStyle("-fx-color: rgb(239, 242, 255)" +
                            "-fx-background-color: rgb(15, 125, 242)"+
                            "-fx-background-radius: 20px");*/

                    textFlow.setPadding(new Insets(5, 10,5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hb.getChildren().add(textFlow);
                    vb_msg.getChildren().add(hb);

                    server.sendMessageToClient(msg);
                    tf_msg.clear();

                }
            }
        });
    }
    public static void addLabel(String msgClient, VBox vb)
    {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(msgClient);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-background-color: rgb(233, 233, 235"+
                "-fx-background-radius: 20px");

        textFlow.setPadding(new Insets(5, 10,5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vb.getChildren().add(hBox);
            }
        });
    }
}
