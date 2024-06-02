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
import com.example.afinal.R;
import com.example.afinal.activity.DetailActivity;
import com.example.afinal.models.Destination;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context mContext;
    private List<Destination> mFavorites;

    public FavoriteAdapter(Context context, List<Destination> favorites) {
        this.mContext = context;
        this.mFavorites = favorites != null ? favorites : new ArrayList<>();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Destination favorite = mFavorites.get(position);
        holder.bind(favorite);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("imageUrl", favorite.getImage());
            intent.putExtra("negara", favorite.getName());
            intent.putExtra("country", favorite.getCountry());
            intent.putExtra("desc", favorite.getDescription());
            intent.putExtra("lang", favorite.getLanguage());
            intent.putExtra("bestTimeToVisit", favorite.getBestTimeToVisit());
            if (favorite.getTopAttractions() != null) {
                intent.putExtra("topAttractions", favorite.getTopAttractions().toArray(new String[0]));
            } else {
                intent.putExtra("topAttractions", new String[0]);
            }
            mContext.startActivity(intent);
        });

        holder.ivFav.setOnClickListener(v -> {
            boolean isFavorite = !favorite.isFavorite();
            favorite.setFavorite(isFavorite);
            saveFavoriteStatus(favorite.getName(), isFavorite);
            if (!isFavorite) {
                removeFavorite(position, favorite.getName(), favorite.getImage(), favorite.getCountry());
            }
            notifyItemChanged(position);
            String message = isFavorite ? "Added to favorites" : "Removed from favorites";
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        });
    }

    private void saveFavoriteStatus(String destination, boolean isFavorite) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("favorites", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(destination, isFavorite);
        editor.apply();
    }

    private void removeFavorite(int position, String destinationName, String destinationImage, String destinationCountry) {
        mFavorites.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mFavorites.size();
    }

    public void setDestinations(List<Destination> favoriteDestinations) {
        if (favoriteDestinations != null) {
            this.mFavorites = favoriteDestinations;
        } else {
            this.mFavorites = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDestination, ivFav;
        TextView tvNegara, tvCountry;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDestination = itemView.findViewById(R.id.iv_destination);
            ivFav = itemView.findViewById(R.id.iv_fav);
            tvNegara = itemView.findViewById(R.id.tv_negara);
            tvCountry = itemView.findViewById(R.id.tv_country);
        }

        public void bind(Destination favorite) {
            Glide.with(itemView.getContext()).load(favorite.getImage()).into(ivDestination);
            tvNegara.setText(favorite.getName());
            tvCountry.setText(favorite.getCountry());
            ivFav.setImageResource(favorite.isFavorite() ? R.drawable.favorite : R.drawable.favorite_border);
        }
    }
}
