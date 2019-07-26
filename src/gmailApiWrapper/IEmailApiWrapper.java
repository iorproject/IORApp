package gmailApiWrapper;

import java.util.List;

public interface IEmailApiWrapper {

    List<EmailMessage> getMessages() throws Exception;
}
