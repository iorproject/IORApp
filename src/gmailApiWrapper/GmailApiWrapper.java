package gmailApiWrapper;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import org.jsoup.Jsoup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class GmailApiWrapper implements IEmailApiWrapper {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance(); //JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "/tokens";
    private static final int TOKEN_EXPIRE = 401;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList("https://mail.google.com/",
            "https://www.googleapis.com/auth/gmail.modify", "https://www.googleapis.com/auth/gmail.readonly"
            , GmailScopes.GMAIL_LABELS);
    //private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    private Gmail service;
    private final NetHttpTransport HTTP_TRANSPORT;
    //private static final String ACCESS_TOKEN = "ya29.GlxLByUeM22BYo-TmzwSSC6LSCWH_4naMi3J_TkOngflJQIzM3jz7tZBtUesOPRvoK_X-KsrfoXnIXh7gSYg1IeHgEC9kr09-f1Tfi_AkLywNSZfmqseuRLXJESZUg";
    //private static final String REFRESH_TOKEN = "1/l60RQ1WQINlbcsDvmONwDPiTjt88lWx29BvSwdaJJw8";
    private static final String CLIENT_SECRET = "VsT2YNjAbH6WgVrhwiE7JlKw";
    private static final String CLIENT_ID = "82521020889-gepgorji8252381j18ii2kd5cb20igsg.apps.googleusercontent.com";

    private final String userId;
    private String accessToken;
    private final String refreshToken;

    private Credential credential;


    public GmailApiWrapper(String userId, String accessToken, String refreshToken) throws GeneralSecurityException, IOException {

        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        if (accessToken != null) {
///*
//            service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                    .setApplicationName(APPLICATION_NAME)
//                    .build();
        }

        performRequest(HTTP_TRANSPORT, CLIENT_ID, CLIENT_SECRET, accessToken, refreshToken);
    }


//    public GmailApiWrapper() throws GeneralSecurityException, IOException {
//
//        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        if (ACCESS_TOKEN != null) {
//            service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                    .setApplicationName(APPLICATION_NAME)
//                    .build();
//        }
//        performRequest(HTTP_TRANSPORT, CLIENT_ID, CLIENT_SECRET, ACCESS_TOKEN, REFRESH_TOKEN);
//    }


    private void performRequest(final NetHttpTransport HTTP_TRANSPORT, String clientId, String clientSecret, String accessToken, String refreshToken) throws IOException {
        Credential c = new GoogleCredential.Builder()
                .setJsonFactory(JSON_FACTORY)
                .setTransport(HTTP_TRANSPORT)
                .setClientSecrets(clientId, clientSecret)
                .build();
        c.setAccessToken(accessToken);
        c.setRefreshToken(refreshToken);
        service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, c)
                .setApplicationName(APPLICATION_NAME)
                .build();

        this.credential = c;


    }


//    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//        // Load client secrets.
//        InputStream in = GmailApiWrapper.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }


//    public List<IEmailMessage> getMessages(String userId) throws IOException{
//
//        List<IEmailMessage> results = new ArrayList<>();
//        List<Message> messages = getGmailMessages(userId);
//        for (Message m : messages) {
//            List<MessagePartHeader> headers = m.getPayload().getHeaders();
//
//            String subject = headers.stream().filter(h -> h.getName().equals("Subject")).collect(Collectors.toList()).get(0).getValue();
//            String date = headers.stream().filter(h -> h.getName().equals("Date")).collect(Collectors.toList()).get(0).getValue();
//            String from = headers.stream().filter(h -> h.getName().equals("From")).collect(Collectors.toList()).get(0).getValue();
//            from = from.substring(from.indexOf('<') + 1, from.lastIndexOf('>'));
//
//            String body = getBody(m);
//            if (body != null) {
//                body = StringUtils.newStringUtf8(Base64.decodeBase64(body));
//                if (m.getPayload().getMimeType().equals("text/html")) {
//                    body = Jsoup.parse(body).text();
//                }
//                String[] lines = body.replaceAll("\r", "").split("\n");
//            }
//            List<Attachment> attachments = getAttachments(m, service, userId);
//            results.add(new IorEmailMessage(m.getId(), subject
//                    , from, date, body, attachments));
//        }
//
//        return results;
//    }

