
package salwa.demo4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AgencyController implements Initializable {
    @FXML
    private Button vbtn;
    @FXML
    private TextField agencyName;
    @FXML
    private TextField agencyAddress;
    @FXML
    private ComboBox agencyYear;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button comBtn;

    @FXML
    private TableView<Agency> tableView; // Specify type for TableColumn
    @FXML
    private TableColumn<Agency, String> colName; // Specify type for TableColumn
    @FXML
    private TableColumn<Agency, String> colAddress; // Specify type for TableColumn
    @FXML
    private TableColumn<Agency, Integer> colYear; // Specify type for TableColumn

    private Connection conn;
    private PreparedStatement ps; // Prepare
    private Statement st; // State
    private ResultSet rs; // Result
    private Alert alert;

    private int[] yearList = {1950, 1960, 1970, 1980, 1990, 2000, 2010, 2020};
    public void agencyYearList(){
        List<Integer> yList = new ArrayList<Integer>();
        for(int data: yearList)
            yList.add(data);

        ObservableList listData = FXCollections.observableArrayList(yList);
        agencyYear.setItems(listData);
    }

    public void agencyAddBtn() throws SQLException, ClassNotFoundException {
        String insertData = "insert into agencies_info (name, address, year) "+"values(?,?,?,?)";

        conn = ConnectionDB.verify();

        if(agencyName.getText().isEmpty() || agencyAddress.getText().isEmpty() || agencyYear.getSelectionModel().getSelectedItem() == null)
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
                String checkData = "select name from agencies_info where name= '"+agencyName.getText()+"'";

                ps = conn.prepareStatement(checkData);
                rs = ps.executeQuery();

                if(rs.next())
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Agency Name: "+agencyName.getText()+" is already taken");
                    alert.showAndWait();
                }
                else
                {
                    insertData = "insert into agencies_info (name, address, year) " + "values(?,?,?)";
                    ps = conn.prepareStatement(insertData);
                    ps.setString(1, agencyName.getText());
                    ps.setString(2, agencyAddress.getText());
                    ps.setString(3, (String) agencyYear.getSelectionModel().getSelectedItem().toString());

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added");
                    alert.showAndWait();

                    ps.executeUpdate();
                    agencyShow();
                    agencyClearBtn();
                }

            }
            catch(Exception e) {e.printStackTrace();
                //System.out.printf(e.toString());
                }
        }
    }

    public void agencyClearBtn()
    {
        agencyName.setText("");
        agencyAddress.setText("");
        agencyYear.getSelectionModel().getSelectedItem();
    }

    public void agencyUpdateBtn() throws SQLException, ClassNotFoundException {
        conn = ConnectionDB.verify();
        try
        {
            if(agencyName.getText().isEmpty())
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields!");
                alert.showAndWait();
            }
            else
            {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE agency name: "+agencyName.getText()+"?");
                //alert.showAndWait();
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK))
                {
                    //String updateData = "update agencies_info set name = '"+agencyName.getText()+"', address = '"+agencyAddress.getText()+"', year = '"+agencyYear.getValue() +"'";
                    String updateData = "update agencies_info set address = '"+agencyAddress.getText()+"', year = '"+agencyYear.getValue() +"' where name = '"+agencyName.getText()+"'";
                    ps = conn.prepareStatement(updateData);
                    //ps.setInt(1, 1);   //
                    ps.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");

                    agencyShow();
                    agencyClearBtn();
                }
                else
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Canceled!");
                    alert.showAndWait();
                }
            }
        }
        catch (Exception e) { e.printStackTrace();}
    }

    public void agencyDeleteBtn() throws SQLException, ClassNotFoundException {
        conn = ConnectionDB.verify();
        try
        {
            if(agencyName.getText().isEmpty())
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields!");
                alert.showAndWait();
            }
            else
            {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete agency name: "+agencyName.getText()+"?");
                //alert.showAndWait();
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK))
                {
                    String deleteData = "delete from agencies_info where name ='"+agencyName.getText()+"'";
                    ps = conn.prepareStatement(deleteData);
                    //ps.setInt(1, 1);   //
                    ps.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deleted!");

                    agencyShow();
                    agencyClearBtn();
                }
                else
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Canceled!");
                    alert.showAndWait();
                }
            }
        }
        catch (Exception e) { e.printStackTrace();}
    }

    //studentData: agency, studentListData: fct: agencyListData, listData:lisData
    public ObservableList<Agency> listData = FXCollections.observableArrayList();
    private ObservableList<Agency> agencyData()
    {
        try {
            conn = ConnectionDB.verify();
            String req = "select * from agencies_info";
            ps = conn.prepareStatement(req);
            rs = ps.executeQuery();
            while (rs.next()) {
                Agency sData = new Agency(rs.getString("name"), rs.getString("address"), rs.getInt("year"));
                listData.add(sData);
            }
            tableView.setItems(listData);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }

        return listData;
    }

    public ObservableList<Agency> agencyData;
    public void agencyShow()
    {
        agencyData= agencyData();

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        tableView.setItems(agencyData);
    }

    public void agencySelectData()
    {
        Agency sData = tableView.getSelectionModel().getSelectedItem();
        int num = tableView.getSelectionModel().getSelectedIndex();

        if((num-1) < - 1) return ;

        agencyName.setText(String.valueOf(sData.getName()));
        agencyAddress.setText(String.valueOf(sData.getAddress()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        agencyYearList();
        agencyShow();

    }

    //Communication button;

    public void com(ActionEvent e) throws IOException
    {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Communication.fxml"));
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Communication.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Server");
        stage.show();
    }

    public void vehicules(ActionEvent e) throws IOException
    {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Vehicules.fxml"));
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Vehicules.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Autosyntirisi");
        stage.show();
    }

    /*public void agencies(ActionEvent e) throws IOException
    {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        //Parent root = FXMLLoader.load(getClass().getResource("Communication.fxml"));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Autosyntirisi");
        stage.show();
    }*/


    /***********/
    public void go()
    {
        System.out.println("Server launched");
        try {
            ServerSocket server = new ServerSocket(9001);
            System.out.println("Server waiting ...");
            int nbClient=0;
            while(nbClient<3)
            {
                Socket s = server.accept();
                System.out.println("Client is connected");
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

}
