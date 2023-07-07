package Controller.Support;

import Controller.MainController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupportController implements Initializable {

    @FXML
    private ListView<ChatMessage> chatBox;

    @FXML
    private TextField textFieldMessage;

    private ObservableList<ChatMessage> chatMessages;
    private Client client;


    // Oke
    @FXML
    private void btnBack(ActionEvent actionEvent) {
        client.disconnect();
        MainController.changeScene("Main-Program.fxml", actionEvent);
    }

    // Oke
    @FXML
    private void btnSend(ActionEvent actionEvent) {
        sendMessage();
    }


    // Oke (NHƯNG NÊN XEM LẠI)
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initial list to save message in "Chat box"
        chatMessages = FXCollections.observableArrayList();
        chatBox.setItems(chatMessages);

        client = new Client();
        client.connectToServer("localhost", 1234);
        listenForMessagesFromServer();
        /* 1) Phương thức setCellFactory() là phương thức dùng cho đối tượng ListView, TreeView, TableView và nó dùng để
         thiết lập cách mà các nội dung cột được hiển thị trong 3 đối tượng trên.
           2) Đối tượng ListCell này sẽ được sử dụng để hiển thị các mục có kiểu dữ liệu là ChatMessage.
         */

        chatBox.setCellFactory(parameter -> new ListCell<ChatMessage>() {
            protected void updateItem(ChatMessage item, boolean empty) {
                // Phương thức updateItem() được sử dụng để tùy chỉnh cách mà mục được hiển thị trong Cell và cập nhật trạng thái hiển thị của Cell.
                // Nó phải được kêu trước khi override lại chính nó để có thể cập nhật đúng với từng cell trong ListView.
                super.updateItem(item, empty);
                // Đây là bước override lại updateItem()
                if(empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getContent());
                    if(item.getType() == MessageType.CLIENT) {
                        setStyle("-fx-alignment: center-right;");
                    } else {
                        setStyle("-fx-alignment: center-left;");
                    }
                }
            }
        });
    }

    // Oke
    private void sendMessage() {
        String message = textFieldMessage.getText();
        ChatMessage chatMessage = new ChatMessage("You: " + message, MessageType.CLIENT);
        // Hiển thị lên giao diện
        chatMessages.add(chatMessage);
        // Gửi tới server
        client.sendMessage(message);
        textFieldMessage.clear();
    }

    // Oke (NHƯNG NÊN XEM LẠI)
    private void listenForMessagesFromServer() {
        Thread thread = new Thread(
                () -> {
                    try {
                        while (true) {
                            String message = client.readFromServer.readUTF();
                            ChatMessage chatMessage = new ChatMessage("Admin: " + message, MessageType.SERVER);
                            Platform.runLater(
                                    () -> {
                                        chatMessages.add(chatMessage);
                                    }
                            );
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        thread.start();
        // Hiển thị tin nhắn từ server trong khung chat
//      /* Trong đoạn code này, chúng ta đang thêm một tin nhắn từ server, và thêm nó vào luồng giao diện người dùng
//       * bằng cách gọi hàm runLater của Platform object */
    }

}
