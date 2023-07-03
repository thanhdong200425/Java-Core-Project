package Controller.Custom;

import Controller.MainController;
import DAO.SQLConnect;
import Model.Player;
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

public class Custom_PLAYER_Controller implements Initializable {

    @FXML
    private TableView<Player> tableViewTuyChinhPLAYER;

    @FXML
    private TableColumn<Player, Integer> idPlayerCol;

    @FXML
    private TableColumn<Player, Integer> idClubCol;

    @FXML
    private TableColumn<Player, String> namePlayerCol;

    @FXML
    private TableColumn<Player, Integer> shirtNumberCol;

    @FXML
    private TableColumn<Player, Integer> goalCol;

    @FXML
    private TableColumn<Player, Integer> yellowCardCol;

    @FXML
    private TableColumn<Player, Integer> redCardCol;

    private ObservableList<Player> listPlayer;

    @FXML
    private TextField textField_IdPlayer;

    @FXML
    private TextField textField_IdClub;

    @FXML
    private TextField textField_NamePlayer;

    @FXML
    private TextField textField_ShirtNumber;

    @FXML
    private TextField textField_Goal;

    @FXML
    private TextField textField_YellowCard;

    @FXML
    private TextField textField_RedCard;


    private Connection c;

    private PreparedStatement pst;

    private ResultSet rs;


    @FXML
    private void back(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Custom.fxml", actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listPlayer = FXCollections.observableArrayList();
        idClubCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("idPlayer"));
        idPlayerCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("idClub"));
        namePlayerCol.setCellValueFactory(new PropertyValueFactory<Player, String>("namePlayer"));
        goalCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("goal"));
        shirtNumberCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("shirtNumber"));
        yellowCardCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("yellowCard"));
        redCardCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("redCard"));
        tableViewTuyChinhPLAYER.setItems(listPlayer);
        display();
    }

    private void display() {
        c = SQLConnect.getConnection();
        try {
            pst = c.prepareStatement("SELECT * FROM player");
            rs = pst.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setIdPlayer(Integer.parseInt(rs.getString("IDPLAYER")));
                player.setNamePlayer(rs.getString("NAMEPLAYER"));
                player.setGoal(Integer.parseInt(rs.getString("GOAL")));
                player.setYellowCard(Integer.parseInt(rs.getString("YELLOWCARD")));
                player.setRedCard(Integer.parseInt(rs.getString("REDCARD")));
                player.setShirtNumber(Integer.parseInt(rs.getString("SHIRTNUMBER")));
                player.setIdClub(Integer.parseInt(rs.getString("IDCLB")));
                listPlayer.add(player);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void add(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();
        try {
            pst = c.prepareStatement("INSERT INTO player VALUES (?,?,?,?,?,?,?)");
            pst.setInt(1, Integer.parseInt(textField_IdPlayer.getText()));
            pst.setInt(2, Integer.parseInt(textField_IdClub.getText()));
            pst.setString(3, textField_NamePlayer.getText());
            pst.setInt(4, Integer.parseInt(textField_ShirtNumber.getText()));
            pst.setInt(5, Integer.parseInt(textField_Goal.getText()));
            pst.setInt(6, Integer.parseInt(textField_YellowCard.getText()));
            pst.setInt(7, Integer.parseInt(textField_RedCard.getText()));
            pst.executeUpdate();

            refresh();

            textField_IdPlayer.setText("");
            textField_IdClub.setText("");
            textField_NamePlayer.setText("");
            textField_Goal.setText("");
            textField_ShirtNumber.setText("");
            textField_RedCard.setText("");
            textField_YellowCard.setText("");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void refresh() {
        c = SQLConnect.getConnection();
        listPlayer.clear();
        try {
            pst = c.prepareStatement("SELECT * FROM player");
            rs = pst.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setIdPlayer(Integer.parseInt(rs.getString("IDPLAYER")));
                player.setIdClub(Integer.parseInt(rs.getString("IDCLB")));
                player.setNamePlayer(rs.getString("NAMEPLAYER"));
                player.setGoal(Integer.parseInt(rs.getString("GOAL")));
                player.setYellowCard(Integer.parseInt(rs.getString("YELLOWCARD")));
                player.setRedCard(Integer.parseInt(rs.getString("REDCARD")));
                player.setShirtNumber(Integer.parseInt(rs.getString("SHIRTNUMBER")));

                listPlayer.add(player);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void clear(ActionEvent actionEvent) {
        listPlayer.clear();
        try {
            pst = c.prepareStatement("TRUNCATE TABLE player");
            pst.execute();
            refresh();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void save(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();
        try {
            pst = c.prepareStatement("UPDATE player SET idplayer = ?, idclb = ?, nameplayer = ?, shirtnumber = ?, goal = ?, yellowcard = ?,"
                    + " redcard = ? WHERE idplayer = ?");
            pst.setInt(1, Integer.parseInt(textField_IdPlayer.getText()));
            pst.setInt(2, Integer.parseInt(textField_IdClub.getText()));
            pst.setString(3, textField_NamePlayer.getText());
            pst.setInt(4, Integer.parseInt(textField_ShirtNumber.getText()));
            pst.setInt(5, Integer.parseInt(textField_Goal.getText()));
            pst.setInt(6, Integer.parseInt(textField_YellowCard.getText()));
            pst.setInt(7, Integer.parseInt(textField_RedCard.getText()));
            pst.setInt(8, Integer.parseInt(textField_IdPlayer.getText()));
            pst.execute();
            refresh();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void update(ActionEvent actionEvent) {
        Player player = tableViewTuyChinhPLAYER.getSelectionModel().getSelectedItem();
        textField_IdPlayer.setText(String.valueOf(player.getIdPlayer()));
        textField_IdClub.setText(String.valueOf(player.getIdClub()));
        textField_NamePlayer.setText(player.getNamePlayer());
        textField_Goal.setText(String.valueOf(player.getGoal()));
        textField_ShirtNumber.setText(String.valueOf(player.getShirtNumber()));
        textField_YellowCard.setText(String.valueOf(player.getYellowCard()));
        textField_RedCard.setText(String.valueOf(player.getRedCard()));
    }

    @FXML
    private void delete(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();
        try {
            pst = c.prepareStatement("DELETE FROM player WHERE IDPLAYER = ? AND IDCLB = ?");
            pst.setInt(1, Integer.parseInt(textField_IdPlayer.getText()));
            pst.setInt(2, Integer.parseInt(textField_IdClub.getText()));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cofirm Delete");
            alert.setContentText("Are you sure to delete?");

            ButtonType buttonTypeYes = new ButtonType("EXIT", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("NO", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                pst.executeUpdate();
                refresh();
            } else if (result.get().getButtonData() == ButtonBar.ButtonData.NO) {

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }


}