//    public static void main(String... args) throws IOException, GeneralSecurityException {
//        // Build a new authorized API client service.
//        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//
//        String user = "me";
//        StringBuilder contentBuilder = new StringBuilder();
//        String token = "";
//
//
//        List<IEmailMessage> messages = getMessages(service, user);
//        int ttt = 7;
//
//        }


    private List<Message> getGmailMessages(Date startingDate) throws IOException {


        String start = new SimpleDateFormat("yyyy/MM/dd").format(startingDate);
        String query = "after:" + start;

        ListMessagesResponse response = service.users().messages().list(userId)
                .setQ(query).execute();

        List<Message> messages = new ArrayList<>();
        List<Message> fullMessages = new ArrayList<>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId)
                        .setQ(query).setPageToken(pageToken).execute();

            } else {
                break;
            }
        }

        for (Message message : messages) {

            Message m = service.users().messages().get(userId, message.getId()).setFormat("full").execute();
            List<String> labels = m.getLabelIds();
            if (!labels.contains("CATEGORY_SOCIAL") && !labels.contains("CATEGORY_PROMOTIONS")
                    && !labels.contains("SENT")) {
                fullMessages.add(m);
            }

        }
        return fullMessages;
    }

    private String getBody(Message message) {

        String res = null;

        if (message.getPayload().getBody().getData() != null) {
            return message.getPayload().getBody().getData();
        }

        List<MessagePart> parts = message.getPayload().getParts();
        if (parts != null) {
            for (MessagePart part : parts) {
                res = getBodyHelper(part);
                if (res != null) {
                    return res;
                }

            }
        }
        return res;
    }

    private String getBodyHelper(MessagePart part) {

        String res = null;

        if (part.getParts() != null) {

            List<MessagePart> parts = part.getParts();
            for (MessagePart part1 : parts) {

                res = getBodyHelper(part1);
                if (res != null) {
                    return res;
                }
            }
        } else {

            if (part.getBody().getData() != null) {
                res = part.getBody().getData();
            }

        }

        return res;
    }

    private List<EmailAttachment> getAttachments(Message message) {

        List<EmailAttachment> attachments = new ArrayList<>();
        String messageId = message.getId();

        if (message.getPayload() != null && message.getPayload().getParts() != null) {
            List<MessagePart> parts = message.getPayload().getParts();
            for (MessagePart part : parts) {
                getAttachmentsHelper(part, messageId, attachments);
            }
        }

        return attachments;
    }


    private void getAttachmentsHelper(MessagePart part, String messageId, List<EmailAttachment> attachments) {

        if (part.getParts() != null) {

            List<MessagePart> parts = part.getParts();
            for (MessagePart part1 : parts) {

                getAttachmentsHelper(part1, messageId, attachments);
            }
        } else {

            if (part.getFilename() != null && part.getFilename().length() > 0) {
                String filename = part.getFilename();
                String attId = part.getBody().getAttachmentId();
                String fileName = part.getFilename();
                FileFormat type = FileFormat.valueOf(fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase());
                GmailAttachment attachment = new GmailAttachment(fileName, attId, type);
                attachment.setEmailApiWrapper(this);
                attachment.setGmailMessage(messageId);
                attachments.add(attachment);
            }
        }
    }

    public byte[] getAttachmentBytes(String attachmentId, String messageId) {

        MessagePartBody attachPart = null;
        try {
            attachPart = service.users().messages().attachments().
                    get(userId, messageId, attachmentId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] fileByteArray = Base64.decodeBase64(attachPart.getData());

        return fileByteArray;
    }


    private byte[] getAttachmentData(String attId, Gmail service, String userId, String messageId) {

        MessagePartBody attachPart = null;
        try {
            attachPart = service.users().messages().attachments().
                    get(userId, messageId, attId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] fileByteArray = Base64.decodeBase64(attachPart.getData());

        return fileByteArray;
    }


    @Override
    public List<EmailMessage> getMessages(Date startingTime) throws Exception {

        List<EmailMessage> results = new ArrayList<>();
        List<Message> messages = null;
        try {
            messages = getGmailMessages(startingTime);
        } catch (GoogleJsonResponseException e1) {

            if (e1.getStatusCode() == TOKEN_EXPIRE) {

                //refreshAccessToken();
                //messages = getGmailMessages();
                credential.refreshToken();
                service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                        .setApplicationName(APPLICATION_NAME)
                        .build();

                messages = getGmailMessages(startingTime);

            }
        }

        for (Message m : messages) {
            try {
                EmailMessage gmailMessage = new GmailMessage(m.getId());
                List<MessagePartHeader> headers = m.getPayload().getHeaders();

                String dateStr = headers.stream().filter(h -> h.getName().equals("Date")).collect(Collectors.toList()).get(0).getValue();
                dateStr = dateStr.substring(0, dateStr.lastIndexOf(' '));
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss"
                        , Locale.US);
                Date date = formatter.parse(dateStr);
                if (date.before(startingTime))
                    break;
//            if (date.before(startingTime))
//                break;

                String subject = headers.stream().filter(h -> h.getName().equals("Subject")).collect(Collectors.toList()).get(0).getValue();
                String from = headers.stream().filter(h -> h.getName().equals("From")).collect(Collectors.toList()).get(0).getValue();
                try {
                    from = from.substring(from.indexOf('<') + 1, from.lastIndexOf('>'));
                } catch (Exception ignored) {
                }
                String to = headers.stream().filter(h -> h.getName().equals("To")).collect(Collectors.toList()).get(0).getValue();
                try {
                    to = to.substring(to.indexOf('<') + 1, to.lastIndexOf('>'));
                } catch (Exception ignored) {
                }
                gmailMessage.setTo(to);
                gmailMessage.setSubject(subject);
                gmailMessage.setDate(date);
                gmailMessage.setFrom(from);


                String body = getBody(m);
                if (body != null) {
                    body = StringUtils.newStringUtf8(Base64.decodeBase64(body));
                    if (m.getPayload().getMimeType().equals("text/html")) {
                        body = Jsoup.parse(body).text();
                    }
                }

                gmailMessage.setContent(body);
                List<EmailAttachment> attachments = getAttachments(m);
                gmailMessage.setAttachments(attachments);
                results.add(gmailMessage);
            } catch (Exception ignored) { }
        }

        return results;
    }

    private void refreshAccessToken() {
        try {
            TokenResponse response =
                    new GoogleRefreshTokenRequest(new NetHttpTransport(), new JacksonFactory(),
                            refreshToken, CLIENT_ID, CLIENT_SECRET).execute();
            System.out.println("Access token: " + response.getAccessToken());
            this.accessToken = response.getAccessToken();
        } catch (TokenResponseException e) {
            if (e.getDetails() != null) {
                System.err.println("Error: " + e.getDetails().getError());
                if (e.getDetails().getErrorDescription() != null) {
                    System.err.println(e.getDetails().getErrorDescription());
                }
                if (e.getDetails().getErrorUri() != null) {
                    System.err.println(e.getDetails().getErrorUri());
                }
            } else {
                System.err.println(e.getMessage());
            }
        } catch (IOException e2) {

        }
    }

}
