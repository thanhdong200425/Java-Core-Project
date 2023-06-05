package Controller.Custom;

import Controller.MainController;
import com.do_an_main.Main_Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Custom_Controller {
    @FXML
    private void back(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Main-Program.fxml", actionEvent);
    }

    @FXML
    private void clb(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Custom_CLB.fxml", actionEvent);
    }

    @FXML
    private void player(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Custom_PLAYER.fxml", actionEvent);
    }

    @FXML
    private void hlv(ActionEvent actionEvent) throws IOException {
        MainController.changeScene("Custom_HLV.fxml", actionEvent);
    }


}
