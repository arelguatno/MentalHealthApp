package com.example.mentalhealthapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentalhealthapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void signupButton(View view){
        EditText emailTextHolder = (EditText) findViewById(R.id.email_txt);
        final String  email= emailTextHolder.getText().toString();

        EditText passwordTextHolder = (EditText) findViewById(R.id.password_txt);
        String password = passwordTextHolder.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            EditText firstNameHolder = (EditText) findViewById(R.id.first_name_txt);
                            String firstName = firstNameHolder.getText().toString();

                            EditText lastNameHolder = (EditText) findViewById(R.id.last_name_txt);
                            String lastName = lastNameHolder.getText().toString();

                            EditText contactInfoHolder = (EditText) findViewById(R.id.contact_info_txt);
                            String contactInfo = contactInfoHolder.getText().toString();

                            FirebaseUser user = mAuth.getCurrentUser();

                            Map<String, Object> data = new HashMap<>();
                            data.put("dateCreated", FieldValue.serverTimestamp());
                            data.put("display_name", firstName+" "+lastName);
                            data.put("first_name", firstName);
                            data.put("last_name", lastName);
                            data.put("email", email);
                            data.put("image", "");
                            data.put("isADoctor", false);
                            data.put("mobile_number", contactInfo);


                            db.collection("users").document(user.getUid()).set(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccount.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


}