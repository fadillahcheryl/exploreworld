package com.example.afinal.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.afinal.models.Destination;
import com.example.afinal.adapter.DestinationAdapter;
import com.example.afinal.R;
import com.example.afinal.api.RetrofitClient;
import com.example.afinal.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DestinationFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    ApiService apiService;
    DestinationAdapter destinationAdapter;
    ArrayList<Destination> destinationList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        recyclerView = view.findViewById(R.id.rv_itemDestination);

        destinationAdapter = new DestinationAdapter(getActivity(), destinationList);
        recyclerView.setAdapter(destinationAdapter);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        loadDestinations();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load destinations only if the list is empty
        if (destinationList.isEmpty()) {
            loadDestinations();
        }
    }

    private void loadDestinations() {
        Call<List<Destination>> call = apiService.getDestinations();
        call.enqueue(new Callback<List<Destination>>() {
            @Override
            public void onResponse(Call<List<Destination>> call, Response<List<Destination>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    destinationList.addAll(response.body());
                    destinationAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "No data Available", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Destination>> call, Throwable t) {
                Log.e("DestinationFragment", "Failed to fetch data", t);
                Toast.makeText(context, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}