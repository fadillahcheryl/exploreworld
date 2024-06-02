package com.example.afinal.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.SearchView;

import com.example.afinal.models.Destination;
import com.example.afinal.R;
import com.example.afinal.api.RetrofitClient;
import com.example.afinal.adapter.SearchAdapter;
import com.example.afinal.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";  // Tag untuk log
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private ProgressBar progressBar;

    public SearchFragment() {
        // Konstruktor publik kosong yang dibutuhkan
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Mengembangkan tampilan untuk fragmen ini
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi RecyclerView dan adapter
        recyclerView = view.findViewById(R.id.rv_itemSearch);
        adapter = new SearchAdapter(requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressBar);

        // Inisialisasi SearchView
        SearchView searchView = view.findViewById(R.id.sv_search);
        searchView.setQueryHint(getString(R.string.search_hint));

        // Inisialisasi tv_searchHint
        TextView tvSearchHint = view.findViewById(R.id.tv_searchHint);
        tvSearchHint.setVisibility(View.VISIBLE); // Tampilkan tv_searchHint secara default

        // Mengatur listener untuk SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    searchDestinations(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter.setDestinations(null);
                    tvSearchHint.setVisibility(View.VISIBLE);
                    showProgressBar(false);
                } else {
                    searchDestinations(newText);
                    tvSearchHint.setVisibility(View.GONE);
                    showProgressBar(true);
                }
                return true;
            }
        });
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    private void searchDestinations(String query) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Destination>> call = apiService.searchDestinations(query);

        call.enqueue(new Callback<List<Destination>>() {
            @Override
            public void onResponse(Call<List<Destination>> call, Response<List<Destination>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Destination> destinations = response.body();
                    updateFavorites(destinations); // Update favorites before setting destinations
                    // Tampilkan hasil pencarian di bagian atas RecyclerView
                    adapter.setDestinations(destinations);
                    recyclerView.scrollToPosition(0); // Scroll ke posisi paling atas
                    if (destinations.isEmpty()) {
                        Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
                    }
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Destination>> call, Throwable t) {
                // Menangani kegagalan mengambil data
                Log.e(TAG, "Failed to fetch data", t);
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFavorites(List<Destination> destinations) {
        SharedPreferences prefs = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        for (Destination destination : destinations) {
            boolean isFavorite = prefs.getBoolean(destination.getName(), false);
            destination.setFavorite(isFavorite);
        }
    }
}


