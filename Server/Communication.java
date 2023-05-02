package salwa.demo4;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Communication {
    Stage stage;

    public void com() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Communication.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
