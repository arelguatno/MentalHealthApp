package com.example.mentalhealthapp.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.UserModel;
import com.example.mentalhealthapp.repository.UserProfileRepository;
import com.example.mentalhealthapp.utility.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    Button editPersonalDetailsBtn, savePersonalDetailsBtn,
        editContactDetailsBtn, saveContactDetailsBtn;

    EditText firstNameField, lastNameField,
            phoneNumField, emailField;
    TextView displayNameLabel, viewConsultationHistoryLink, signOutLink,
            uploadPhotoLink, savePhotoLink;
    CircleImageView profilePicImageView;
    Uri profilePicUri;

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

        // Initializes the essential UI elements of this fragment
        profilePicImageView = (CircleImageView) v.findViewById(R.id.profile_image);
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
                // Renders profile photo (if exists)
                if (!value.getImage().isEmpty()){
                    Picasso.get().load(value.getImage()).into(profilePicImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Do nothing
                        }

                        @Override
                        public void onError(Exception e) {
                            // Put back the anonymous profile placeholder in case something goes wrong
                            profilePicImageView.setImageResource(R.drawable.profile_photo_placeholder);
                        }
                    });
                }
                // Populates the fields with the current user data
                displayNameLabel.setText(value.getDisplay_name());
                firstNameField.setText(value.getFirst_name());
                lastNameField.setText(value.getLast_name());
                phoneNumField.setText(value.getMobile_number());
                emailField.setText(value.getEmail());
            }

            @Override
            public void onSuccess(String msg) {
                // Do nothing
            }

            @Override
            public void onFailure(String errorMsg){
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        /* UPLOAD PHOTO clicked */
        uploadPhotoLink = (TextView) v.findViewById(R.id.upload_photo_link);
        uploadPhotoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        /* SAVE PHOTO clicked */
        savePhotoLink = (TextView) v.findViewById(R.id.save_photo_link);
        savePhotoLink.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                //Save image to Cloud Storage and URL to Firestore
                uploadImage();
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

    private void showFileChooser() {
        Dialog dialog = new Dialog(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[] { "Gallery", "Camera" },
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");

                            Intent chooser = Intent.createChooser(intent, "Choose a Picture");
                            startActivityForResult(chooser, Constants.ACTION_REQUEST_GALLERY);
                            break;

                        case 1:
                            Intent cameraIntent = new Intent(
                                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, Constants.ACTION_REQUEST_CAMERA);
                            break;

                        default:
                            break;
                    }
                }
            });

        builder.show();
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == Constants.ACTION_REQUEST_GALLERY) {
                profilePicUri = data.getData();
                try {
                    // Tries to render the image from gallery to the image view
                    profilePicImageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), profilePicUri));
                    // Gives an option to save the changes
                    uploadPhotoLink.setVisibility(View.GONE);
                    savePhotoLink.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Put back the anonymous profile placeholder in case something goes wrong
                    profilePicImageView.setImageResource(R.drawable.profile_photo_placeholder);
                }
            } else if (requestCode == Constants.ACTION_REQUEST_CAMERA) {
                Bitmap capturedPhoto = (Bitmap) data.getExtras().get("data");
                profilePicImageView.setImageBitmap(capturedPhoto);
                // Gives an option to save the changes
                uploadPhotoLink.setVisibility(View.GONE);
                savePhotoLink.setVisibility(View.VISIBLE);
            }
        }
    }

    private void uploadImage() {
        if (profilePicUri != null) {
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            UserProfileRepository repository = new UserProfileRepository();
            repository.saveProfilePhoto(profilePicUri, new UserProfileRepository.UserProfileCallback() {
                @Override
                public void onSuccess(UserModel value) {
                    // Do nothing
                }

                @Override
                public void onSuccess(String msg) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(String errorMsg) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                }
            });

        }else{
            Toast.makeText(getContext(), "File cannot be saved", Toast.LENGTH_LONG).show();
        }
    }

}
