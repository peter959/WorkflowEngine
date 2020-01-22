package com.mycompany.MSB.MSB;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.*;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseManager {
    
    FirebaseManager () {
        initFirebase();
    }

    void initFirebase() {
		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream(
				"/Users/pietrovassallo/Documents/GitHub/IvaSpringboot/workflow-engine-db-firebase-adminsdk-1t320-cf74df9b92.json");
			FirebaseOptions options;
		
			options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://workflow-engine-db.firebaseio.com/")
				.build();

				if (FirebaseApp.getApps().isEmpty()) {
					FirebaseApp.initializeApp(options);
				}

		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

    void insertResult(String path, String result) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        
        Map<String, String> data = new HashMap<>();
        data.put("result", result);
        
		ref.setValueAsync(data);
	}
}