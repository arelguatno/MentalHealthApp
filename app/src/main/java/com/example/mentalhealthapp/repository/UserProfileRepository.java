package com.example.mentalhealthapp.repository;


import androidx.annotation.NonNull;

import com.example.mentalhealthapp.java_objects.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileRepository {

    public interface UserProfileCallback {
        void onSuccess(UserModel value);
        void onFailure(String errorMsg);
    }

    /* Fetches user data from Firestore */
    public void getUserProfile(final UserProfileCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final UserModel profile = new UserModel();

        db.collection("users")
                .document(user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {

                        if (document.exists()) {
                            profile.setUid(user.getUid());
                            profile.setImage(document.get("image").toString());
                            profile.setDate_created(document.get("dateCreated").toString());
                            profile.setDisplay_name(document.get("display_name").toString());
                            profile.setFirst_name(document.get("first_name").toString());
                            profile.setLast_name(document.get("last_name").toString());
                            profile.setMobile_number(document.get("mobile_number").toString());
                            profile.setEmail(document.get("email").toString());
                            profile.setToDoctor(Boolean.parseBoolean(document.get("isADoctor").toString()));

                            callback.onSuccess(profile);
                        }else{
                            callback.onFailure("Problem occurred while fetching user data");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure("Error occurred while connecting to the database");
                    }
                });
    }

}
