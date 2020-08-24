package com.example.mentalhealthapp.ui;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.adapters.AppointmentAdapter;
import com.example.mentalhealthapp.adapters.FeaturedListCustomAdapter;
import com.example.mentalhealthapp.adapters.RecommendedListCustomAdapter;
import com.example.mentalhealthapp.java_objects.AppointmentModel;
import com.example.mentalhealthapp.java_objects.RecommendedListModel;
import com.example.mentalhealthapp.utility.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.example.mentalhealthapp.utility.Constants.DISPLAY_NAME;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    CarouselView carouselView;
    ArrayList<RecommendedListModel> arrayList;
    RecyclerView recyclerView;
    ArrayList<AppointmentModel> appointmentsArrayList;
    FirebaseFirestore db;

    private static final String TAG = "HomeFragment";
    private FirebaseAuth mAuth;
    public ProgressDialog mProgressDialog;

    // Carousel Images
    int[] sampleImages = {R.drawable.image_4, R.drawable.image_2, R.drawable.image_3, R.drawable.image_1};

    // Appointments
    String[] appointments_sample = {"August 22, 2020"};

    // Recommended List
    int recommended_icons[] = {R.drawable.recommended_image_1, R.drawable.recommended_image_2, R.drawable.recommended_image_3, R.drawable.recommended_image_4, R.drawable.recommended_image_5};
    String recommended_iconsName[] = {"Stay Happy", "Overcome Anxiety", "Stress Free", "Focus", "Get up"};

    // Featured List
    int featured_icons[] = {R.drawable.care_image1, R.drawable.care_image1, R.drawable.care_image1, R.drawable.care_image1, R.drawable.care_image1};
    String featured_iconsName[] = {"Feeling Tired", "Evidence and research", "We Care", "Focus", "Depression"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        showProgressDialog("Please wait...");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Image slider
        carouselView = (CarouselView) v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        //Recommended
        populateList(v, R.id.recycler_view, recommended_icons, recommended_iconsName);
        //Featured
        populateList(v, R.id.recycler_view_2, featured_icons, featured_iconsName);

        getDisplayName(v);
        //Appointments
        populateAppointments(v);
        getAppoinmentData();

        return v;
    }

    private void populateList(View v, int recycler_view, int icons[], String iconsName[]) {
        // Recommend List
        recyclerView = v.findViewById(recycler_view);
        arrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        for (int i = 0; i < icons.length; i++) {
            RecommendedListModel itemModel = new RecommendedListModel();
            itemModel.setImage(icons[i]);
            itemModel.setName(iconsName[i]);
            //add in array list
            arrayList.add(itemModel);
        }

        switch (recycler_view) {
            case R.id.recycler_view:
                RecommendedListCustomAdapter adapter = new RecommendedListCustomAdapter(v.getContext(), arrayList);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.recycler_view_2:
                FeaturedListCustomAdapter adapter2 = new FeaturedListCustomAdapter(v.getContext(), arrayList);
                recyclerView.setAdapter(adapter2);
                break;
        }
    }

    private void populateAppointments(final View v) {
        recyclerView = v.findViewById(R.id.recycler_appointment);
        appointmentsArrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        db.collection(Constants.APPOINTMENTS_COLLECTION)
                .whereEqualTo("patient_email", mAuth.getCurrentUser().getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                appointmentsArrayList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("video_room") != null) {
                        AppointmentModel itemModel = new AppointmentModel();
                        String string = doc.getString("date");
                        DateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.ENGLISH);

                        try {
                            Date date = format.parse(string);
                            itemModel.setDate_number(String.valueOf(date.getDate()));
                            itemModel.setDate_month(getDateOfTheMonth(date));
                            appointmentsArrayList.add(itemModel);

                        } catch (ParseException ex) {
                            Log.d(TAG, "Current client " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                }
                AppointmentAdapter adapter = new AppointmentAdapter(v.getContext(), appointmentsArrayList);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private void getDisplayName(final View v) {
        final TextView labelName = v.findViewById(R.id.welcome_label);

        DocumentReference docRef = db.collection(Constants.USER_COLLECTION).document(mAuth.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    DISPLAY_NAME = "No_Name";
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    DISPLAY_NAME = snapshot.getString("display_name");
                    labelName.setText("Welcome back, " + DISPLAY_NAME + "!");
                    hideProgressDialog();
                } else {
                    Log.d(TAG, "Current data: null");
                    DISPLAY_NAME = "No_Name";
                    labelName.setText("Welcome back!");
                    hideProgressDialog();
                }
            }
        });
    }

    private void getAppoinmentData() {

    }

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    private String getDateOfTheMonth(Date i) {
        switch (i.getMonth()) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";

            default:
                return "January";
        }
    }
}
