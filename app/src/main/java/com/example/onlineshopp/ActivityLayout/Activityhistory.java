package com.example.onlineshopp.ActivityLayout;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.onlineshopp.Adapter.viewFragmentAdapter;
import com.example.onlineshopp.Database.ConnectFirebase;
import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.databinding.HistorybuyitemBinding;

public class Activityhistory extends AppCompatActivity {
    HistorybuyitemBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setMapping();
        eventCompment();
    }

    private void eventCompment() {
    }

    private void setMapping() {
    }
}
