package EmailProcessor;

import dbObjects.ApproveIndicator;
import emailRecognition.EmailRecognitionBuilder;
import emailRecognition.IEmailRecognition;
import gmailApiWrapper.EmailMessage;
import gmailApiWrapper.IEmailApiWrapper;
import main.java.DB.DBHandler;
import main.java.DB.Entities.TotalIndicator;
import main.java.DB.ReceiptsDAO;
import main.java.DB.error.FirebaseException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Processor {


    public void Run(IEmailApiWrapper emailApiWrapper){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Runnable mailProcess = () -> {
            try {
                List<EmailMessage> messages = emailApiWrapper.getMessages();
                ReceiptsDAO dbHandler = DBHandler.getInstance();
                ApproveIndicator approveIndicators = dbHandler.getApprovalIndicators();
                TotalIndicator totalIndicators = dbHandler.getTotalIndicator();
                IEmailRecognition emailRecognition = EmailRecognitionBuilder.Build(approveIndicators, totalIndicators);
                for(EmailMessage emailMessage : messages){
                    emailRecognition.Recognize(emailMessage);
                }

                EmailMessage lastEmailMessage = messages.
                        stream().
                        max(Comparator.comparing(EmailMessage::getDate)).
                        get();
                dbHandler.setLastSearchMailTime("", lastEmailMessage.getDate());
            } catch (Exception | FirebaseException e) {
                e.printStackTrace();
            }
            System.out.println("Task #2 is running");
        };
        service.scheduleAtFixedRate(mailProcess, 0, 1, TimeUnit.MINUTES);
        
    }
}
