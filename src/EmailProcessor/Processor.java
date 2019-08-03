package EmailProcessor;

import dbObjects.ApproveIndicator;
import emailRecognition.EmailRecognitionBuilder;
import emailRecognition.IEmailRecognition;
import gmailApiWrapper.EmailApiWrapperFactory;
import gmailApiWrapper.EmailMessage;
import gmailApiWrapper.IEmailApiWrapper;
import gmailApiWrapper.eEmailApi;
import main.java.DB.DBHandler;
import main.java.DB.Entities.TotalIndicator;
import main.java.DB.ReceiptsDAO;
import main.java.DB.error.FirebaseException;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Processor {
    public void Run() throws Throwable {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        ReceiptsDAO dbHandler = DBHandler.getInstance();
        ApproveIndicator approveIndicators = dbHandler.getApprovalIndicators();
        TotalIndicator totalIndicators = dbHandler.getTotalIndicator();
        IEmailRecognition emailRecognition = EmailRecognitionBuilder.Build(approveIndicators, totalIndicators);
        Runnable mailProcess = () -> {
            try {
                dbHandler.getAllUsers().forEach(user -> {
                    new Thread(() -> {
                        try{
                            IEmailApiWrapper emailApiWrapper = EmailApiWrapperFactory.createEmailApiWrapper(eEmailApi.GMAIL, user.getEmail(), user.getAccessToken(), null);
                            List<EmailMessage> messages = null;
                            messages = emailApiWrapper.getMessages(
                                    dbHandler.getLastSearchMailTime(user.getEmail()));
                            EmailMessage lastEmailMessage = messages.
                                    stream().
                                    max(Comparator.comparing(EmailMessage::getDate)).
                                    get();
                            for(EmailMessage emailMessage : messages){
                                emailRecognition.Recognize(emailMessage);
                            }
                            dbHandler.setLastSearchMailTime(user.getEmail(), lastEmailMessage.getDate());
                        } catch (Throwable ignored){

                        }

                    }).run();
                });
            } catch (Throwable e) {
                e.printStackTrace();
            }
            System.out.println("Task #2 is running");
        };
        service.scheduleAtFixedRate(mailProcess, 0, 5, TimeUnit.MINUTES);
    }
}
