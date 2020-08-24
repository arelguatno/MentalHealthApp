package com.example.mentalhealthapp.repository;


import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.mentalhealthapp.java_objects.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UserProfileRepository {

    public interface UserProfileCallback {
        void onSuccess(UserModel value);
        void onSuccess(String msg);
        void onFailure(String errorMsg);
    }

    /* Fetches user data from Firestore */
    public void getUserProfile(final UserProfileCallback result) {
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

                            result.onSuccess(profile);
                        }else{
                            result.onFailure("Problem occurred while fetching user data");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        result.onFailure("Error occurred while connecting to the database");
                    }
                });
    }

    /* Upload profile photo to Cloud Storage and save the download url
    to the user profile document from Firestore */
    public void saveProfilePhoto(final Uri input, final UserProfileCallback result){
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

                            result.onSuccess("Profile photo saved successfully");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            result.onFailure("Error occurred while saving the photo");
                        }
                    });
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    result.onFailure("Error occurred while saving the photo");
                }
            });
    }

    public void savePersonalDetails(final UserModel input, final UserProfileCallback result){
        // Getting current user object
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Prepares the given personal data in a map
        Map<String, Object> personalDetails = new HashMap<String, Object>() {{
            put("first_name", input.getFirst_name());
            put("last_name", input.getLast_name());
            put("display_name", input.getDisplay_name());
        }};

        // Updates the given fields in their corresponding document from Firestore
        FirebaseFirestore.getInstance().collection("users")
                .document(user.getUid()).update(personalDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Turns to Firebase Auth to update the current user's display name
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(input.getDisplay_name())
                                .build();
                        user.updateProfile(profileUpdates);

                        result.onSuccess("Successfully updated");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        result.onFailure("Error occurred while updating your personal details");
                    }
                });
    }

    public void saveContactDetails(final UserModel input, final UserProfileCallback result){
        // Getting current user object
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Prepares the given personal data in a map
        Map<String, Object> contactDetails = new HashMap<String, Object>() {{
            put("mobile_number", input.getMobile_number());
            put("email", input.getEmail());
        }};

        // Updates the given fields in their corresponding document from Firestore
        FirebaseFirestore.getInstance().collection("users")
                .document(user.getUid()).update(contactDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Turns to Firebase Auth to update the current user's display name
                        user.updateEmail(input.getEmail());

                        result.onSuccess("Successfully updated");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        result.onFailure("Error occurred while update your contact details");
                    }
                });
    }

}
