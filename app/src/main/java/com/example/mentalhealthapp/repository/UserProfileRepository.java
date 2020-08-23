package com.example.mentalhealthapp.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mentalhealthapp.java_objects.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class UserProfileRepository {

    public interface UserProfileCallback {
        void onCallback(UserModel value);
    }

    public UserModel getUserProfile(final UserProfileCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
        final UserModel profile = new UserModel();

        DocumentReference docRef = db.collection("users").document(uid);

        db.collection("users")
                //.document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (task.isSuccessful()) {
                            profile.setUid(uid);
                            profile.setImage(document.get("image").toString());
                            profile.setDate_creation(document.get("dateCreation").toString());
                            profile.setDisplay_name(document.get("display_name").toString());
                            profile.setFirst_name(document.get("first_name").toString());
                            profile.setLast_name(document.get("last_name").toString());
                            profile.setMobile_number(document.get("mobile_number").toString());
                            profile.setEmail(document.get("email").toString());
                            profile.setToDoctor(Boolean.parseBoolean(document.get("isADoctor").toString()));
                        } else {
                            Log.d(this.getClass().getName(), "Error fetching user profile");
                        }

                        callback.onCallback(profile);
                    }
                });

        return profile;
    }

}