package com.example.afinal.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.afinal.R;
import com.example.afinal.adapter.FavoriteAdapter;
import com.example.afinal.models.Destination;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private List<Destination> favoriteList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.rv_itemFavorite);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi list favorit Anda di sini, misalnya dari SharedPreferences atau database
        favoriteList = new ArrayList<>(); // Ganti dengan data nyata Anda

        // Inisialisasi adapter dengan requireContext() dan daftar favorit
        favoriteAdapter = new FavoriteAdapter(requireContext(), favoriteList);
        recyclerView.setAdapter(favoriteAdapter);

        // Tambahkan data ke adapter jika perlu
        loadFavoriteDestinations();
    }

    // Mengambil status favorit dari SharedPreferences
    private void loadFavoriteDestinations() {
        SharedPreferences prefs = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        List<Destination> favoriteDestinations = new ArrayList<>();

        // Iterate through SharedPreferences to get favorite destinations
        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String destinationName = entry.getKey();
            String destinationImage = prefs.getString(destinationName + "_image", "");
            String destinationCountry = prefs.getString(destinationName + "_country", "");
            // Check if the value is of type Boolean before accessing it
            if (entry.getValue() instanceof Boolean) {
                boolean isFavorite = (boolean) entry.getValue();
                // If the destination is favorite, add it to the list
                if (isFavorite) {
                    Destination destination = new Destination();
                    destination.setName(destinationName);
                    destination.setImage(destinationImage);
                    destination.setCountry(destinationCountry); // Set the country
                    destination.setFavorite(isFavorite);
                    favoriteDestinations.add(destination);
                }
            }
        }
        // Update adapter with favorite destinations outside the loop
        setFavoriteDestinations(favoriteDestinations);
    }

    private void setFavoriteDestinations(List<Destination> favoriteDestinations) {
        if (favoriteAdapter != null) {
            favoriteAdapter.setDestinations(favoriteDestinations);
        }
    }
}
