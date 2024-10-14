package com.example.onlineshopp.FragmentLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshopp.Object.ItemCat;

import java.util.List;

public class FragmentMeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> username=new MutableLiveData<>();
    public LiveData<String> getuser(){

        return  username;
    }
    public void setuata(String value) {
        username.setValue(value);
    }
}