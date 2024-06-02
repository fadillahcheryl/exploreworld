package com.example.afinal.activity;

        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.View;
        import android.widget.ProgressBar;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;

        import com.example.afinal.R;
        import com.example.afinal.fragment.DestinationFragment;
        import com.example.afinal.fragment.FavoriteFragment;
        import com.example.afinal.fragment.ProfileFragment;
        import com.example.afinal.fragment.SearchFragment;
        import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        if (isNetworkAvailable()) {
            showProgressBarAndLoadFragment();
        } else {
            showNetworkError();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.fragment_destination) {
                selectedFragment = new DestinationFragment();
            } else if (itemId == R.id.fragment_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.fragment_favorite) {
                selectedFragment = new FavoriteFragment();
            } else if (itemId == R.id.fragment_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, selectedFragment)
                        .commit();

                return true;
            } else {
                return false;
            }
        });

        // Load the default fragment
        if (savedInstanceState == null) {
            // Set Fragment Destination as the default fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .commit();
        }
    }

    private void showProgressBarAndLoadFragment() {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(DestinationFragment.class.getSimpleName());
            if (fragment == null) {
                fragmentManager
                        .beginTransaction()
                        .add(R.id.flFragment, new DestinationFragment())
                        .commit();
            }
        }, 2000);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNetworkError() {
        new AlertDialog.Builder(this)
                .setTitle("Network Error")
                .setMessage("No internet connection. Please check your network settings.")
                .setPositiveButton("Retry", (dialog, which) -> {
                    if (isNetworkAvailable()) {
                        showProgressBarAndLoadFragment();
                    } else {
                        showNetworkError();
                    }
                })
                .setNegativeButton("OK", (dialog, which) -> finish())
                .show();
    }
}