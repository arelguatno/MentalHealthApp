package com.example.mentalhealthapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.AppointmentModel;
import com.example.mentalhealthapp.ui.SplashScreen;
import com.example.mentalhealthapp.ui.VideoActivity;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.viewHolder>{

    Context context;
    ArrayList<AppointmentModel> arrayList;


    public AppointmentAdapter(Context context, ArrayList<AppointmentModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public AppointmentAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointment_list, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AppointmentAdapter.viewHolder viewHolder, final int position) {
        viewHolder.dateNumber.setText(arrayList.get(position).getDate_number());
        viewHolder.dateMonthName.setText(arrayList.get(position).getDate_month());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, VideoActivity.class);
                i.putExtra("CALL_ID", arrayList.get(position).getCall_id());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView dateNumber;
        TextView dateMonthName;

        public viewHolder(View itemView) {
            super(itemView);
            dateNumber = (TextView) itemView.findViewById(R.id.date_number2);
            dateMonthName = (TextView) itemView.findViewById(R.id.date_month_name2);

        }
    }

}
