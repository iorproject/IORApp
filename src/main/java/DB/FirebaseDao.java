package main.java.DB;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import dbObjects.ApproveIndicator;
import main.java.DB.Entities.*;
import main.java.DB.error.FirebaseException;
import main.java.DB.error.JacksonUtilityException;
import main.java.DB.model.FirebaseResponse;
import main.java.DB.service.Firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FirebaseDao implements ReceiptsDAO{
    private static FirebaseDao firebaseDao = null;
    private final String firebase_baseUrl = "https://iorproject.firebaseio.com/";
    private Firebase firebase;
    private FirebaseResponse response;
    private Storage firebaseStorage;
    private static final Object lockObject = new Object();
    private FirebaseDao() throws FirebaseException {
        firebase = new Firebase( firebase_baseUrl);
        try {
            Credentials credentials = GoogleCredentials
                    .fromStream(new FileInputStream("./IORProject-645649d1f7f3.json"));
            firebaseStorage = StorageOptions.newBuilder().setCredentials(credentials)
                    .setProjectId("iorproject").build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static FirebaseDao getInstance() throws FirebaseException {
        if(firebaseDao == null){
            synchronized (lockObject){
                if(firebaseDao == null){
                    firebaseDao = new FirebaseDao();
                }
            }
        }
        return firebaseDao;
    }

    private URL saveReceiptInStorage(String receiptName, byte[] receiptBody, String contextType){
        BlobId blobId = BlobId.of("iorproject.appspot.com", receiptName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contextType).build();
        Blob blob = firebaseStorage.create(blobInfo, receiptBody);
        return blob.signUrl(100,TimeUnit.DAYS);
    }

    private String encodeString(String insertedData){

        return insertedData
                .replaceAll("\\.","_!_")
                .replaceAll("/","___");
    }

    private String decodeString(String retrievedData){
        return retrievedData
                .replaceAll("_!_",".")
                .replaceAll("___","/");
    }

    private UserReceipts getAllReceipts(String email) throws Throwable{
        email = encodeString(email);
        final String userReceipts = "Users/receipts/" + email;
        response = firebase.get( userReceipts );
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        return json.fromJson(decodeString,UserReceipts.class);
    }

    @Override
    public ApproveIndicator getApprovalIndicators() throws Throwable {
        final String approveIdenticatorPath = "Identicators";
        response = firebase.get( approveIdenticatorPath );
        System.out.println( "\n\nResult of GET (for the test-PUT):\n" + response );
        System.out.println("\n");
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        return json.fromJson(decodeString,ApproveIndicator.class);
    }

    @Override
    public OrderNumberApproveIndicator getOrderNumberApproval() throws Throwable {
        final String approveIdenticatorPath = "Identicators";
        response = firebase.get( approveIdenticatorPath );
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        return json.fromJson(decodeString,OrderNumberApproveIndicator.class);
    }

    @Override
    public TotalIndicator getTotalIndicator() throws Throwable {
        final String totalIndicatorPath = "Identicators";
        response = firebase.get( totalIndicatorPath );
        System.out.println( "\n\nResult of GET (for the test-PUT):\n" + response );
        System.out.println("\n");
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        return json.fromJson(decodeString,TotalIndicator.class);
    }

    @Override
    public List<Receipt> getUserReceipts(String email) throws Throwable{
        List<Receipt> userReceipts = new ArrayList<>();
        UserReceipts receipts = getAllReceipts(email);
        if(receipts == null){
            return new ArrayList<>();
        }
        if(receipts.getUserReceipts() == null){
            return null;
        }
        receipts.getUserReceipts().values()
                .forEach(receiptsMap -> receiptsMap.values().forEach(
                        receiptsCollection -> receiptsCollection.values().forEach(
                                receiptList -> userReceipts.add(receiptList))));
        return userReceipts;
    }

    @Override
    public List<Receipt> getCompanyReceiptsByUser(String email,String company) throws Throwable{
        email = encodeString(email);
        company = encodeString(company);
        final String userCompanyReceipts = "Users/receipts/" + email + "/companyList/" + company;
        response = firebase.get(userCompanyReceipts);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        UserReceiptsByCompany receipts = json.fromJson(decodeString,UserReceiptsByCompany.class);
        return receipts.getCompanyReceipts();
    }

    @Override
    public Receipt getCompanyReceiptByUser(String email, String company, long id) throws Throwable {
        final String userCompanyReceipts = "Users/receipts/" + email + "/companyList/" + company;
        response = firebase.get(userCompanyReceipts);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        UserReceiptByCompany receipts = json.fromJson(decodeString,UserReceiptByCompany.class);
        return receipts.getCompanyReceipt(Long.toString(id));
    }

    @Override
    public void setLastSearchMailTime(String email, Date lastUpdatedSearchTime) throws Throwable {
        email = encodeString(email);
        final String userCompanyReceipts = "Users/receipts/" + email + "/LastUpdatedSearchTime";
        response = firebase.put(userCompanyReceipts,new Gson().toJson(lastUpdatedSearchTime));
    }

    @Override
    public Date getLastSearchMailTime(String email) throws Throwable {
        email = encodeString(email);
        final String userCompanyReceipts = "Users/receipts/" + email + "/LastUpdatedSearchTime";
        response = firebase.get(userCompanyReceipts);
        Gson json = new Gson();
        return json.fromJson(response.getRawBody(),Date.class);
    }

    private Receipt encodeReceipt(Receipt receipt){
        receipt.setCompanyName(encodeString(receipt.getCompanyName()));
        receipt.setEmail(encodeString(receipt.getEmail()));
        return  receipt;
    }

    private Receipt decodeReceipt(Receipt receipt){
        receipt.setCompanyName(decodeString(receipt.getCompanyName()));
        receipt.setEmail(decodeString(receipt.getEmail()));
        return  receipt;
    }

    private User encodeUser(User user){
        user.setAccessToken(encodeString(user.getAccessToken()));
        user.setRefreshToken(encodeString(user.getRefreshToken()));
        user.setEmail(encodeString(user.getEmail()));
        return user;
    }

    private User decodeUser(User user){
        user.setAccessToken(decodeString(user.getAccessToken()));
        user.setRefreshToken(decodeString(user.getRefreshToken()));
        user.setEmail(decodeString(user.getEmail()));
        return  user;
    }

    @Override
    public void insertReceipt(String email, Receipt receipt) throws Throwable {
        String receiptURL = null;
        receipt.setId(receipt.getCreationDate().getTime());
        if(receipt.getType() == eContentType.PDF){
            String receiptPathInStorage = "receipts/" + email + "/" + receipt.getId() + "/" + receipt.getFileName();
            receiptURL = saveReceiptInStorage(receiptPathInStorage,receipt.getBody(), "application/pdf").toString();
            receiptURL = encodeString(receiptURL);
            receipt.setAttachmentURL(receiptURL);
        }
        receipt.setAttachmentURL(encodeString(receipt.getAttachmentURL()));
        email = encodeString(email);
        receipt.resetBody();
        receipt = encodeReceipt(receipt);
        final String userCompanyReceipts = "Users/receipts/" + email + "/companyList/" + receipt.getCompanyName() + "/receipt/" + receipt.getId();
        response = firebase.put(userCompanyReceipts,new Gson().toJson(receipt));
    }

    @Override
    public List<User> getAllUsers() throws Throwable {
        final String userPath = "Users";
        response = firebase.get(userPath);
        Gson json = new Gson();
        CredentialsResponse credentialsResponse = json.fromJson(decodeString(response.getRawBody()),CredentialsResponse.class);
        return new ArrayList<>(credentialsResponse.getCredentials().values());
    }

    @Override
    public void registerUser(User user) throws Throwable {
        user = encodeUser(user);
        final String userCredentialsPath = "Users/credentials/" + user.getEmail();
        response = firebase.patch(userCredentialsPath,new Gson().toJson(user));
    }

    @Override
    public List<String> getCompaniesNames(String email) throws Throwable {
        UserReceipts receipts = getAllReceipts(email);
        if(receipts == null){
            return new ArrayList<>();
        }
        return new ArrayList<>(receipts.getUserReceipts().keySet());

    }

    @Override
    public List<User> getAllAccessPermissionFriendshipsByUser(String email) throws Throwable {
        email = encodeString(email);
        final String userRequestsPath = "Users/friendships/" + email;
        response = firebase.get(userRequestsPath);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        AccessPermissionFriendshipResponse accessPermissionFriendshipResponse = json.fromJson(decodeString,AccessPermissionFriendshipResponse.class);
        if(accessPermissionFriendshipResponse.getAccessPermissionFriendships(email) == null){
            return null;
        }
        List<User> accessingFriendsList = new ArrayList<>(accessPermissionFriendshipResponse
                .getAccessPermissionFriendships(email)
                .values());
        for(User friend : accessingFriendsList){
            if(friend.getProfileImage() == null){
                User credentialFriend = getCredentialUser(friend.getEmail());
                friend.setProfileImage(credentialFriend.getProfileImage());
                if(credentialFriend.getProfileImage() != null){
                    fetchAccessingFriend(email, encodeString(friend.getEmail()));
                }
            }
        }
        return accessingFriendsList;
    }

    private void fetchAccessingFriend(String receiverEmail, String requesterEmail) throws Throwable {
        removeFriendShip(receiverEmail,requesterEmail);
        saveFriendship(receiverEmail,requesterEmail);
    }

    private void fetchRequests(String receiverEmail, String requesterEmail) throws Throwable {
        removeFriendShipRequest(receiverEmail,requesterEmail);
        sendFriendshipRequest(receiverEmail,requesterEmail);
    }

    @Override
    public List<User> getAllViewingPermissionFriendshipsByUser(String email) throws Throwable {
        email = encodeString(email);
        final String userRequestsPath = "Users/friendships/" + email;
        response = firebase.get(userRequestsPath);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        ViewingPermissionFriendshipResponse viewingPermissionFriendshipResponse = json.fromJson(decodeString,ViewingPermissionFriendshipResponse.class);
        if(viewingPermissionFriendshipResponse.getViewingPermissionFriendships(email) == null){
            return null;
        }
        List<User> viewingFriendList = new ArrayList<>(viewingPermissionFriendshipResponse
                .getViewingPermissionFriendships(email)
                .values());
        for(User friend : viewingFriendList){
            if(friend.getProfileImage() == null){
                User credentialFriend = getCredentialUser(friend.getEmail());
                friend.setProfileImage(credentialFriend.getProfileImage());
                if(credentialFriend.getProfileImage() != null){
                    fetchAccessingFriend(encodeString(friend.getEmail()),email);
                }
            }
        }
        return viewingFriendList;
    }

    public void addLogo(String companyName, String URL) throws JacksonUtilityException, UnsupportedEncodingException, FirebaseException {
        final String userRequestsPath = "Companies/logos";
        URL = encodeString(URL);
        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put(companyName,URL);
        response = firebase.patch( userRequestsPath,dataMap);
    }

    @Override
    public void sendFriendshipRequest(String receiverEmail, String requesterEmail) throws Throwable {
        User requesterUser = getCredentialUser(requesterEmail);
        requesterEmail = encodeString(requesterEmail);
        receiverEmail = encodeString(receiverEmail);
        final String userRequestsPath = "Users/requests/" + receiverEmail;
        Map<String,User> newRequestMap = new HashMap<>();
        newRequestMap.put(requesterEmail,requesterUser);
        response = firebase.patch(userRequestsPath,new Gson().toJson(newRequestMap));
    }

    @Override
    public void acceptFriendshipRequest(String receiverEmail, String requesterEmail) throws Throwable {
        requesterEmail = encodeString(requesterEmail);
        receiverEmail = encodeString(receiverEmail);
        removeFriendShipRequest(receiverEmail,requesterEmail);
        saveFriendship(receiverEmail,requesterEmail);
    }

    private void saveFriendship(String receiverEmail, String requesterEmail) throws Throwable{
        User receiverUser = getCredentialUser(decodeString(receiverEmail));
        User requesterUser = getCredentialUser(decodeString(requesterEmail));
        final String userFriendShipAccessPermissionPath = "Users/friendships/" + requesterEmail + "/accessPermission";
        Map<String,User> newFriendShipMap = new HashMap<>();
        newFriendShipMap.put(receiverEmail,receiverUser);
        response = firebase.patch(userFriendShipAccessPermissionPath ,new Gson().toJson(newFriendShipMap));
        final String userFriendShipViewingPath = "Users/friendships/" + receiverEmail + "/viewingPermission";
        newFriendShipMap.clear();
        newFriendShipMap.put(requesterEmail,requesterUser);
        response = firebase.patch(userFriendShipViewingPath ,new Gson().toJson(newFriendShipMap));
    }

    private void removeFriendShipRequest(String receiverEmail, String requesterEmail) throws Throwable {
        final String userRequestToDeletePath = "Users/requests/" + receiverEmail + "/" + requesterEmail;
        response = firebase.delete(userRequestToDeletePath);
    }

    @Override
    public void rejectFriendshipRequest(String receiverEmail, String requesterEmail) throws Throwable {
        requesterEmail = encodeString(requesterEmail);
        receiverEmail = encodeString(receiverEmail);
        removeFriendShipRequest(receiverEmail,requesterEmail);
    }

    @Override
    public void removeFriendShip(String toDeleteEmail,String requesterEmail) throws Throwable {
        requesterEmail = encodeString(requesterEmail);
        toDeleteEmail = encodeString(toDeleteEmail);
        final String userFriendShipAccessPermissionToDeletePath = "Users/friendships/" + toDeleteEmail + "/accessPermission/" + requesterEmail;
        final String userFriendShipViewingToDeletePath = "Users/friendships/" + requesterEmail + "/viewingPermission/" + toDeleteEmail;
        response = firebase.delete(userFriendShipAccessPermissionToDeletePath);
        response = firebase.delete(userFriendShipViewingToDeletePath);
    }

    @Override
    public List<User> getAllRequestsByUser(String email) throws Throwable {
        final String userRequestsPath = "Users";
        response = firebase.get(userRequestsPath);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        RequestsResponse requestsResponse = json.fromJson(decodeString,RequestsResponse.class);
        if(requestsResponse.getRequests(email) == null){
            return null;
        }
        List<User> friendRequests =  new ArrayList<>(requestsResponse
                .getRequests(email)
                .values());
        for(User friend : friendRequests){
            if(friend.getProfileImage() == null){
                User credentialFriend = getCredentialUser(friend.getEmail());
                friend.setProfileImage(credentialFriend.getProfileImage());
                if(credentialFriend.getProfileImage() != null){
                    fetchRequests(email, encodeString(friend.getEmail()));
                }
            }
        }

        return friendRequests;
    }

    @Override
    public User getCredentialUser(String email) throws Throwable {
        final String userRequestsPath = "Users";
        response = firebase.get( userRequestsPath);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        CredentialsResponse credentialsResponse = json.fromJson(decodeString,CredentialsResponse.class);
        return credentialsResponse.getCredentials().get(email);
    }

    @Override
    public List<CompanyLogo> getAllCompaniesLogo() throws Throwable{
        final String logosRequestsPath = "Companies";
        response = firebase.get(logosRequestsPath);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        LogosResponse logosResponse = json.fromJson(decodeString,LogosResponse.class);
        return logosResponse.getLogos().entrySet()
                .stream()
                .map(logo -> new CompanyLogo(logo.getKey(),logo.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public int getAmountOfUserReceipts(String email) throws Throwable {
        return getUserReceipts(email).size();
    }

    @Override
    public int getAmountOfCompanyReceiptsByUser(String email, String company) throws Throwable {
        return getCompanyReceiptsByUser(email,company).size();
    }

    @Override
    public int getAmountOfAccessFriendships(String email) throws Throwable {
        return getAllAccessPermissionFriendshipsByUser(email).size();
    }

    @Override
    public int getAmountOfViewingFriendships(String email) throws Throwable {
        return getAllViewingPermissionFriendshipsByUser(email).size();
    }

    @Override
    public void saveUserDisplayPicture(String email, String encodeBitmap) throws Throwable {
        User user = getCredentialUser(email);
        encodeBitmap = encodeString(encodeBitmap);
        user.setProfileImage(encodeBitmap);
        registerUser(user);
    }
}
