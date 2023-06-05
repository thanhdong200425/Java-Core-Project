package Controller.Custom;


import Controller.MainController;
import DAO.SQLConnect;
import Model.CLB;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Custom_CLB_Controller implements Initializable {
    @FXML
    private TableView<CLB> tableViewClb;

    @FXML
    private TableColumn<CLB, Integer> pointsCol;

    @FXML
    private TableColumn<CLB, Integer> gamePlayedCol;

    @FXML
    private TableColumn<CLB, Integer> idClbCol;

    @FXML
    private TableColumn<CLB, String> nameClbCol;

    @FXML
    private TableColumn<CLB, Integer> idHlvCol;

    @FXML
    private TextField textFieldId;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldHlv;

    @FXML
    private TextField textFieldPoint;

    @FXML
    private TextField textFieldGame;

    private ObservableList<CLB> listCLB;


    Connection c;

    PreparedStatement pst;

    @FXML
    private void save(ActionEvent actionEvent) throws IOException {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("UPDATE clb SET NAMECLB = ?, IDHLV = ?, POINTS = ?, GAMEPLAYED = ? WHERE IDCLB = ? ;");
            pst.setString(1,textFieldName.getText());
            pst.setInt(2,Integer.parseInt(textFieldHlv.getText()));
            pst.setInt(3,Integer.parseInt(textFieldPoint.getText()));
            pst.setInt(4,Integer.parseInt(textFieldGame.getText()));
            pst.setInt(5,Integer.parseInt(textFieldId.getText()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SAVE");
            alert.setHeaderText("ARE YOU WANT TO SAVE?");

            ButtonType buttonTypeYes = new ButtonType("YES", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("NO", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()== buttonTypeYes){
                pst.executeUpdate();
                refresh();

                textFieldId.setText("");
                textFieldGame.setText("");
                textFieldPoint.setText("");
                textFieldName.setText("");
                textFieldHlv.setText("");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }


    @FXML
    private void add(ActionEvent actionEvent) throws IOException {
        String id, name, hlv, point, game;
        id = textFieldId.getText();
        name = textFieldName.getText();
        hlv = textFieldHlv.getText();
        point = textFieldPoint.getText();
        game = textFieldGame.getText();

        try {
            pst = c.prepareStatement("INSERT INTO clb VALUES (?,?,?,?,?)");
            pst.setInt(1, Integer.parseInt(id));
            pst.setString(2, name);
            pst.setInt(3, Integer.parseInt(hlv));
            pst.setInt(4, Integer.parseInt(point));
            pst.setInt(5, Integer.parseInt(game));
            pst.executeUpdate();
            textFieldId.setText("");
            textFieldName.setText("");
            textFieldHlv.setText("");
            textFieldPoint.setText("");
            textFieldGame.setText("");
            refresh();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    private void back(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Custom.fxml",actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
    }
    private void table() {
        c = SQLConnect.getConnection();
        listCLB = FXCollections.observableArrayList();
        idClbCol.setCellValueFactory(new PropertyValueFactory<CLB, Integer>("id"));
        nameClbCol.setCellValueFactory(new PropertyValueFactory<CLB, String>("name"));
        idHlvCol.setCellValueFactory(new PropertyValueFactory<CLB, Integer>("hlv"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<CLB, Integer>("points"));
        gamePlayedCol.setCellValueFactory(new PropertyValueFactory<CLB, Integer>("gamePlayed"));
        tableViewClb.setItems(listCLB);
        try {
            pst = c.prepareStatement("SELECT * FROM clb");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CLB clb = new CLB();
                clb.setId(Integer.parseInt(rs.getString("IDCLB")));
                clb.setName(rs.getString("NAMECLB"));
                clb.setHlv(Integer.parseInt(rs.getString("IDHLV")));
                clb.setPoints(Integer.parseInt(rs.getString("POINTS")));
                clb.setGamePlayed(Integer.parseInt(rs.getString("GAMEPLAYED")));
                listCLB.add(clb);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    private void delete(ActionEvent event) {
        CLB clb = tableViewClb.getSelectionModel().getSelectedItem();
        listCLB.remove(clb);
        delete();

    }

    @FXML
    private void clear(ActionEvent event) {
        listCLB.clear();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Do you want to clear everything?");

        ButtonType buttonTypeYes = new ButtonType("YES", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("NO", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            deleteAll();
        } else {

        }

    }

    @FXML
    private void update(ActionEvent actionEvent) {
        CLB clb = tableViewClb.getSelectionModel().getSelectedItem();
        textFieldId.setText(String.valueOf(clb.getId()));
        textFieldName.setText(clb.getName());
        textFieldHlv.setText(String.valueOf(clb.getHlv()));
        textFieldPoint.setText(String.valueOf(clb.getPoints()));
        textFieldGame.setText(String.valueOf(clb.getGamePlayed()));
    }

//
//    @FXML
//    public void update(ActionEvent event) {
//        CLB clb = tableView.getSelectionModel().getSelectedItem();
//        idTextField.setText("" + clb.getId());
//        nameTextField.setText(clb.getName());
//        hlvTextField.setText("" + clb.getHlv());
//    }
//
//    @FXML
//    public void save(ActionEvent event) throws IOException {
//        delete(event);
//        add(event);
//    }
//
//
//    @FXML
//    public void DB(ActionEvent actionEvent) throws IOException {
//
//    }

    private void refresh() {
        listCLB.clear();
        try {
            pst = c.prepareStatement("SELECT * FROM clb");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CLB clb = new CLB();
                clb.setId(Integer.parseInt(rs.getString("IDCLB")));
                clb.setName(rs.getString("NAMECLB"));
                clb.setHlv(Integer.parseInt(rs.getString("IDHLV")));
                clb.setPoints(Integer.parseInt(rs.getString("POINTS")));
                clb.setGamePlayed(Integer.parseInt(rs.getString("GAMEPLAYED")));
                listCLB.add(clb);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void deleteAll() {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("TRUNCATE TABLE clb");
            pst.execute();
            refresh();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void delete() {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("DELETE FROM clb where IDCLB = ?");
            pst.setInt(1, Integer.parseInt(textFieldId.getText()));
            pst.execute();
            refresh();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
