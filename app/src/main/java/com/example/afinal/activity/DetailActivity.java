package com.example.afinal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.afinal.R;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivDestination;
    private TextView tvNegara, tvCountry, tvDesc, tvLang, tvBestTime, tvTopAtt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivDestination = findViewById(R.id.iv_destination);
        tvNegara = findViewById(R.id.tv_negara);
        tvCountry = findViewById(R.id.tv_country);
        tvDesc = findViewById(R.id.tv_desc);
        tvLang = findViewById(R.id.tv_lang);
        tvBestTime = findViewById(R.id.tv_bttv);
        tvTopAtt = findViewById(R.id.tv_ta);

        String imageUrl = getIntent().getStringExtra("imageUrl");
        String negara = getIntent().getStringExtra("negara");
        String country = getIntent().getStringExtra("country");
        String desc = getIntent().getStringExtra("desc");
        String lang = getIntent().getStringExtra("lang");
        String bestTimeToVisit = getIntent().getStringExtra("bestTimeToVisit");
        String[] topAttractionsArray = getIntent().getStringArrayExtra("topAttractions");

        Glide.with(this).load(imageUrl).into(ivDestination);
        tvNegara.setText(negara);
        tvCountry.setText(country);
        tvDesc.setText(desc);
        tvLang.setText(lang);
        tvBestTime.setText(bestTimeToVisit);

        // Inisialisasi TextView
        TextView tvTopAtt = findViewById(R.id.tv_ta);

        // Mengonversi array menjadi satu string
        if (topAttractionsArray != null) {
            StringBuilder topAttractionsStringBuilder = new StringBuilder();
            for (String attraction : topAttractionsArray) {
                topAttractionsStringBuilder.append(attraction).append("\n");
            }
            tvTopAtt.setText(topAttractionsStringBuilder.toString());
        } else {
            tvTopAtt.setText("No top attractions available.");
        }

        // Inisialisasi tombol kembali
        ImageView backButton = findViewById(R.id.iv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Menutup aktivitas saat tombol kembali diklik
            }
        });
    }
}
