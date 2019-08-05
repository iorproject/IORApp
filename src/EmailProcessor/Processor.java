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
import main.java.DB.Entities.User;
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
    public void Run() throws Throwable {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        ReceiptsDAO dbHandler = DBHandler.getInstance();
        ApproveIndicator approveIndicators = dbHandler.getApprovalIndicators();
        TotalIndicator totalIndicators = dbHandler.getTotalIndicator();
//        Runnable mailProcess = () -> {
//            try {
////                dbHandler.getAllUsers().forEach(user -> {
//                    new Thread(() -> {
//                        try{
//                            IEmailRecognition emailRecognition = EmailRecognitionBuilder.Build(approveIndicators, totalIndicators);
//                            IEmailApiWrapper emailApiWrapper = EmailApiWrapperFactory.createEmailApiWrapper(
//                                    eEmailApi.GMAIL,
//                                    "shikoba21@gmail.com",
//                                    "ya29.GltUB3m-1ma9GCrjY8tuFzngd-PAEGgGXZq75fNzAo1uhAR-TDNofdbjKSCFXERZoBJhTZPk0-oSvLB3_DwPC7iy4iM3_3ah3IG-UWkjXRga7gicDi5W-t0jqELa",
//                                    "1/DvWTHOyK03h8VvJi6IXtZ12JbyvG8ir0VHtj4DLX528");
//                            List<EmailMessage> messages = null;
////                            messages = emailApiWrapper.getMessages(
////                                    dbHandler.getLastSearchMailTime("shikoba21@gmail.com"));
//                            messages = emailApiWrapper.getMessages(new Date(0));
//                            EmailMessage lastEmailMessage = messages.
//                                    stream().
//                                    max(Comparator.comparing(EmailMessage::getDate)).
//                                    get();
//                            for(EmailMessage emailMessage : messages){
//                                emailRecognition.Recognize(emailMessage);
//                            }
//                            dbHandler.setLastSearchMailTime("shikoba21@gmail.com", lastEmailMessage.getDate());
//                        } catch (Exception | FirebaseException ignored){
//
//                        }
//
//                    }).run();
////                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("Task #2 is running");
//        };
//        service.scheduleAtFixedRate(mailProcess, 0, 5, TimeUnit.MINUTES);

        dbHandler.getAllUsers().forEach(user -> {
            new Thread(() -> {
                try{
                    IEmailRecognition emailRecognition = EmailRecognitionBuilder.Build(approveIndicators, totalIndicators);
                    User userCredential = dbHandler.getCredentialUser(user.getEmail());
                    IEmailApiWrapper emailApiWrapper = EmailApiWrapperFactory.createEmailApiWrapper(
                            eEmailApi.GMAIL,
                            user.getEmail(),
                            userCredential.getAccessToken(),
                            userCredential.getRefreshToken());
                    List<EmailMessage> messages = null;
                    Date from = dbHandler.getLastSearchMailTime(user.getEmail());
                    from = from == null ? new Date(0) : from;
                    messages = emailApiWrapper.getMessages(from);
                    EmailMessage lastEmailMessage = null;
                    try {
                        lastEmailMessage = messages.
                                stream().
                                max(Comparator.comparing(EmailMessage::getDate)).get();
                    }catch (Exception ignored){}

                    for(EmailMessage emailMessage : messages){
                        emailRecognition.Recognize(emailMessage);
                    }
                    Date date = lastEmailMessage != null ? lastEmailMessage.getDate() : null;
                    dbHandler.setLastSearchMailTime(user.getEmail(), date);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }).run();
        });
    }
}