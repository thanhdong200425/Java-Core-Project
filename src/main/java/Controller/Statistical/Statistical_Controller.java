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
        Player player = tableViewThongKe.getSelectionModel().getSelectedItem();
        int idPlayer = player.getIdPlayer();
        int idClub = player.getIdClub();
        String namePlayer = player.getNamePlayer();
        int shirtNumber = player.getShirtNumber();
        int goal = player.getGoal();
        int redCard = player.getRedCard();
        int yellowCard = player.getYellowCard();
        saveToXML(idPlayer, idClub, namePlayer, shirtNumber, goal, redCard, yellowCard);
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

    private void saveToXML(int idPlayer, int idClub, String namePlayer, int shirtNumber, int goal, int redCard, int yellowCard) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        /*Player tag*/
        Element playerTag = document.createElement("player");
        document.appendChild(playerTag);

        /*Infomation*/
        // ID
        Element idTag = document.createElement("id");
        idTag.setAttribute("idPlayer", String.valueOf(idPlayer));
        idTag.setAttribute("idClub", String.valueOf(idClub));
        playerTag.appendChild(idTag);

        // Name
        Element nameTag = document.createElement("name");
        nameTag.setTextContent(namePlayer);
        playerTag.appendChild(nameTag);

        // Shirt number
        Element shirtNumberTag = document.createElement("shirtNumber");
        shirtNumberTag.setTextContent(String.valueOf(shirtNumber));
        playerTag.appendChild(shirtNumberTag);

        // Goal
        Element goalTag = document.createElement("goals");
        goalTag.setTextContent(String.valueOf(goal));
        playerTag.appendChild(goalTag);

        // Card
        Element card = document.createElement("card");
        card.setAttribute("yellowCard", String.valueOf(yellowCard));
        card.setAttribute("redCard", String.valueOf(redCard));
        playerTag.appendChild(card);

        /*Save to file*/
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult file = new StreamResult(new File("statistical.xml"));
        transformer.transform(domSource, file);
    }
}
