package com.example.mentalhealthapp.repository;


import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.mentalhealthapp.java_objects.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserProfileRepository {

    public interface UserProfileCallback {
        void onSuccess(UserModel value);
        void onSuccess(String msg);
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

    public void saveProfilePhoto(final Uri input, final UserProfileCallback callback){
        // Getting current user's UID
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // Getting the input's file extension
        String fileExt = input.toString().substring(input.toString().lastIndexOf(".") + 1);
        // Defining the child of storageReference
        final StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child("user_profile")
                .child(uid + "/" + uid + "." + fileExt);

        // adding listeners on upload or failure of image
        ref.putFile(input)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Save the Uri to the user profile document in Firestore here
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(uid).update("image", uri.toString());

                            callback.onSuccess("Profile photo saved successfully");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            callback.onFailure("Error occurred while saving the photo");
                        }
                    });
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.onFailure("Error occurred while saving the photo");
                }
            });
    }

}
