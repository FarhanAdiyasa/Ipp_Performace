package com.polytechnic.astra.ac.id.ippperformance.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.polytechnic.astra.ac.id.ippperformance.API.Repository.MyRepository;
import com.polytechnic.astra.ac.id.ippperformance.Model.Login;

public class LoginViewModel extends AndroidViewModel {

    private MyRepository mMyRepository;
    private LiveData<Login> mLoginData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mMyRepository = MyRepository.get();
    }

    public LiveData<Login> getLoginData() {
        Log.e("LOGIN",  "AS");
        return mLoginData;
    }

    public LiveData<String> login(String username, String password){
        return mMyRepository.login(username, password);
    }

}
