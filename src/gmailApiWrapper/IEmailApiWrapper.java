package gmailApiWrapper;

import java.util.Date;
import java.util.List;

public interface IEmailApiWrapper {

    List<EmailMessage> getMessages(Date startingTime) throws Exception;
}
