package com.example.mentalhealthapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.ConsultationOfferingModel;

import java.util.List;

public class ConsultationOfferingAdapter extends ArrayAdapter<ConsultationOfferingModel> {
    List<ConsultationOfferingModel> listOfServices;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public ConsultationOfferingAdapter(Context context, int resource, List<ConsultationOfferingModel> listOfServices) {
        super(context, resource, listOfServices);
        this.context = context;
        this.resource = resource;
        this.listOfServices = listOfServices;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewTeam = view.findViewById(R.id.textViewTeam);

        //getting the listOfServices of the specified position
        ConsultationOfferingModel hero = listOfServices.get(position);

        //adding values to the list item
        imageView.setImageDrawable(context.getResources().getDrawable(hero.getImage()));
        textViewName.setText(hero.getTitle());
        textViewTeam.setText(hero.getDesc());

        //finally returning the view
        return view;
    }

}
