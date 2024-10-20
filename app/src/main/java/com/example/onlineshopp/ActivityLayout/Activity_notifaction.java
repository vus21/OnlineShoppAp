package com.example.onlineshopp.ActivityLayout;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshopp.databinding.ActivityPaymentBinding;
import com.example.onlineshopp.databinding.LayoutnotificationBinding;

import java.util.ArrayList;

public class Activity_notifaction extends AppCompatActivity {
    LayoutnotificationBinding binding;
    private final String TAG="Activity_payment";
    private ArrayList<String> listmethods=new ArrayList<>();
    private ArrayList<Integer> listfreeship=new ArrayList<>();
    private  int selectionPosition =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutnotificationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
