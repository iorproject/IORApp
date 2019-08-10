package gmailApiWrapper;

import java.util.Date;
import java.util.List;

public abstract class EmailMessage {

    private String id;
    private String subject;
    private String from;
    private String to;
    private Date date;
    private String content;
    private List<EmailAttachment> attachments;



    public EmailMessage(String id) {

        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getFrom() {
        return from;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
