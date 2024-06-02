package com.example.afinal;

import com.example.afinal.models.Destination;

import java.util.List;

public class DestinationResponse {
    private List<Destination> data;
    public List<Destination> getData() {
        return data;
    }
    public void setData(List<Destination> data) {
        this.data = data;
    }
}
