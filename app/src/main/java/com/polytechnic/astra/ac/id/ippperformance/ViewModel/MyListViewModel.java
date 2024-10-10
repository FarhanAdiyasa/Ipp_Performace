package com.polytechnic.astra.ac.id.ippperformance.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.polytechnic.astra.ac.id.ippperformance.API.Repository.MyRepository;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.AddPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.CreatePekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.PekerjaanViewVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.RekapitulasiVO;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;

import java.util.List;

public class MyListViewModel extends ViewModel {
    private MutableLiveData<List<PekerjaanViewVO>> mMyModelListMutableLiveData;
    private MyRepository mMyRepository;
    private MutableLiveData<List<CreatePekerjaanVO>> mCreatePekerjaan;
    private LiveData<RekapitulasiVO> mRekapitulasi;


    public MyListViewModel() {
        mMyRepository = MyRepository.get();

    }

    public MutableLiveData<List<PekerjaanViewVO>> getListModel() {
        mMyModelListMutableLiveData = mMyRepository.getMyListProdi();
        return mMyModelListMutableLiveData;
    }
    public MutableLiveData<List<CreatePekerjaanVO>> getCreatePekerjaan() {
            mCreatePekerjaan = mMyRepository.getCreatePekerjaan();
            return mCreatePekerjaan;
    }

    public MutableLiveData<List<PekerjaanModel>> getPekerjaanById(String id) {
        return mMyRepository.findOnePekerjaan(id);
    }
    public LiveData<RekapitulasiVO> getRekapitulasi() {
        mRekapitulasi = mMyRepository.getRekapitulasi();
        return mRekapitulasi;
    }

    public LiveData<String> insert(AddPekerjaanVO addPekerjaanVO) {
        return mMyRepository.insertPekerjaan(addPekerjaanVO);
    }
    public LiveData<String> update(AddPekerjaanVO addPekerjaanVO) {
        return mMyRepository.updatePekerjaan(addPekerjaanVO);
    }
    public LiveData<String> setStatus(String id, String status) {
        return mMyRepository.setStatus(id, status);
    }
}
