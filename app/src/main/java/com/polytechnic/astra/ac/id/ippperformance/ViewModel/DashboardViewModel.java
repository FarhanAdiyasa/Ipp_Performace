package com.polytechnic.astra.ac.id.ippperformance.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.ippperformance.API.Repository.MyRepository;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DashboardDataVO;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;

public class DashboardViewModel extends ViewModel {
    public MyRepository mMyRepository;



    public DashboardViewModel() {
        mMyRepository = MyRepository.get();
    }
    public LiveData<DashboardDataVO> getDashboardData() {
        return  mMyRepository.getDashboardData();
    }

    public LiveData<UserModel> getKryFoto(){
        return mMyRepository.getKryFoto();
    }

}
