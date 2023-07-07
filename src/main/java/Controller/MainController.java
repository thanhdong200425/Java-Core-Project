package Controller;


import com.do_an_main.Main_Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Optional;

public class MainController {

    @FXML
    private void btnHighlight(ActionEvent actionEvent) {
        openWebPage("https://youtube.com/playlist?list=PLFtY1m-5VpRCE_ia4HRJqOTGbrlI-TMAl");
    }

    @FXML
    private void btnCustom(ActionEvent event) throws IOException {
        changeScene("Custom.fxml", event);
    }

    @FXML
    private void btnRank(ActionEvent event) throws IOException {
        changeScene("Rank.fxml", event);
    }

    @FXML
    private void btnStatiscial(ActionEvent event) throws IOException {
        changeScene("Statistical.fxml", event);
    }

    @FXML
    private void btnResult(ActionEvent event) throws IOException {
        changeScene("Result.fxml", event);
    }

    @FXML
    private void btnLogOut(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit!");
        alert.setHeaderText("Do you want to log out?");


        Optional<ButtonType> result = alert.showAndWait();
        /*
        Đoạn code này sẽ làm xuất hiện một hộp thoại thông báo lên màn hình và chờ người dùng nhấn vào một nút bất kì thực hiện sự kiện, kết quả của
        "alert.showAndWait()" sẽ trả về một đối tượng Optional<ButtonType>, đối tượng này được gán vào biến "result"

        Đối tượng Optional là một đối tượng mới được giới thiệu trong Java 8, nó được sinh ra để giải quyết về vấn đề giá trị null,
        khi mà nhấn vào nút đóng trên hộp thoại, thì giá trị trả về sẽ là null, còn khi nhấn vào một nút bất kì xuất hiện trên hộp thoại, thì nút nhấn
        đó sẽ trả về giá trị tương ứng và sẽ được lưu trong biến "result"
         */
        if (result.get() == ButtonType.OK) {
            // Phương thức get() ở đây là để truy xuất giá trị của biến result, và đem nó so sánh với giá trị của button "OK", nếu bằng thì sẽ trả về true,
            // còn kh thì false
            changeScene("Login.fxml", actionEvent);
        } else {
        }
    }

    @FXML
    private void btnSupport(ActionEvent actionEvent) {
        changeScene("Support.fxml", actionEvent);
    }

    public static void changeScene(String string, ActionEvent actionEvent) {
        try {

            Parent parent = FXMLLoader.load(Main_Program.class.getResource(string));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void openWebPage(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
            }
        }
    }
}