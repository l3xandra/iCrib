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
import Entities.CribCreation;
import Entities.User;

public class FirestoreService {
	
	private Firestore dbFirestore;
	

	private final static String BABY_IN_CRIB = "The baby is in the crib";
	private final static String BABY_NOT_IN_CRIB = "The baby is not in the crib anymore.";
	
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
	
	public boolean deleteCrib(CribCreation crib) {
		
		DocumentReference documentReference = dbFirestore.collection("users").document(crib.getEmail());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		try {
			document = future.get();
		
		
			User user = null;
			
			if(document.exists()) {
				user = document.toObject(User.class);

				Map<String,String> cribs = user.getCribs();
				
				if(cribs == null) {
					return false;
				}
				
				
				if(!cribs.containsKey(crib.getCode())) {
					return false;
				}
				
				
				cribs.remove(crib.getCode());
				
				ApiFuture<WriteResult> future2 = documentReference.update("cribs", cribs);

				// ...
				WriteResult result = future2.get();
				System.out.println("Write result: " + result);
				
				
			    return true;
			}
			else {
				return false;
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
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
	
	
	public CribCreation updateCribName(CribCreation newCrib) {
		
		DocumentReference documentReference = dbFirestore.collection("users").document(newCrib.getEmail());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		
		try {
			document = future.get();
			
			
			User user = null;
			
			if(document.exists()) {
				user = document.toObject(User.class);
				
				Map<String,String> cribs = user.getCribs();
				
				if(cribs == null) {
					return null;
				}
				
				
				if(!cribs.containsKey(newCrib.getCode())) {
					return null;
				}
				
				
				cribs.put(newCrib.getCode(), newCrib.getName());
				
				ApiFuture<WriteResult> future2 = documentReference.update("cribs", cribs);

				// ...
				WriteResult result = future2.get();
				System.out.println("Write result: " + result);
				
				return newCrib;
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
	
	public Crib addCrib(String userEmail, String cribCode, Crib crib, String cribName) {

	
			DocumentReference documentReference = dbFirestore.collection("users").document(userEmail);
			ApiFuture<DocumentSnapshot> future = documentReference.get();
			
			DocumentSnapshot document;
			try {
				document = future.get();
			
			
				User user = null;
				
				if(document.exists()) {
					user = document.toObject(User.class);
					
					Map<String,String> cribs = user.getCribs();
					
					if(cribs == null) {
						user.setCribs(new HashMap<String,String>());
						cribs = user.getCribs();
					}
					
					
					if(cribs.containsKey(cribCode)) {
						return crib;
					}
					
					cribs.put(cribCode, cribName);
					
					ApiFuture<WriteResult> future2 = documentReference.update("cribs", cribs);
	
					// ...
					WriteResult result = future2.get();
					System.out.println("Write result: " + result);
					
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
	
	public boolean updateTemperature(Crib newCrib) {
		
		boolean updated = false;
		DocumentReference documentReference = dbFirestore.collection("cribs").document(newCrib.getCode());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		try {
			document = future.get();
		
		
			Crib crib = null;
			
			if(document.exists()) {

				System.out.println("7777");
				crib = document.toObject(Crib.class);
			
				System.out.println("8888");
				WriteResult result;

				System.out.println("9999");
				if(Math.abs(crib.getLastTemperatureChange() - newCrib.getRoomTemperature()) > 1) {

					System.out.println("1111");
					updated = true;
					ApiFuture<WriteResult> future2 = documentReference.update("lastTemperatureChange", newCrib.getRoomTemperature());

					System.out.println("2222");
					// ...
					result = future2.get();
		
				}
				
				ApiFuture<WriteResult> future2 = documentReference.update("roomTemperature", newCrib.getRoomTemperature());

				System.out.println("3333");
					// ...
				result = future2.get();
				System.out.println("Write result: " + result);
					
				return updated;
			}
			else {
				return false;
			}			
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
			
	}
	
public String updateIsInCrib(Crib newCrib) {
		
		String updated = null;
		
		if(newCrib.getBabyInCribVoltage() == 5.5) {
			newCrib.setIsInCrib(true);
		}
		else {
			newCrib.setIsInCrib(false);
		}
		
		
		DocumentReference documentReference = dbFirestore.collection("cribs").document(newCrib.getCode());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		try {
			document = future.get();
		
		
			Crib crib = null;
			
			if(document.exists()) {

				System.out.println("7777");
				crib = document.toObject(Crib.class);
			
				System.out.println("8888");
				WriteResult result;

				System.out.println("9999");
				if(crib.getIsInCrib() != newCrib.getIsInCrib()) {

					System.out.println("1111");
					if(newCrib.getIsInCrib()) {
						
						updated = BABY_IN_CRIB;
					}else {

						updated = BABY_NOT_IN_CRIB;
					}
					
					ApiFuture<WriteResult> future2 = documentReference.update("isInCrib", newCrib.getIsInCrib());

					System.out.println("2222");
					// ...
					result = future2.get();
		
				}
				return updated;
			}
			else {
				return updated;
			}			
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return updated;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return updated;
		}
			
			
	}

	
	public String updateMovement(Crib newCrib) {
		
		String updated = null;
		String update;
		
		if(newCrib.getBabyMovement() > 0) {
			update = "is moving!";
			newCrib.setBabyMovement(2);
		}
		else {
			update = "is still.";
			newCrib.setBabyMovement(1);
		}
		
		DocumentReference documentReference = dbFirestore.collection("cribs").document(newCrib.getCode());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		try {
			document = future.get();
		
		
			Crib crib = null;
			
			if(document.exists()) {

				System.out.println("7777");
				crib = document.toObject(Crib.class);
			
				System.out.println("8888");
				WriteResult result;

				
				System.out.println("9999");
				if(newCrib.getBabyMovement() != crib.getLastBabyMovement()) {

					System.out.println(newCrib.getBabyMovement() + "   " +  crib.getLastBabyMovement());
					
					updated = update;
					
					ApiFuture<WriteResult> future2 = documentReference.update("lastBabyMovement", newCrib.getBabyMovement());

					System.out.println("2222");
					// ...
					result = future2.get();
		
				}
				
				ApiFuture<WriteResult> future2 = documentReference.update("babyMovement", newCrib.getBabyMovement());

				System.out.println("3333");
					// ...
				result = future2.get();
				System.out.println("Write result: " + result);
					
				return updated;
			}
			else {
				return updated;
			}			
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return updated;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return updated;
		}
			
			
	}
	
	
	public String updateSound(Crib newCrib) {
		
		String updated = null;
		String update;
		
		//convert from volts
		if(newCrib.getBabySound() < 1) {

			update = "quiet.";
			newCrib.setBabySound(1);
		}
		else if(newCrib.getBabySound() > 1.8) {

			update = "crying!";
			newCrib.setBabySound(3);
		}
		else {

			update = "talking.";

			newCrib.setBabySound(2);
		}

		
		DocumentReference documentReference = dbFirestore.collection("cribs").document(newCrib.getCode());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		
		DocumentSnapshot document;
		try {
			document = future.get();
		
		
			Crib crib = null;
			
			if(document.exists()) {

				System.out.println("7777");
				crib = document.toObject(Crib.class);
			
				System.out.println("8888");
				WriteResult result;

				
				System.out.println("9999");
				if(newCrib.getBabySound() != crib.getLastBabySound()) {


					updated = update;

					ApiFuture<WriteResult> future2 = documentReference.update("lastBabySound", newCrib.getBabySound());

					System.out.println("2222");
					// ...
					result = future2.get();
		
				}
				
				ApiFuture<WriteResult> future2 = documentReference.update("babySound", newCrib.getBabySound());

				System.out.println("3333");
					// ...
				result = future2.get();
				System.out.println("Write result: " + result);
					
				return updated;
			}
			else {
				return updated;
			}			
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return updated;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return updated;
		}
			
			
	}
}
