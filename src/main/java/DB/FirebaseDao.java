package main.java.DB;

import com.google.gson.Gson;
import dbObjects.ApproveIndicator;
import main.java.DB.Entities.*;
import main.java.DB.error.FirebaseException;
import main.java.DB.error.JacksonUtilityException;
import main.java.DB.model.FirebaseResponse;
import main.java.DB.service.Firebase;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

public class FirebaseDao implements ReceiptsDAO{
    private static FirebaseDao firebaseDao = null;
    final String firebase_baseUrl = "https://iorproject.firebaseio.com/";
    private Firebase firebase;
    private FirebaseResponse response;
    private static Object lockObject = new Object();

    private FirebaseDao() throws FirebaseException {
        firebase = new Firebase( firebase_baseUrl);
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
        UserReceipts receipts = getAllReceipts(email);
        if(receipts == null){
            return new ArrayList<>();
        }
        return receipts.getUserReceipts().values().stream()
                .map(receiptsCollection -> receiptsCollection.values())
                .collect(ArrayList::new,List::addAll,List::addAll);
    }

    @Override
    public List<Receipt> getCompanyReceiptsByUser(String email,final String company) throws Throwable{
        UserReceipts receipts = getAllReceipts(email);
        if(receipts == null || !receipts.getUserReceipts().containsKey(company)){
            return new ArrayList<>();
        }
        return new ArrayList<>(receipts.getUserReceipts().get(company).values());
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
        return  user;
    }

    private User decodeUser(User user){
        user.setAccessToken(decodeString(user.getAccessToken()));
        user.setRefreshToken(decodeString(user.getRefreshToken()));
        user.setEmail(decodeString(user.getEmail()));
        return  user;
    }

    @Override
    public void insertReceipt(String email, Receipt receipt) throws Throwable {
        receipt = encodeReceipt(receipt);
        final String userCompanyReceipts = "Users/receipts/" + receipt.getEmail() + "/companyList/" + receipt.getCompanyName();
        response = firebase.post(userCompanyReceipts,new Gson().toJson(receipt));
        getCompanyReceiptsByUser(receipt.getEmail(),receipt.getCompanyName());
    }

    @Override
    public List<User> getAllUsers() throws Throwable {
        final String userPath = "Users";
        response = firebase.get(userPath);
        Gson json = new Gson();
        CredentialsResponse credentialsResponse = json.fromJson(decodeString(response.getRawBody()),CredentialsResponse.class);
        return credentialsResponse.getCredentials().values().stream()
                .collect(Collectors.toList());
    }

    @Override
    public void registerUser(User user) throws Throwable {
        user = encodeUser(user);
        final String userCredentialsPath = "Users/credentials/" + user.getEmail();
        response = firebase.patch(userCredentialsPath,new Gson().toJson(user));
        System.out.println( "\n\nResult of GET (for the test-PUT):\n" + response );
        System.out.println("\n");
        getAllUsers();
    }

    @Override
    public List<String> getCompaniesNames(String email) throws Throwable {
        UserReceipts receipts = getAllReceipts(email);
        if(receipts == null){
            return new ArrayList<>();
        }
        return receipts.getUserReceipts().keySet()
                .stream()
                .collect(Collectors.toList());

    }

    @Override
    public List<User> getAllAccessPermissionFriendshipsByUser(String email) throws Throwable {
        email = encodeString(email);
        final String userRequestsPath = "Users/friendships/" + email;
        response = firebase.get(userRequestsPath);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());

        return json.fromJson(decodeString, AccessPermissionFriendshipResponse.class)
                .getAccessPermissionFriendships(email)
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllViewingPermissionFriendshipsByUser(String email) throws Throwable {
        email = encodeString(email);
        final String userRequestsPath = "Users/friendships/" + email;
        response = firebase.get(userRequestsPath);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());

        return json.fromJson(decodeString, ViewingPermissionFriendshipResponse.class)
                .getViewingPermissionFriendships(email)
                .values()
                .stream()
                .collect(Collectors.toList());
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
        requesterEmail = encodeString(requesterEmail);
        receiverEmail = encodeString(receiverEmail);
        final String userRequestsPath = "Users/requests/" + receiverEmail;
        Map<String,User> newRequestMap = new HashMap<>();
        User requesterUser = new User(requesterEmail);
        newRequestMap.put(requesterEmail,requesterUser);
        response = firebase.patch(userRequestsPath,new Gson().toJson(newRequestMap));
    }

    @Override
    public void acceptFriendshipRequest(String receiverEmail, String requesterEmail) throws Throwable {
        requesterEmail = encodeString(requesterEmail);
        receiverEmail = encodeString(receiverEmail);
        removeFriendShipRequest(receiverEmail,requesterEmail);
        final String userFriendShipAccessPermissionPath = "Users/friendships/" + requesterEmail + "/accessPermission";
        Map<String,User> newFriendShipMap = new HashMap<>();
        User receiverUser = new User(receiverEmail);
        newFriendShipMap.put(receiverEmail,receiverUser);
        response = firebase.patch(userFriendShipAccessPermissionPath ,new Gson().toJson(newFriendShipMap));
        final String userFriendShipViewingPath = "Users/friendships/" + receiverEmail + "/viewingPermission";
        newFriendShipMap.clear();
        User requesterUser = new User(requesterEmail);
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
    public void removeFriendShip(String requesterEmail, String toDeleteEmail) throws Throwable {
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
        response = firebase.get( userRequestsPath);
        Gson json = new Gson();
        String decodeString = decodeString(response.getRawBody());
        return json.fromJson(decodeString,RequestsResponse.class)
                .getRequests(email)
                .values()
                .stream()
                .collect(Collectors.toList());
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
}
