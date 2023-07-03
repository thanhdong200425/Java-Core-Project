package Controller.Login;

import Controller.MainController;
import DAO.SQLConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.mail.*;
import javax.mail.internet.*;


public class ForgetPassword {
    @FXML
    private TextField usernameTextField;

    private Connection c;

    @FXML
    private Label messegeLabel;

    public String caesarCipher(String password, int offset) {
        StringBuilder newPassword = new StringBuilder();

        // Handle
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isLetter(ch)) {
                char shiftedChar = (char) (ch + offset);
                if ((Character.isLowerCase(ch) && shiftedChar > 'z') || (Character.isUpperCase(ch) && shiftedChar > 'Z')) {
                    shiftedChar = (char) (ch - (26 - offset));
                }
                newPassword.append(shiftedChar);
            } else if (Character.isDigit(ch)) {
                char shiftedDigit = (char) (ch + offset);
                if (shiftedDigit > '9') {
                    shiftedDigit = (char) (ch - (10 - offset));
                }
                newPassword.append(shiftedDigit);
            } else {
                newPassword.append(ch);
            }
        }
        return newPassword.toString();
    }

    @FXML
    public void apply(ActionEvent actionEvent) throws SQLException {
        if (usernameTextField.getText().isBlank() == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("RESET PASSWORD");
            alert.setContentText("Do you want to reset password");

            ButtonType buttonTypeYes = new ButtonType("YES", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("NO", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().getButtonData() == ButtonBar.ButtonData.YES) {
                try {
                    c = SQLConnect.getConnection();
                    PreparedStatement pst = c.prepareStatement("SELECT * FROM user WHERE username = ? ");
                    pst.setString(1, usernameTextField.getText());
                    ResultSet row = pst.executeQuery();
                    while (row.next()) {
                        String originPassword = row.getString("password");
                        String newPassword = caesarCipher(originPassword, 2);
                        System.out.println(newPassword);
                        updatePassword(newPassword);
                    }


                    // Làm đến phần mã hóa mật khẩu nhưng đang kẹt ở chỗ tìm kiếm database

                    SQLConnect.closeConnection(c);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            messegeLabel.setText("Username can't blank");
        }

    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        MainController.changeScene("Login.fxml", actionEvent);
    }

    public void updatePassword(String password) {
        try {
            c = SQLConnect.getConnection();
            PreparedStatement pst = c.prepareStatement("UPDATE user SET password = ? WHERE username = ?");
            pst.setString(1, password);
            pst.setString(2, usernameTextField.getText());
            pst.executeUpdate();
            SQLConnect.closeConnection(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
