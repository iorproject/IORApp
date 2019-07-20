package gmailApiWrapper;

import java.util.List;

public interface IEmailMessage {

    String getId();
    String getSubject();
    String getFrom();
    String getContent();
    String getDate();
    List<Attachment> getAttachments();
}
