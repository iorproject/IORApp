package main.java.DB.demo;

import java.io.IOException;
import java.util.*;

import dbObjects.ApproveIndicator;
import main.java.DB.Entities.*;
import main.java.DB.FirebaseDao;
import main.java.DB.error.FirebaseException;
import main.java.DB.error.JacksonUtilityException;
import main.java.DB.model.FirebaseResponse;
import main.java.DB.service.Firebase;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public class Demo {


	public static void main(String[] args) throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException, InterruptedException {

		
		// get the base-url (ie: 'http://gamma.firebase.com/username')

		// get the api-key (ie: 'tR7u9Sqt39qQauLzXmRycXag18Z2')
		final String firebase_apiKey = "AAAAFx7lSQ8:APA91bHgHuEV0G6OMtAzLdPdS0rHlE3EizFM_DuVQXvfgscTM-gbVeuIcLK3gZcLGIis2B1YkePVO0qC4rBwLGHsyEt57B5lKh6bSEg6-UiCN8yAekCZeZjTBQhjDnLZvvmXrpYRYzd2";

        byte[] bytes = "Paypal".getBytes();

        Receipt r = new Receipt("Paypal","ior46800@gmail.com",eContentType.STRING,bytes,new Date(),"US",300);
        Thread.sleep(50);
        Receipt f = new Receipt("Ebay","ior46800@gmail.com",eContentType.STRING,bytes,new Date(),"US",300);
        Thread.sleep(50);
        Receipt q = new Receipt("Amazon","ior46800@gmail.com",eContentType.STRING,bytes,new Date(),"US",300);

        //User user = new User("omer@gmail.com","111","1111",new Date());
        FirebaseDao firebaseDao = FirebaseDao.getInstance();
        try{
/*
            firebaseDao.insertReceipt("ior46800@gmail.com",r);
            firebaseDao.insertReceipt("ior46800@gmail.com",f);
            firebaseDao.insertReceipt("ior46800@gmail.com",q);
*/
/*
            List<Receipt> d = firebaseDao.getCompanyReceiptsByUser("ior46800@gmail.com","Paypal");
            System.out.println(d);
*/
        }
        catch (Throwable e){

        }

/*        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("מספר הזמנה",5);
        dataMap.put("מס' הזמנה",5);
        dataMap.put("מס_!_ הזמנה",5);
        dataMap.put("מספר ההזמנה",5);
        dataMap.put("מס' ההזמנה",5);
        dataMap.put("מס_!_ ההזמנה",5);
        dataMap.put("מספר אישור",5);
        dataMap.put("מס' אישור",5);
        dataMap.put("מס_!_ אישור",5);
        dataMap.put("מספר האישור",5);
        dataMap.put("מס' האישור",5);
        dataMap.put("מס_!_ האישור",5);
        dataMap.put("עסקה מספר",5);
        dataMap.put("עסקה מס_!_",5);
        dataMap.put("עסקה מס'",5);
        dataMap.put("חשבונית מס___קבלה",10);
        dataMap.put("חשבונית מס-קבלה",10);
        dataMap.put("חשבונית מס - קבלה",10);
        dataMap.put("חשבונית מס קבלה",10);
        dataMap.put("חשבונית מס מספר",10);
        dataMap.put("מספר הזמנה בפנקס",5);
        dataMap.put("מספר אישור הזמנה",5);
        dataMap.put("מס' אישור הזמנה",5);
        dataMap.put("מס_!_ אישור הזמנה",5);
        dataMap.put("הזמנתך מספר",5);
        dataMap.put("מספר הזמנת אינטרנט",5);
        dataMap.put("מס_!_ הזמנת אינטרנט",5);
        dataMap.put("מס' הזמנת אינטרנט",5);
        dataMap.put("מספר הזמנתך הוא",5);
        dataMap.put("מס_!_ הזמנתך הוא",5);
        dataMap.put("מס' הזמנתך הוא",5);
        dataMap.put("קבלה מס'",10);
        dataMap.put("קבלה מספר",10);
        dataMap.put("קבלה מס_!_",10);
        dataMap.put("הזמנה מספר",5);
        dataMap.put("מספר חשבונית",10);
        dataMap.put("מס' חשבונית",10);
        dataMap.put("מס_!_ חשבונית",10);
        dataMap.put("order",5);
        dataMap.put("order number",5);
        dataMap.put("order num_!_",5);
        dataMap.put("order num",5);
        dataMap.put("order no_!_",5);
        dataMap.put("order no",5);
        dataMap.put("booking number",5);
        dataMap.put("booking num_!_",5);
        dataMap.put("booking num",5);
        dataMap.put("booking no_!_",5);
        dataMap.put("booking no",5);
        dataMap.put("reference number",5);
        dataMap.put("reference num_!_",5);
        dataMap.put("reference num",5);
        dataMap.put("reference no_!_",5);
        dataMap.put("reference no",5);
        dataMap.put("transfer number",10);
        dataMap.put("transfer num_!_",10);
        dataMap.put("transfer num",10);
        dataMap.put("transfer no_!_",10);
        dataMap.put("receipt no",10);
        dataMap.put("transfer-receipt number",10);
        dataMap.put("transfer-receipt num_!_",10);
        dataMap.put("transfer-receipt num",10);
        dataMap.put("transfer-receipt no_!_",10);
        dataMap.put("transfer-receipt no",10);
        dataMap.put("transfer receipt number",10);
        dataMap.put("transfer receipt num_!_",10);
        dataMap.put("transfer receipt num",10);
        dataMap.put("transfer receipt no_!_",10);
        dataMap.put("transfer receipt no",10);
        dataMap.put("transfer - receipt number",10);
        dataMap.put("transfer - receipt num_!_",10);
        dataMap.put("transfer - receipt num",10);
        dataMap.put("transfer - receipt no_!_",10);
        dataMap.put("transfer - receipt no",10);
        dataMap.put("receipt-transfer number",10);
        dataMap.put("receipt-transfer num_!_",10);
        dataMap.put("receipt-transfer num",10);
        dataMap.put("receipt-transfer no_!_",10);
        dataMap.put("receipt-transfer no",10);
        dataMap.put("receipt transfer number",10);
        dataMap.put("receipt transfer num_!_",10);
        dataMap.put("receipt transfer num",10);
        dataMap.put("receipt transfer no_!_",10);
        dataMap.put("receipt transfer no",10);
        dataMap.put("receipt - transfer number",10);
        dataMap.put("receipt - transfer num_!_",10);
        dataMap.put("receipt - transfer num",10);
        dataMap.put("receipt - transfer no_!_",10);
        dataMap.put("receipt - transfer no",10);
        String firebase_baseUrl = "https://iorproject.firebaseio.com/";
        Firebase firebase = new Firebase( firebase_baseUrl );
        firebase.put( "Identicators/orderNumberApprovalList", dataMap );*/
        /*try {
            firebaseDao.insertReceipt(r);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/
       // Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        /*Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        Map<String, Object> dataMap2 = new LinkedHashMap<String, Object>();
        dataMap2.put("accessToken","111");
        dataMap2.put("refreshToken","1111");
        dataMap.put("omerblechman@gmail.com",dataMap2);*/
        //firebaseDao.registerUser(user);
        try {
/*
            List<CompanyLogo> c = firebaseDao.getAllCompaniesLogo();
            System.out.println(c);
*/
            //firebaseDao.sendFriendshipRequest("ior46800@gmail.com","omer@gmail.com");
            firebaseDao.acceptFriendshipRequest("omer@gmail.com","ior46800@gmail.com");
            firebaseDao.acceptFriendshipRequest("omerblechman@gmail.com","ior46800@gmail.com");
            firebaseDao.acceptFriendshipRequest("ior46800@gmail.com","omerblechman@gmail.com");
//
           // List<User> f =firebaseDao.getAllViewingPermissionFriendshipsByUser("ior46800@gmail.com");
//            List<User> f =firebaseDao.getAllRequestsByUser("ior46800@gmail.com");
          //  System.out.println(f);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

/*
		// create the firebase
		Firebase firebase = new Firebase( firebase_baseUrl );


		// "DELETE" (the fb4jDemo-root)
		FirebaseResponse response;

        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        List<String> dataMap2 = new LinkedList<String>();
        List<String> dataMap3 = new LinkedList<String>();
        List<String> dataMap4 = new LinkedList<String>();
       // List<String> dataMap5 = new LinkedList<String>();
        Map<String,Object> dataMap5 = new HashMap<>();

        dataMap5.put("אישור הזמנה",15);
        dataMap5.put("פרטי תשלום",15);
        dataMap5.put("ship to",10);
        dataMap5.put("Arriving",10);
        dataMap5.put("Order",15);
        dataMap5.put("מספר אישור",15);
        dataMap5.put("כרטיס אשראי",15);
        dataMap5.put("כרטיס האשראי",15);
        dataMap5.put("אשראי",10);
        dataMap5.put("התשלום",10);
        dataMap5.put("האשראי",10);
        dataMap5.put("תשלום",10);
        dataMap5.put("כמות, מחיר",20);
        dataMap5.put("קבלה",15);
        dataMap5.put("הקבלה",15);
        dataMap5.put("מזהה עסקה",15);
        dataMap5.put("דמי משלוח",15);
        dataMap5.put("דמי טיפול",15);
        dataMap5.put("חשבונית",15);
        dataMap5.put("חשבונית מס",30);
        dataMap5.put("חשבונית מס-קבלה",30);
        dataMap5.put("חשבונית קבלה",30);
        dataMap5.put("מק\"ט",5);
        dataMap5.put("תקבולים",10);
        dataMap5.put("התקבולים",10);
        dataMap5.put("משלוח",10);
        dataMap5.put("הזמנה",10);
        dataMap5.put("Item",15);
        dataMap5.put("פריט",10);
        dataMap5.put("מוצר",10);
        dataMap5.put("פרטי הזמנה",30);
        dataMap5.put("טיפול ומשלוח",15);
        dataMap5.put("קוד מוצר",5);

        dataMap5.put("חשבונית מס___קבלה",30);
        dataMap5.put("עסקה___קבלה",20);*/
/*

		// "PUT" (test-map into the fb4jDemo-root)
		dataMap.put( "PUT-root", "This was PUT into the fb4jDemo-root" );
		response = firebase.put( dataMap );
		System.out.println( "\n\nResult of PUT (for the test-PUT to fb4jDemo-root):\n" + response );
		System.out.println("\n");


		// "GET" (the fb4jDemo-root)
		response = firebase.get();
		System.out.println( "\n\nResult of GET:\n" + response );
		System.out.println("\n");
*/
/*            dataMap4.add("סה\"כ");
            dataMap4.add("סך הכל");
            dataMap4.add("סך-הכל");
            dataMap4.add("totals");
            dataMap4.add("total");

            dataMap3.add("total incl. tax");
            dataMap3.add("total incl. taxes");
            dataMap3.add("total include tax");
            dataMap3.add("total include taxes");


        dataMap2.add("מחיר סופי");
        dataMap2.add("order total");
        dataMap2.add("סכום");
        dataMap2.add("תשלום");
        dataMap2.add("סה\"כ מחיר");
        dataMap2.add("סה\"כ תקבולים");
        dataMap2.add("סה\"כ לתקבולים");
        dataMap2.add("סה\"כ כללי");
        dataMap2.add("סה\"כ לתשלום");
        dataMap2.add("סה\"כ תשלום");
        dataMap2.add("סך הכל מחיר");
        dataMap2.add("סך הכל לתקבולים");
        dataMap2.add("סך הכל תקבולים");
        dataMap2.add("סך הכל כללי");
        dataMap2.add("סך הכל לתשלום");
        dataMap2.add("סך הכל תשלום");
        dataMap2.add("סך-הכל מחיר");
        dataMap2.add("סך-הכל תקבולים");
        dataMap2.add("סך-הכל לתקבולים");
        dataMap2.add("סך-הכל כללי");
        dataMap2.add("סך-הכל לתשלום");
        dataMap2.add("סך-הכל תשלום");
        dataMap2.add("הסכום הכולל");
        dataMap2.add("סך-תשלום");

        dataMap5.add("מחיר סופי כולל מע\"מ");
        dataMap5.add("מחיר סופי כולל מיסים");
        dataMap5.add("order total incl. tax");
        dataMap5.add("order total incl. taxes");
        dataMap5.add("order total include taxes");
        dataMap5.add("order total include tax");
        dataMap5.add("סכום כולל מע\"מ");
        dataMap5.add("סכום כולל מיסים");
        dataMap5.add("תשלום כולל מע\"מ");
        dataMap5.add("תשלום כולל מיסים");
        dataMap5.add("סה\"כ מחיר כולל מע\"מ");
        dataMap5.add("סה\"כ מחיר כולל מיסים");
        dataMap5.add("סה\"כ תקבולים כולל מע\"מ");
        dataMap5.add("סה\"כ תקבולים כולל מיסים");
        dataMap5.add("סה\"כ לתקבולים כולל מיסים");
        dataMap5.add("סה\"כ לתקבולים כולל מע\"מ");
        dataMap5.add("סה\"כ כולל מיסים");
        dataMap5.add("סה\"כ כולל מע\"מ");
        dataMap5.add("סה\"כ כללי כולל מיסים");
        dataMap5.add("סה\"כ כללי כולל מע\"מ");
        dataMap5.add("סה\"כ לתשלום כולל מיסים");
        dataMap5.add("סה\"כ לתשלום כולל מע\"מ");
        dataMap5.add("סה\"כ תשלום כולל מיסים");
        dataMap5.add("סה\"כ תשלום כולל מע\"מ");
        dataMap5.add("סך הכל מחיר כולל מע\"מ");
        dataMap5.add("סך הכל מחיר כולל מיסים");
        dataMap5.add("סך הכל לתקבולים כולל מע\"מ");
        dataMap5.add("סך הכל לתקבולים כולל מיסים");
        dataMap5.add("סך הכל תקבולים כולל מע\"מ");
        dataMap5.add("סך הכל תקבולים כולל מיסים");
        dataMap5.add("סך הכל כולל מיסים");
        dataMap5.add("סך הכל כולל מע\"מ");
        dataMap5.add("סך הכל כללי כולל מיסים");
        dataMap5.add("סך הכל כולל מע\"מ");
        dataMap5.add("סך הכל כללי כולל מיסים");
        dataMap5.add("סך הכל כללי כולל מע\"מ");
        dataMap5.add("סך הכל כללי כולל מיסים");
        dataMap5.add("סך הכל כללי כולל מע\"מ");
        dataMap5.add("סך הכל לתשלום כולל מיסים");
        dataMap5.add("סך הכל לתשלום כולל מע\"מ");
        dataMap5.add("סך הכל תשלום כולל מיסים");
        dataMap5.add("סך הכל תשלום כולל מע\"מ");
        dataMap5.add("סך-הכל מחיר כולל מע\"מ");
        dataMap5.add("סך-הכל מחיר כולל מיסים");
        dataMap5.add("סך-הכל תקבולים כולל מע\"מ");
        dataMap5.add("סך-הכל לתקבולים כולל מע\"מ");
        dataMap5.add("סך-הכל תקבולים כולל מיסים");
        dataMap5.add("סך-הכל לתקבולים כולל מיסים");
        dataMap5.add("סך-הכל כולל מיסים");
        dataMap5.add("סך-הכל כולל מע\"מ");
        dataMap5.add("סך-הכל כללי כולל מיסים");
        dataMap5.add("סך-הכל כללי כולל מע\"מ");
        dataMap5.add("סך-הכל לתשלום כולל מיסים");
        dataMap5.add("סך-הכל לתשלום כולל מע\"מ");
        dataMap5.add("סך-הכל תשלום כולל מיסים");
        dataMap5.add("סך-הכל תשלום כולל מע\"מ");
        dataMap5.add("הסכום הכולל מיסים");
        dataMap5.add("הסכום הכולל מע\"מ");
        dataMap5.add("סך-תשלום כולל מיסים");
        dataMap5.add("סך-תשלום כולל מע\"מ");

        dataMap.put("10",dataMap2);
        dataMap.put("15",dataMap3);
        dataMap.put("5",dataMap4);
        dataMap.put("20",dataMap5);*/
      //  firebase.put( "Identicators/approvedNumberOfReceiptList", dataMap );
        /*response = firebase.get( "Identicators/Totals");
        System.out.println(response);*/
        //firebase.put( "Identicators/approvals", dataMap5 );
       /* FirebaseDao dao = FirebaseDao.getInstance();
        ApproveIndicator d = dao.getApprovalIndicators();
        System.out.println(d);*/
        // "PUT" (test-map into a sub-node off of the fb4jDemo-root)
/*
		dataMap = new LinkedHashMap<String, Object>();
		dataMap.put( "Key_1", "This is the first value" );
		dataMap.put( "Key_2", "This is value #2" );
		Map<String, Object> dataMap2 = new LinkedHashMap<String, Object>();
		dataMap2.put( "Sub-Key1", new MessageObject(1,"d") );
        System.out.println(dataMap.toString());
		dataMap.put( "Key_3", dataMap2);
		response = firebase.put( "test-PUT/Omer/", dataMap );
		System.out.println( "\n\nResult of PUT (for the test-PUT):\n" + response );
		System.out.println("\n");


		// "GET" (the test-PUT)
		response = firebase.get( "test-PUT" );
		System.out.println( "\n\nResult of GET (for the test-PUT):\n" + response );
		System.out.println("\n");


		// "POST" (test-map into a sub-node off of the fb4jDemo-root)
		response = firebase.post( "test-POST", dataMap );
		System.out.println( "\n\nResult of POST (for the test-POST):\n" + response );
		System.out.println("\n");


		// "DELETE" (it's own test-node)
		dataMap = new LinkedHashMap<String, Object>();
		dataMap.put( "DELETE", "This should not appear; should have been DELETED" );
		response = firebase.put( "test-DELETE", dataMap );
		System.out.println( "\n\nResult of PUT (for the test-DELETE):\n" + response );
		response = firebase.delete( "test-DELETE");
		System.out.println( "\n\nResult of DELETE (for the test-DELETE):\n" + response );
		response = firebase.get( "test-DELETE" );
		System.out.println( "\n\nResult of GET (for the test-DELETE):\n" + response );

		// Sign Up user for Firebase's Auth Service demo (https://firebase.google.com/docs/reference/rest/auth/)
		if(firebase_apiKey != null) {

			firebase = new Firebase(firebase_baseUrl, true);
			firebase.addQuery("key", firebase_apiKey);

			dataMap.clear();
			dataMap.put("email", "elonmusk@tesla.com");
			dataMap.put("password", "TeslaRocks");
			dataMap.put("returnSecureToken", true);

			response = firebase.post("signupNewUser", dataMap);
			System.out.println("\n\nResult of Signing Up:\n" + response);
			System.out.println("\n");

		} else {
			System.out.println("\n\nResult of Signing Up:\n failed, because no API Key was provided.");
			System.out.println("\n");
		}
*/

	}
	
}




