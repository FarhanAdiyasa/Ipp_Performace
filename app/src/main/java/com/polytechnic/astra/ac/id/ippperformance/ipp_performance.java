package com.polytechnic.astra.ac.id.ippperformance;

import android.app.Application;
import com.polytechnic.astra.ac.id.ippperformance.API.Repository.MyRepository;

public class ipp_performance extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        MyRepository.initialize(this);
    }
}
