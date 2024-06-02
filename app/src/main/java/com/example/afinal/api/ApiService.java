package com.example.afinal.api;

import com.example.afinal.models.Destination;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("destinations")
    Call<List<Destination>> getDestinations();

    @GET("destinations")
    Call<List<Destination>> searchDestinations(@Query("search") String query);
}
