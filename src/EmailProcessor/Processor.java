package EmailProcessor;

import dbObjects.ApproveIndicator;
import emailRecognition.EmailRecognitionBuilder;
import emailRecognition.IEmailRecognition;
import gmailApiWrapper.*;
import main.java.DB.DBHandler;
import main.java.DB.Entities.*;
import main.java.DB.ReceiptsDAO;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Processor {
    private ApproveIndicator approveIndicators;
    private TotalIndicator totalIndicators;
    private OrderNumberApproveIndicator orderNumberApproveIndicator;
    private ReceiptsDAO dbHandler;
    private static final Logger LOGGER = Logger.getLogger("MyLog");

    public void Run(){
        init();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::runAllUsers, 0, 10, TimeUnit.MINUTES);
    }

    private void init() {
        dbHandler = DBHandler.getInstance();
        try {
            approveIndicators = dbHandler.getApprovalIndicators();
            totalIndicators = dbHandler.getTotalIndicator();
            orderNumberApproveIndicator = dbHandler.getOrderNumberApproval();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void runAllUsers(){
        try {
            dbHandler.getAllUsers().forEach(user -> {
                    runUser(user);
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void runUser(User user) {
        new Thread(() -> {
            try{
                checkUserMails(user);
            } catch (Throwable throwable) {
                LOGGER.log(Level.WARNING, throwable.getMessage());
                throwable.printStackTrace();
            }
        }).run();
    }

    private void checkUserMails(User user) throws Throwable {
        Date now = new Date();
        IEmailRecognition emailRecognition = EmailRecognitionBuilder.Build(
                approveIndicators,
                totalIndicators,
                orderNumberApproveIndicator);
        List<EmailMessage> messages = setEmailMessages(user);
        for(EmailMessage emailMessage : messages){
            LOGGER.log(Level.INFO, "***************************" +
                    emailMessage.getSubject() +
                    "***************************");
            emailRecognition.Recognize(emailMessage);
        }
        
        setLastServed(messages, user, now);
    }

    private List<EmailMessage> setEmailMessages(User user) throws Throwable {
        final long defaultStartTime = 1546838041821l; //07.01.19
        IEmailApiWrapper emailApiWrapper = setEmailWrapper(user);
        Date from = dbHandler.getLastSearchMailTime(user.getEmail());
        from = from == null ? new Date(defaultStartTime) : from;
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
        Date date;
        try{
            EmailMessage lastEmailMessage = messages.
                    stream().
                    max(Comparator.comparing(EmailMessage::getDate)).get();
            date = lastEmailMessage.getDate();
        } catch (Exception ignored){
            date = now;
        }

        dbHandler.setLastSearchMailTime(user.getEmail(), date);
    }
}