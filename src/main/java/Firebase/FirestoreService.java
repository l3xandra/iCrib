package Firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import Entities.Crib;
import Entities.CribSimplified;
import Entities.User;

public class FirestoreService {
	
	private Firestore dbFirestore;
	
	public FirestoreService() {
		dbFirestore = FirestoreClient.getFirestore();
	}
	
	public Crib getCrib(String id) {
		
		DocumentReference documentReference = dbFirestore.collection("cribs").document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		try {
			document = future.get();
		
		
			Crib crib = null;
			
			if(document.exists()) {
				crib = document.toObject(Crib.class);
				
			    return crib;
			}
			else {
				return null;
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public  Map<String, String> getCribs(User u) {
		
		DocumentReference documentReference = dbFirestore.collection("users").document(u.getEmail());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		try {
			document = future.get();
		
		
			User user = null;
			
			if(document.exists()) {
				user = document.toObject(User.class);
				
			    return user.getCribs();
			}
			else {
				return null;
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public User getUser(String email) {
		
		DocumentReference documentReference = dbFirestore.collection("users").document(email);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		try {
			document = future.get();
		
		
			User user = null;
			
			if(document.exists()) {
				user = document.toObject(User.class);
				
			    return user;
			}
			else {
				return null;
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean register(User user) {


		Map<String, Object> docData = new HashMap<>();
		docData.put("email", user.getEmail());
		docData.put("password", user.getPassword());
		
		// Add a new document (asynchronously) in collection "cities" with id "LA"
		ApiFuture<WriteResult> future = dbFirestore.collection("users").document(user.getEmail()).set(docData);
		
		return true;
		
	}
	
	public boolean login(User user) {

		String token = UUID.randomUUID().toString();
		
		Map<String, Object> docData = new HashMap<>();
		docData.put("email", user.getEmail());
		docData.put("password", user.getPassword());
		docData.put("cribs", user.getCribs());
		docData.put("token", token);
		
		// Add a new document (asynchronously) in collection "cities" with id "LA"
		ApiFuture<WriteResult> future = dbFirestore.collection("users").document(user.getEmail()).set(docData);
		
		return true;
		
	}
	
	/**public boolean createCrib(String id) {
		
	}
	
	public boolean deleteCrib(String id) {
		
	}**/

	
}
