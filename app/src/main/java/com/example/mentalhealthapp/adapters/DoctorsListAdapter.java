package com.example.mentalhealthapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.DoctorListItemModel;
import com.example.mentalhealthapp.ui.BookingConfirmedFragment;
import com.example.mentalhealthapp.ui.MainActivity;

import java.util.ArrayList;

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorsListAdapter.DoctorsViewHolder> {
    private ArrayList<DoctorListItemModel> mDoctorsList;
    public MutableLiveData<String> dateSelected;
    private Context context;

    public static class DoctorsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView docName;
        public TextView rating;
        public TextView time;
        public Button bookButton;

        public DoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView2);
            docName = itemView.findViewById(R.id.textView3);
            rating = itemView.findViewById(R.id.textView4);
            time = itemView.findViewById(R.id.textView6);
            bookButton = itemView.findViewById(R.id.button);
        }
    }

    public DoctorsListAdapter(ArrayList<DoctorListItemModel> doctorsList, MutableLiveData<String> dateSelected, Context context){
        mDoctorsList = doctorsList;
        this.dateSelected = dateSelected;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_doctor_item, parent, false);
        DoctorsViewHolder doctorsViewHolder = new DoctorsViewHolder(v);
        return doctorsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsViewHolder holder, int position) {
        final DoctorListItemModel doctor = mDoctorsList.get(position);
        holder.mImageView.setImageResource(R.drawable.app_logo);


        holder.docName.setText(doctor.getDocName());
        holder.rating.setText(doctor.getRating());
        holder.time.setText(doctor.getTime());

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Doctor", doctor.getDocName());
                Log.d("Date", dateSelected.getValue());
                Log.d("Time",doctor.getTime());
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.fragment_container, new BookingConfirmedFragment()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoctorsList.size();
    }
}
