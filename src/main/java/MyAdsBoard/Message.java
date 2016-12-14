package MyAdsBoard;

import javax.persistence.*;
import java.time.LocalDateTime;

enum MessageType {IN, OUT}

@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private String fromEmail;
    private String toEmail;
    private String messageText;
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
    private MessageType messageType;
    private String fileName;
    @Lob
    private byte[] fileBody;
    private LocalDateTime creationDate;

    public Message() {}
    public Message(String fromEmail, String toEmail, String messageText, Ad ad, MessageType messageType) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.messageText = messageText;
        this.ad = ad;
        this.messageType = messageType;
        creationDate = LocalDateTime.now();
    }
    public Message(String fromEmail, String toEmail, String messageText, Ad ad, MessageType messageType, String fileName, byte[] fileBody) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.messageText = messageText;
        this.ad = ad;
        this.messageType = messageType;
        this.fileName = fileName;
        this.fileBody = fileBody;
        creationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public String getFromEmail() {
        return fromEmail;
    }
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }
    public String getToEmail() {
        return toEmail;
    }
    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public Ad getAd() {
        return ad;
    }
    public void setAd(Ad ad) {
        this.ad = ad;
    }
    public MessageType getMessageType() {
        return messageType;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public byte[] getFileBody() {
        return fileBody;
    }
    public void setFileBody(byte[] fileBody) {
        this.fileBody = fileBody;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
