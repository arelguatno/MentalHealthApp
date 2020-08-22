package com.example.mentalhealthapp.ui;

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

public class ProfileFragment extends Fragment {

    Button editPersonalDetailsBtn, savePersonalDetailsBtn,
        editContactDetailsBtn, saveContactDetailsBtn;

    EditText firstNameField, lastNameField,
            phoneNumField, emailField;
    TextView viewConsultationHistoryLink, signOutLink;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container,false);

        // Initializes the edit texts
        firstNameField = (EditText) v.findViewById(R.id.first_name_field);
        lastNameField = (EditText) v.findViewById(R.id.last_name_field);
        phoneNumField = (EditText) v.findViewById(R.id.mobile_number_field);
        emailField = (EditText) v.findViewById(R.id.email_field);

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
                Toast.makeText(getContext(), "Sign out clicked", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
