package gmailApiWrapper;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GmailApiWrapper {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance(); //JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "/tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList("https://mail.google.com/",
            "https://www.googleapis.com/auth/gmail.modify", "https://www.googleapis.com/auth/gmail.readonly"
            , GmailScopes.GMAIL_LABELS);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    private Gmail service;
    private final NetHttpTransport HTTP_TRANSPORT;
    private static final String ACCESS_TOKEN="ya29.GlxLByUeM22BYo-TmzwSSC6LSCWH_4naMi3J_TkOngflJQIzM3jz7tZBtUesOPRvoK_X-KsrfoXnIXh7gSYg1IeHgEC9kr09-f1Tfi_AkLywNSZfmqseuRLXJESZUg";
    private static final String REFRESH_TOKEN="1/l60RQ1WQINlbcsDvmONwDPiTjt88lWx29BvSwdaJJw8";
    private static final String CLIENT_SECRET="6C2okEplSBNFI8CQ9sr_m6gO";
    private static final String CLIENT_ID="745146810127-qr5uhgmubru7mv835ftqb28mh9onerrh.apps.googleusercontent.com";


    public GmailApiWrapper() throws GeneralSecurityException, IOException {

        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        if (ACCESS_TOKEN != null) {
            service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }
        performRequest(HTTP_TRANSPORT, CLIENT_ID, CLIENT_SECRET, ACCESS_TOKEN, REFRESH_TOKEN);
    }


    private void performRequest(final NetHttpTransport HTTP_TRANSPORT, String clientId, String clientSecret,String accessToken, String refreshToken) throws IOException {
                Credential c = new GoogleCredential.Builder()
                                .setJsonFactory(JSON_FACTORY)
                                .setTransport(HTTP_TRANSPORT)
                                .setClientSecrets(clientId,clientSecret)
                                .build();
                c.setAccessToken(accessToken);
                c.setRefreshToken(refreshToken);
                service = new Gmail.Builder(HTTP_TRANSPORT,JSON_FACTORY,c)
                                .setApplicationName(APPLICATION_NAME)
                                .build();
            }



    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailApiWrapper.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    public List<IEmailMessage> getMessages(String userId) throws IOException{

        List<IEmailMessage> results = new ArrayList<>();
        List<Message> messages = getGmailMessages(userId);
        for (Message m : messages) {
            List<MessagePartHeader> headers = m.getPayload().getHeaders();

            String subject = headers.stream().filter(h -> h.getName().equals("Subject")).collect(Collectors.toList()).get(0).getValue();
            String date = headers.stream().filter(h -> h.getName().equals("Date")).collect(Collectors.toList()).get(0).getValue();
            String from = headers.stream().filter(h -> h.getName().equals("From")).collect(Collectors.toList()).get(0).getValue();
            from = from.substring(from.indexOf('<') + 1, from.lastIndexOf('>'));

            String body = getBody(m);
            if (body != null) {
                body = StringUtils.newStringUtf8(Base64.decodeBase64(body));
                if (m.getPayload().getMimeType().equals("text/html")) {
                    body = Jsoup.parse(body).text();
                }
                String[] lines = body.replaceAll("\r", "").split("\n");
            }
            List<Attachment> attachments = getAttachments(m, service, userId);
            results.add(new IorEmailMessage(m.getId(), subject
                    , from, date, body, attachments));
        }

        return results;
    }

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


    private List<Message> getGmailMessages(String userId) throws IOException {


        ListMessagesResponse response = service.users().messages().list(userId)
                .setQ("newer_than:1d").execute();

        List<Message> messages = new ArrayList<>();
        List<Message> fullMessages = new ArrayList<>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId)
                        .setQ("newer_than:1d").execute();
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

            if(part.getBody().getData() != null) {
                res = part.getBody().getData();
            }

        }

        return res;
    }

    private List<Attachment> getAttachments(Message message, Gmail service, String userId) {

        List<Attachment> attachments = new ArrayList<>();
        String messageId = message.getId();

        if (message.getPayload() != null && message.getPayload().getParts() != null) {
            List<MessagePart> parts = message.getPayload().getParts();
            for (MessagePart part : parts) {
                getAttachmentsHelper(part, service, userId, messageId, attachments);
            }
        }

        return attachments;
    }


    private void getAttachmentsHelper(MessagePart part, Gmail service, String userId, String messageId, List<Attachment> attachments) {

        if (part.getParts() != null) {

            List<MessagePart> parts = part.getParts();
            for (MessagePart part1 : parts) {

                getAttachmentsHelper(part1, service, userId, messageId, attachments);
            }
        }
        else {

            if (part.getFilename() != null && part.getFilename().length() > 0) {
                String filename = part.getFilename();
                String attId = part.getBody().getAttachmentId();
                String fileName = part.getFilename();
                FileFormat type = FileFormat.valueOf(fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase());
                attachments.add(new Attachment(fileName, attId, type));
            }
        }
    }

    public byte[] getAttachmentBytes(Attachment attachment, String userId, IEmailMessage message) {

        MessagePartBody attachPart = null;
        try {
            attachPart = service.users().messages().attachments().
                    get(userId, message.getId(), attachment.getId()).execute();
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
}
