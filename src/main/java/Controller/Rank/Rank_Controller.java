package Controller.Rank;

import Controller.MainController;
import DAO.SQLConnect;
import Model.CLB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Rank_Controller implements Initializable {

    @FXML
    private TableView<CLB> tableViewBXH;


    @FXML
    private TableColumn<CLB, Integer> rankCol;

    @FXML
    private TableColumn<CLB, String> nameClbCol;

    @FXML
    private TableColumn<CLB, Integer> idHlvCol;

    @FXML
    private TableColumn<CLB, Integer> pointCol;

    @FXML
    private TableColumn<CLB, Integer> gameplayedCol;

    private ObservableList<CLB> listBXH;

    private Connection c;

    private PreparedStatement pst;

    private ResultSet rs;



    @FXML
    private void back(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Main-Program.fxml",actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        listBXH = FXCollections.observableArrayList();
        tableViewBXH.setItems(listBXH);
        rankCol.setCellValueFactory(new PropertyValueFactory<CLB, Integer>("id"));
        nameClbCol.setCellValueFactory(new PropertyValueFactory<CLB, String>("name"));
        idHlvCol.setCellValueFactory(new PropertyValueFactory<CLB, Integer>("hlv"));
        pointCol.setCellValueFactory(new PropertyValueFactory<CLB, Integer>("points"));
        gameplayedCol.setCellValueFactory(new PropertyValueFactory<CLB, Integer>("gamePlayed"));

        refresh();
        SQLConnect.closeConnection(c);
    /*
    Phương thức initialize() này dùng để khởi tạo các thuộc tình và trạng thái của một controller ngay vừa khi file FXML đó được khởi tạo
     */
    }

    private void refresh(){
        c = SQLConnect.getConnection();
        listBXH.clear();
        try {
            pst = c.prepareStatement("SELECT * FROM clb ORDER BY points DESC ");
            rs = pst.executeQuery();
            while (rs.next()){
                CLB clb = new CLB();
                clb.setId(Integer.parseInt(rs.getString("IDCLB")));
                clb.setName(rs.getString("NAMECLB"));
                clb.setHlv(Integer.parseInt(rs.getString("IDHLV")));
                clb.setPoints(Integer.parseInt(rs.getString("POINTS")));
                clb.setGamePlayed(Integer.parseInt(rs.getString("GAMEPLAYED")));
                listBXH.add(clb);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
