package Controller.Support;

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
