package com.example.mentalhealthapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    CarouselView carouselView;
    ArrayList<RecommendedListModel> arrayList;
    RecyclerView recyclerView;
    ArrayList<AppointmentModel> appointmentsArrayList;

//    Button

    // Carousel Images
    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4};

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

        // Image slider
        carouselView = (CarouselView) v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        //Appointments
        populateAppointments(v, appointments_sample);

        //Recommended
        populateList(v, R.id.recycler_view, recommended_icons, recommended_iconsName);
        //Featured
        populateList(v, R.id.recycler_view_2, featured_icons, featured_iconsName);

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


    private void populateAppointments(View v, String[] appointments_sample) {
        recyclerView = v.findViewById(R.id.recycler_appointment);
        appointmentsArrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < appointments_sample.length; i++) {
            AppointmentModel itemModel = new AppointmentModel();

            itemModel.setDate_number("22");
            itemModel.setDate_month("August");
            appointmentsArrayList.add(itemModel);
        }

        AppointmentAdapter adapter = new AppointmentAdapter(v.getContext(), appointmentsArrayList);
        recyclerView.setAdapter(adapter);


    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
}
