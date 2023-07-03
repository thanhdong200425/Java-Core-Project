package Controller.Statistical;

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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Statistical_Controller implements Initializable {

    @FXML
    private TableView<Player> tableViewThongKe;

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

    private Connection c;

    private PreparedStatement pst;

    @FXML
    private void btnBack(ActionEvent event) throws IOException {
        MainController.changeScene("Main-Program.fxml", event);
    }

    @FXML
    private void btnSave(ActionEvent actionEvent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element playersTag = document.createElement("players");
        document.appendChild(playersTag);

        for (Player player : tableViewThongKe.getItems()) {
            Element playerTag = document.createElement("player");
            playersTag.appendChild(playerTag);

            // ID tag
            Element idTag = document.createElement("id");
            idTag.setAttribute("idClub", String.valueOf(player.getIdClub()));
            idTag.setAttribute("idPlayer", String.valueOf(player.getIdPlayer()));
            playerTag.appendChild(idTag);

            // Name tag
            Element nameTag = document.createElement("name");
            nameTag.setTextContent(player.getNamePlayer());
            playerTag.appendChild(nameTag);

            // Shirt number tag
            Element shirtNumberTag = document.createElement("shirtNumber");
            shirtNumberTag.setTextContent(String.valueOf(player.getShirtNumber()));
            playerTag.appendChild(shirtNumberTag);

            // Goals tag
            Element goalsTag = document.createElement("goals");
            goalsTag.setTextContent(String.valueOf(player.getGoal()));
            playerTag.appendChild(goalsTag);

            // Card tag
            Element cardTag = document.createElement("card");
            cardTag.setAttribute("yellowCard", String.valueOf(player.getYellowCard()));
            cardTag.setAttribute("redCard", String.valueOf(player.getRedCard()));
            playerTag.appendChild(cardTag);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("statistical.xml"));
        transformer.transform(source, result);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listPlayer = FXCollections.observableArrayList();
        idPlayerCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("idPlayer"));
        idClubCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("idClub"));
        namePlayerCol.setCellValueFactory(new PropertyValueFactory<Player, String>("namePlayer"));
        goalCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("goal"));
        shirtNumberCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("shirtNumber"));
        yellowCardCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("yellowCard"));
        redCardCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("redCard"));
        tableViewThongKe.setItems(listPlayer);

        display();


    }

    private void display() {
        c = SQLConnect.getConnection();
        try {
            pst = c.prepareStatement("SELECT * FROM player");
            ResultSet rs = pst.executeQuery();
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


}
