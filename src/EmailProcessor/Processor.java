package EmailProcessor;

import dbObjects.ApproveIndicator;
import emailRecognition.EmailRecognitionBuilder;
import emailRecognition.IEmailRecognition;
import gmailApiWrapper.EmailApiWrapperFactory;
import gmailApiWrapper.EmailMessage;
import gmailApiWrapper.IEmailApiWrapper;
import gmailApiWrapper.eEmailApi;
import main.java.DB.DBHandler;
import main.java.DB.Entities.Receipt;
import main.java.DB.Entities.TotalIndicator;
import main.java.DB.Entities.User;
import main.java.DB.Entities.eContentType;
import main.java.DB.ReceiptsDAO;
import main.java.DB.error.FirebaseException;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Processor {
    private ApproveIndicator approveIndicators;
    private TotalIndicator totalIndicators;
    private ReceiptsDAO dbHandler;

    public void Run(){
        init();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::runAllUsers, 0, 1, TimeUnit.MINUTES);
    }

    private void init() {
        dbHandler = DBHandler.getInstance();
        try {
            approveIndicators = dbHandler.getApprovalIndicators();
            totalIndicators = dbHandler.getTotalIndicator();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void runAllUsers(){
        System.out.println("*******runAllUsers***********");
        try {
            dbHandler.getAllUsers().forEach(this::runUser);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void runUser(User user) {
        new Thread(() -> {
            try{
                checkUserMails(user);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }).run();
    }

    private void checkUserMails(User user) throws Throwable {
        Date now = new Date();
        IEmailRecognition emailRecognition = EmailRecognitionBuilder.Build(approveIndicators, totalIndicators);
        List<EmailMessage> messages = setEmailMessages(user);
        for(EmailMessage emailMessage : messages){
            emailRecognition.Recognize(emailMessage);
        }
        setLastServed(messages, user, now);
    }

    private List<EmailMessage> setEmailMessages(User user) throws Throwable {
        IEmailApiWrapper emailApiWrapper = setEmailWrapper(user);
        Date from = dbHandler.getLastSearchMailTime(user.getEmail());
        from = from == null ? new Date(0) : from;
        return emailApiWrapper.getMessages(from);
    }

    private IEmailApiWrapper setEmailWrapper(User user) throws Throwable {
        User userCredential = dbHandler.getCredentialUser(user.getEmail());
        return EmailApiWrapperFactory.createEmailApiWrapper(
                eEmailApi.GMAIL,
                user.getEmail(),
                userCredential.getAccessToken(),
                userCredential.getRefreshToken());
    }

    private void setLastServed(List<EmailMessage> messages, User user, Date now) throws Throwable {
        EmailMessage lastEmailMessage = messages.
                stream().
                max(Comparator.comparing(EmailMessage::getDate)).get();
        Date date = lastEmailMessage != null ? lastEmailMessage.getDate() : now;
        dbHandler.setLastSearchMailTime(user.getEmail(), date);
    }
}