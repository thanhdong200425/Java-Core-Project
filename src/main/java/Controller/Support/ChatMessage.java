package Controller.Support;

// Class này ra đời để kiểm soát tin nhắn là của phía nào.
public class ChatMessage {
    private String content;
    private MessageType messageType;

    public ChatMessage(String content, MessageType type) {
        this.content = content;
        this.messageType = type;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return messageType;
    }
}
