package com.example.afinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afinal.models.Destination;
import com.example.afinal.activity.DetailActivity;
import com.example.afinal.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context mContext;
    private List<Destination> mDestinations;
    private SharedPreferences sharedPreferences;

    public SearchAdapter(Context context, List<Destination> destinationList) {
        this.mContext = context;
        this.mDestinations = (destinationList != null) ? destinationList : new ArrayList<>();
        this.sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
    }

    public SearchAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public void setDestinations(List<Destination> destinations) {
        this.mDestinations = (destinations != null) ? destinations : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
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

        holder.ivFav.setOnClickListener(v -> {
            destination.setFavorite(!destination.isFavorite());
            saveFavoriteStatus(destination);
            notifyItemChanged(position);
            // Tampilkan toast sesuai dengan status favorit yang baru disimpan
            if (destination.isFavorite()) {
                Toast.makeText(mContext, "Added to Favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveFavoriteStatus(Destination destination) {
        SharedPreferences prefs = mContext.getSharedPreferences("favorites", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(destination.getName(), destination.isFavorite());
        editor.putString(destination.getName() + "_image", destination.getImage());
        editor.putString(destination.getName() + "_country", destination.getCountry());
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return mDestinations != null ? mDestinations.size() : 0;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDestination, ivFav;
        TextView tvNegara, tvCountry;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDestination = itemView.findViewById(R.id.iv_destination);
            ivFav = itemView.findViewById(R.id.iv_fav);
            tvNegara = itemView.findViewById(R.id.tv_negara);
            tvCountry = itemView.findViewById(R.id.tv_country);
        }

        public void bind(Destination destination) {
            Glide.with(itemView.getContext()).load(destination.getImage()).into(ivDestination);
            tvNegara.setText(destination.getName());
            tvCountry.setText(destination.getCountry());
            ivFav.setImageResource(destination.isFavorite() ? R.drawable.favorite : R.drawable.favorite_border);
        }
    }
}
