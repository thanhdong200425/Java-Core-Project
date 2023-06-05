package Controller.Custom;

import Controller.MainController;
import DAO.SQLConnect;
import Model.HLV;
import com.do_an_main.Main_Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Custom_HLV_Controller implements Initializable {

    @FXML
    private TableView<HLV> tableViewHLV;

    @FXML
    private TableColumn<HLV, Integer> idHlvCol;

    @FXML
    private TableColumn<HLV, String> nameHlvCol;

    @FXML
    private TableColumn<HLV, Integer> idClbCol;

    @FXML
    private TextField textField_IdClb;

    @FXML
    private TextField textField_NameHlv;

    @FXML
    private TextField textField_IdHlv;

    private ObservableList<HLV> listHlv;

    private Connection c;

    private PreparedStatement pst;

    private ResultSet rs;

    @FXML
    private void back(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Custom.fxml",actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c = SQLConnect.getConnection();
        listHlv = FXCollections.observableArrayList();
        idClbCol.setCellValueFactory(new PropertyValueFactory<HLV, Integer>("idClb"));
        nameHlvCol.setCellValueFactory(new PropertyValueFactory<HLV, String>("nameHlv"));
        idHlvCol.setCellValueFactory(new PropertyValueFactory<HLV, Integer>("idHlv"));
        tableViewHLV.setItems(listHlv);

        display();
    }

    private void display() {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("SELECT * FROM coach");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                HLV hlv = new HLV();
                hlv.setIdHlv(rs.getInt("IDHLV"));
                hlv.setIdClb(rs.getInt("IDCLB"));
                hlv.setNameHlv(rs.getString("NAMEHLV"));
                listHlv.add(hlv);
            }
            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void add(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("INSERT INTO coach VALUES (?,?,?)");
            pst.setInt(1, Integer.parseInt(textField_IdHlv.getText()));
            pst.setString(2, textField_NameHlv.getText());
            pst.setInt(3, Integer.parseInt(textField_IdClb.getText()));
            pst.executeUpdate();
            refresh();
            SQLConnect.closeConnection(c);


            textField_IdHlv.setText("");
            textField_IdClb.setText("");
            textField_NameHlv.setText("");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void refresh() {
        c = SQLConnect.getConnection();
        listHlv.clear();

        try {
            pst = c.prepareStatement("SELECT * FROM coach");
            rs = pst.executeQuery();
            while (rs.next()) {
                HLV hlv = new HLV();
                hlv.setIdHlv(Integer.parseInt(rs.getString("IDHLV")));
                hlv.setIdClb(Integer.parseInt(rs.getString("IDCLB")));
                hlv.setNameHlv(rs.getString("NAMEHLV"));
                listHlv.add(hlv);

            }
            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    @FXML
    private void clearAll(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();
        listHlv.clear();

        try {
            pst = c.prepareStatement("TRUNCATE TABLE coach");
            pst.executeUpdate();
            refresh();
            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void update(ActionEvent actionEvent) {
        HLV hlv = tableViewHLV.getSelectionModel().getSelectedItem();
        textField_IdHlv.setText(String.valueOf(hlv.getIdHlv()));
        textField_IdClb.setText(String.valueOf(hlv.getIdClb()));
        textField_NameHlv.setText(hlv.getNameHlv());



    }

    @FXML
    private void save(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("UPDATE coach SET IDHLV = ?, NAMEHLV = ?, IDCLB = ? WHERE IDHLV = ?");
            pst.setInt(1,Integer.parseInt(textField_IdHlv.getText()));
            pst.setString(2,textField_NameHlv.getText());
            pst.setInt(3,Integer.parseInt(textField_IdClb.getText()));
            pst.setInt(4,Integer.parseInt(textField_IdHlv.getText()));
            pst.executeUpdate();
            refresh();
            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    private void delete(ActionEvent actionEvent){
        c = SQLConnect.getConnection();
        try {
            pst = c.prepareStatement("DELETE FROM coach WHERE IDHLV = ?");
            pst.setInt(1, Integer.parseInt(textField_IdHlv.getText()));
            pst.executeUpdate();
            refresh();
            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
