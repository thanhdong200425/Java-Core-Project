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
import java.util.Properties;
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
                        String email = row.getString("email");
                        String newPassword = caesarCipher(originPassword, 2);
                        updatePassword(newPassword);
                        sendEmail(email, newPassword);
                    }
                    SQLConnect.closeConnection(c);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            messegeLabel.setText("Username can't be blank");
        }
    }

    // Oke
    private void sendEmail(String recipient, String newPassword) {
        // Information of protocol, port, and sender
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "thanhdong200425@gmail.com";
        String password = "uxkaiaxazdklerrm";

        // Sử dụng class Properties để lưu cấu hình dựa trên put()
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        /*"mail.smtp.auth": Đây là một thuộc tính để xác định xem xác thực (authentication) sẽ được sử dụng khi gửi email thông qua máy chủ SMTP.
        Nếu giá trị của thuộc tính này là "true", đồng nghĩa với việc sử dụng xác thực. Điều này yêu cầu người gửi cung cấp thông tin xác thực (username và password)
         để được xác minh trước khi gửi email. Nếu giá trị là "false", không yêu cầu xác thực.*/
        props.put("mail.smtp.starttls.enable", "true");
        /*Đây là một thuộc tính để xác định xem có kích hoạt TLS (Transport Layer Security) trong giao tiếp với máy chủ SMTP hay không.
        TLS là một giao thức bảo mật được sử dụng để mã hóa dữ liệu gửi đi và nhận về trong quá trình truyền tải.
         Nếu giá trị của thuộc tính này là "true", kích hoạt TLS. Nếu giá trị là "false", không sử dụng TLS.*/


        // Sử dụng class "Session" để tạo ra một phiên làm việc giữa ứng dụng ở localhost và server
        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
            // Authenticator là một abstract class, nó dùng để xác thực thông tin email trước khi đến các hành vi tiếp theo, nhằm đảo bảo email của người gửi hoặc
            // người nhận là có thật
        });

        try {
            String subject = "RESET PASSWORD";
            String body = "Hi, I'm admin. THANKS FOR USING MY APPLICATION \n" +
                    "Your new password: " + newPassword + "\n" +
                    "PLEASE REMEMBER IT";

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
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
