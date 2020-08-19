package com.example.mentalhealthapp.adapters;


import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthapp.R;
import com.example.mentalhealthapp.java_objects.RecommendedListModel;

import java.util.ArrayList;

public class FeaturedListCustomAdapter extends RecyclerView.Adapter<FeaturedListCustomAdapter.viewHolder> {

    Context context;
    ArrayList<RecommendedListModel> arrayList;

    public FeaturedListCustomAdapter(Context context, ArrayList<RecommendedListModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public  FeaturedListCustomAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.featured_list, viewGroup, false);
        return new viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(FeaturedListCustomAdapter.viewHolder viewHolder,int position) {
        viewHolder.iconName.setText(arrayList.get(position).getName());
        viewHolder.icon.setImageResource(arrayList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView iconName;

        public viewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            iconName = (TextView) itemView.findViewById(R.id.icon_name);

        }
    }

}