package com.example.mentalhealthapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.UserModel;
import com.example.mentalhealthapp.repository.UserProfileRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    Button editPersonalDetailsBtn, savePersonalDetailsBtn,
        editContactDetailsBtn, saveContactDetailsBtn;

    EditText firstNameField, lastNameField,
            phoneNumField, emailField;
    TextView displayNameLabel, viewConsultationHistoryLink, signOutLink;

    private GoogleSignInClient mGoogleSignInClient;
    private UserProfileRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container,false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        // Initializes the labels and edit texts
        displayNameLabel = (TextView) v.findViewById(R.id.display_name);
        firstNameField = (EditText) v.findViewById(R.id.first_name_field);
        lastNameField = (EditText) v.findViewById(R.id.last_name_field);
        phoneNumField = (EditText) v.findViewById(R.id.mobile_number_field);
        emailField = (EditText) v.findViewById(R.id.email_field);

        // Fetch user details from database
        repository = new UserProfileRepository();
        repository.getUserProfile(new UserProfileRepository.UserProfileCallback() {
            @Override
            public void onSuccess(UserModel value) {
                // Populates the fields with the current user data
                displayNameLabel.setText(value.getDisplay_name());
                firstNameField.setText(value.getFirst_name());
                lastNameField.setText(value.getLast_name());
                phoneNumField.setText(value.getMobile_number());
                emailField.setText(value.getEmail());
            }

            @Override
            public void onFailure(String errorMsg){
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        /* Edit PERSONAL DETAILS clicked */
        editPersonalDetailsBtn = (Button) v.findViewById(R.id.edit_personal_details_btn);
        editPersonalDetailsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Enables the text fields
                firstNameField.setEnabled(true);
                lastNameField.setEnabled(true);

                //Makes the save button appear, and this one disappear
                editPersonalDetailsBtn.setVisibility(View.GONE);
                savePersonalDetailsBtn.setVisibility(View.VISIBLE);
            }
        });

        /* Save PERSONAL DETAILS clicked */
        savePersonalDetailsBtn = (Button) v.findViewById(R.id.save_personal_details_btn);
        savePersonalDetailsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Disables the text fields
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);

                //Makes the edit button appear, and this one disappear
                savePersonalDetailsBtn.setVisibility(View.GONE);
                editPersonalDetailsBtn.setVisibility(View.VISIBLE);
            }
        });

        /* Edit CONTACT DETAILS clicked */
        editContactDetailsBtn = (Button) v.findViewById(R.id.edit_contact_details_btn);
        editContactDetailsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Enables the text fields
                phoneNumField.setEnabled(true);
                emailField.setEnabled(true);

                //Makes the save button appear, and this one disappear
                editContactDetailsBtn.setVisibility(View.GONE);
                saveContactDetailsBtn.setVisibility(View.VISIBLE);
            }
        });

        /* Save CONTACT DETAILS clicked */
        saveContactDetailsBtn = (Button) v.findViewById(R.id.save_contact_details_btn);
        saveContactDetailsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Disables the text fields
                phoneNumField.setEnabled(false);
                emailField.setEnabled(false);

                //Makes the edit button appear, and this one disappear
                saveContactDetailsBtn.setVisibility(View.GONE);
                editContactDetailsBtn.setVisibility(View.VISIBLE);
            }
        });

        /* View Consultation History link clicked */
        viewConsultationHistoryLink = (TextView) v.findViewById(R.id.view_consultation_details_link);
        viewConsultationHistoryLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "View Consultation History details clicked", Toast.LENGTH_LONG).show();
            }
        });

        /* Sign Out link clicked */
        signOutLink = (TextView) v.findViewById(R.id.log_out_link);
        signOutLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut();
                navigateToLogInScreen();
            }
        });

        return v;
    }

    private void navigateToLogInScreen(){
        Intent intent = new Intent(getContext(), LoginHomeScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

}
