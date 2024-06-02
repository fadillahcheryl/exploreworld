package com.example.afinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afinal.models.Destination;
import com.example.afinal.R;
import com.example.afinal.activity.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {

    private Context mContext;
    private List<Destination> mDestinations;

    public DestinationAdapter(Context context, List<Destination> destinationList) {
        this.mContext = context;
        this.mDestinations = (destinationList != null) ? destinationList : new ArrayList<>();
    }

    public DestinationAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public void setDestinations(List<Destination> destinations) {
        this.mDestinations = (destinations != null) ? destinations : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_destination, parent, false);
        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        Destination destination = mDestinations.get(position);
        holder.bind(destination);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("imageUrl", destination.getImage());
            intent.putExtra("negara", destination.getName());
            intent.putExtra("country", destination.getCountry());
            intent.putExtra("desc", destination.getDescription());
            intent.putExtra("lang", destination.getLanguage());
            intent.putExtra("bestTimeToVisit", destination.getBestTimeToVisit());
            intent.putExtra("topAttractions", destination.getTopAttractions().toArray(new String[0]));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDestinations != null ? mDestinations.size() : 0;
    }

    public static class DestinationViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDestination;
        TextView tvNegara;

        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDestination = itemView.findViewById(R.id.iv_destination);
            tvNegara = itemView.findViewById(R.id.tv_negara);
        }

        public void bind(Destination destination) {
            Glide.with(itemView.getContext()).load(destination.getImage()).into(ivDestination);
            tvNegara.setText(destination.getName());
        }
    }
}