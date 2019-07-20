package gmailApiWrapper;

import java.util.List;

public class IorEmailMessage implements IEmailMessage {

    private String id;
    private String subject;
    private String from;
    private String date;
    private String content;
    private List<Attachment> attachments;


    public IorEmailMessage(String id, String subject, String from, String date, String content, List<Attachment> attachments) {

        this.id = id;
        this.subject = subject;
        this.from = from;
        this.date = date;
        this.content = content;
        this.attachments = attachments;


    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public List<Attachment> getAttachments() {
        return attachments;
    }
}
