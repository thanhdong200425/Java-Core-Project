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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccountController {

    @FXML
    private Label messegeLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;
    private Connection c;
    private PreparedStatement pst;


    @FXML
    private void btnCreate(ActionEvent actionEvent) {
        c = SQLConnect.getConnection();
        if (usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
            try {
                PreparedStatement check = c.prepareStatement("SELECT count(1) from user where username = ?");
                check.setString(1,usernameTextField.getText());
                ResultSet rs = check.executeQuery();
                if(rs.next() && rs.getInt(1)>0){
                    messegeLabel.setText("Username already exits!");
                } else {
                    pst = c.prepareStatement("Insert into user(username,password) values (?,?)");
                    pst.setString(1, usernameTextField.getText());
                    pst.setString(2, passwordPasswordField.getText());
                    pst.executeUpdate();
                    messegeLabel.setText("You were created account!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                messegeLabel.setText("Error " + e.getMessage());
            }
        } else {
            messegeLabel.setText("You must enter full data!");
        }

    }

    @FXML
    private void btnCancel(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Login.fxml",actionEvent);
    }
}
