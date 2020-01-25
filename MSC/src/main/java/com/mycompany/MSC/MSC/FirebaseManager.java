package com.mycompany.MSC.MSC;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.*;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class ClassData {
	public String data;

	public ClassData(String data) {
		this.data = data;
	}

}

public class FirebaseManager {
    
    FirebaseManager () {
        initFirebase();
    }

    void initFirebase() {
		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream(
				"/Users/gioelecageggi/Documents/GitHub/IvaSpringboot/workflow-engine-db-firebase-adminsdk-1t320-cf74df9b92.json");
			FirebaseOptions options;
		
			options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://workflow-engine-db.firebaseio.com/")
				.build();

				if (FirebaseApp.getApps().isEmpty()) {
					FirebaseApp.initializeApp(options);
				}
				
				//FirebaseApp.initializeApp(options);

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
	
	void getAndUnion(String path, String path2, String destinationPath) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference(path);
		DatabaseReference ref2 = database.getReference(path2);
		
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
			  String data1 = dataSnapshot.getValue(String.class);
			  System.out.println(data1);

			  ref2.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					String data2 = dataSnapshot.getValue(String.class);
					System.out.println(data2);
				  System.out.println(data1 + data2);
				  insertResult(destinationPath, data1 + data2);
				}
			  
				@Override
				public void onCancelled(DatabaseError databaseError) {
				  System.out.println("The read failed: " + databaseError.getCode());
				}
			  });
			}
			@Override
			public void onCancelled(DatabaseError databaseError) {
			  System.out.println("The read failed: " + databaseError.getCode());
			}

			
		  });
	}


	

	
	
	
}