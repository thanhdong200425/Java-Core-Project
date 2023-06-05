package Controller.Result;

import DAO.SQLConnect;
import Model.Result;
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

public class Result_Controller implements Initializable {

    @FXML
    private TableView<Result> tableViewKQ;

    @FXML
    private TableColumn<Result, Integer> idMatchesCol;

    @FXML
    private TableColumn<Result, Integer> idClbWinCol;

    @FXML
    private TableColumn<Result, Integer> idClbLoseCol;

    @FXML
    private TableColumn<Result, Integer> score1Col;

    @FXML
    private TableColumn<Result, Integer> score2Col;

    private ObservableList<Result> listKQ;

    @FXML
    private TextField textField_ClbWin;

    @FXML
    private TextField textField_ClbLose;

    @FXML
    private TextField textField_Score1;

    @FXML
    private TextField textField_Score2;


    private Connection c;

    private PreparedStatement pst;

    private ResultSet rs;

    @FXML
    private void add(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();
        try {
            pst = c.prepareStatement("INSERT INTO matches_result(IDCLBWIN,IDCLBLOSE,SCORE1,SCORE2) VALUES (?,?,?,?)");
            pst.setInt(1, Integer.parseInt(textField_ClbWin.getText()));
            pst.setInt(2, Integer.parseInt(textField_ClbLose.getText()));
            pst.setInt(3, Integer.parseInt(textField_Score1.getText()));
            pst.setInt(4, Integer.parseInt(textField_Score2.getText()));
            pst.executeUpdate();

            refresh();

            textField_ClbWin.setText("");
            textField_ClbLose.setText("");
            textField_Score1.setText("");
            textField_Score2.setText("");

            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void refresh() {
        c = SQLConnect.getConnection();
        listKQ.clear();

        try {
            pst = c.prepareStatement("SELECT * FROM matches_result");
            rs = pst.executeQuery();
            while (rs.next()) {
                Result result = new Result();
                result.setIdMatches(Integer.parseInt(rs.getString("ID_MATCHES")));
                result.setIdClbWin(Integer.parseInt(rs.getString("IDCLBWIN")));
                result.setIdClbLose(Integer.parseInt(rs.getString("IDCLBLOSE")));
                result.setScore1(Integer.parseInt(rs.getString("SCORE1")));
                result.setScore2(Integer.parseInt(rs.getString("SCORE2")));
                listKQ.add(result);
            }

            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void delete(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("TRUNCATE TABLE matches_result");
            pst.execute();
            refresh();
            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void save(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("UPDATE matches_result SET IDCLBWIN = ?, IDCLBLOSE = ?, SCORE1 = ?, SCORE2 = ? WHERE ID_MATCHES = ?");
            pst.setInt(1, Integer.parseInt(textField_ClbWin.getText()));
            pst.setInt(2, Integer.parseInt(textField_ClbLose.getText()));
            pst.setInt(3, Integer.parseInt(textField_Score1.getText()));
            pst.setInt(4, Integer.parseInt(textField_Score2.getText()));
            pst.setInt(5, Integer.parseInt(textField_ClbWin.getText()));
            pst.executeUpdate();
            refresh();
            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void update(ActionEvent actionEvent) {
        Result result = tableViewKQ.getSelectionModel().getSelectedItem();
        textField_ClbWin.setText(String.valueOf(result.getIdClbWin()));
        textField_ClbLose.setText(String.valueOf(result.getIdClbLose()));
        textField_Score1.setText(String.valueOf(result.getScore1()));
        textField_Score2.setText(String.valueOf(result.getScore2()));
    }

    @FXML
    private void back(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Main_Program.class.getResource("Main-Program.fxml"));
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listKQ = FXCollections.observableArrayList();
        idMatchesCol.setCellValueFactory(new PropertyValueFactory<Result, Integer>("idMatches"));
        idClbWinCol.setCellValueFactory(new PropertyValueFactory<Result, Integer>("idClbWin"));
        idClbLoseCol.setCellValueFactory(new PropertyValueFactory<Result, Integer>("idClbLose"));
        score1Col.setCellValueFactory(new PropertyValueFactory<Result, Integer>("score1"));
        score2Col.setCellValueFactory(new PropertyValueFactory<Result, Integer>("score2"));
        tableViewKQ.setItems(listKQ);

        c = SQLConnect.getConnection();
        try {
            pst = c.prepareStatement("SELECT * FROM matches_result");
            rs = pst.executeQuery();
            while (rs.next()) {
                Result result = new Result();
                result.setIdMatches(Integer.parseInt(rs.getString("ID_MATCHES")));
                result.setIdClbWin(Integer.parseInt(rs.getString("IDCLBWIN")));
                result.setIdClbLose(Integer.parseInt(rs.getString("IDCLBLOSE")));
                result.setScore1(Integer.parseInt(rs.getString("SCORE1")));
                result.setScore2(Integer.parseInt(rs.getString("SCORE2")));
                listKQ.add(result);
            }

            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private void apply(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){
            String query = "UPDATE clb\n" +
                    "JOIN matches_result\n" +
                    "ON clb.idclb in(matches_result.idclbwin, matches_result.idclblose)\n" +
                    "SET clb.points = clb.points + \n" +
                    "CASE \n" +
                    "  WHEN clb.idclb = matches_result.idclbwin then 3\n" +
                    "  when clb.idclb = matches_result.idclblose then 0\n" +
                    "  else 1\n" +
                    "END\n" +
                    ",clb.gameplayed = clb.gameplayed + 1;";

            Connection c = SQLConnect.getConnection();
            PreparedStatement pst = c.prepareStatement(query);
            pst.executeUpdate();
            clear();
        }


//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("DO YOU WANT TO SAVE ALL INFORMATION");
//
//            ButtonType buttonTypeYES = new ButtonType("YES", ButtonBar.ButtonData.YES);
//            ButtonType buttonTypeNO = new ButtonType("NO", ButtonBar.ButtonData.NO);
//
//            alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.YES) {
//                pst.execute();
//                System.out.println("SAVED");
//                refresh();
//            }

    }

    private void clear() {
        c = SQLConnect.getConnection();

        try {
            pst = c.prepareStatement("TRUNCATE TABLE matches_result");
            pst.execute();
            refresh();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
