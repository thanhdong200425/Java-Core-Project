package Controller.Login;

import Controller.MainController;
import DAO.SQLConnect;
import com.do_an_main.Main_Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController {

    @FXML
    private Label messegeLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    private Connection c;
    private PreparedStatement pst;

    private ResultSet rs;


    @FXML
    private void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void login(ActionEvent actionEvent) throws IOException {
        if (usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
            try {
                c = SQLConnect.getConnection();
                pst = c.prepareStatement("SELECT count(1) FROM user where username = ? and password = ?");
                pst.setString(1, usernameTextField.getText());
                pst.setString(2, passwordPasswordField.getText());
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        MainController.changeScene("Main-Program.fxml",actionEvent);
                    } else {
                        messegeLabel.setText("Invalid username or password!!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            messegeLabel.setText("You must enter data!");
        }
    }

    @FXML
    private void btnCreateAnAccount(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Create-Account.fxml",actionEvent);
    }

}
