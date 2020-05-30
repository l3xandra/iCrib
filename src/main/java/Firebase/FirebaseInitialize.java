package Firebase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


public class FirebaseInitialize {
	
	private static FileInputStream serviceAccount;
	private static FirebaseOptions options;
	
	public static void initialize() {

		if(serviceAccount == null) {
			try {
				serviceAccount = new FileInputStream("./serviceAccountKeys.json");
				
				
				if(options == null) {
					try {
						options = new FirebaseOptions.Builder()
						  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
						  .setDatabaseUrl("https://icrib-scmu.firebaseio.com")
						  .build();
						
						FirebaseApp.initializeApp(options);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

}
