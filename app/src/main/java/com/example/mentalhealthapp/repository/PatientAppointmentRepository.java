package com.example.mentalhealthapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentalhealthapp.java_objects.PatientListItemModel;
import com.example.mentalhealthapp.java_objects.UserModel;
import com.example.mentalhealthapp.utility.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class PatientAppointmentRepository {
    String selectedDate;

    public PatientAppointmentRepository(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public interface FetchPatientAppointmentCallback {
        void onSuccess(ArrayList<PatientListItemModel> list);
        void onFailure(String errorMsg);
    }

    public interface FetchPatientDataCallback {
        void onSuccess(HashMap<String, String> value);
        void onFailure();
    }

    /* Fetches user data from Firestore */
    public void getPatientAppointmentList(final PatientAppointmentRepository.FetchPatientAppointmentCallback result) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Prepares an item model list
        final ArrayList<PatientListItemModel> patientList = new ArrayList<>();

        db.collection(Constants.APPOINTMENTS_COLLECTION)
                .whereEqualTo("doctor_email", user.getEmail())
                .whereEqualTo("date", selectedDate)
                .orderBy("time")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            result.onFailure(e.getLocalizedMessage());
                            Log.d("test", e.getMessage());
                            return;
                        }
                        patientList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            final PatientListItemModel patientAppointment = new PatientListItemModel();
                            patientAppointment.setDateTime(doc.getString("date") + " " + doc.getString("time"));
                            patientAppointment.setPatientEmail(doc.getData().get("patient_email").toString());
                            patientAppointment.setPatientName(Constants.ANONYMOUS_LABEL);
                            patientAppointment.setPhotoURL("");
                            patientAppointment.setVideoRoom(doc.getString("video_room").toString());
                            patientList.add(patientAppointment);
                        }
                        // Returns a success result if the list has a data
                        if (!patientList.isEmpty()){
                            result.onSuccess(patientList);
                        }else{
                            result.onFailure("No patient has booked an appointment yet");
                        }
                    }
                });
    }

    public void getPatientDetails(String email, final PatientAppointmentRepository.FetchPatientDataCallback result){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Prepares an item model list
        final HashMap<String, String> map = new HashMap<>();

        db.collection(Constants.USER_COLLECTION)
                .whereEqualTo("email", email)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            map.put("patient_name", doc.getData().get("display_name").toString());
                            map.put("patient_photo", doc.getData().get("image").toString());
                            // Only gets the first instance of the (assuming there's only one profile per unique email)
                            break;
                        }
                        // Returns a success result if the list has a data
                        if (!map.isEmpty()){
                            result.onSuccess(map);
                        }else{
                            result.onFailure();
                        }
                    }
                });
    }

}
