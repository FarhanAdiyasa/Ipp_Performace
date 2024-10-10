package com.polytechnic.astra.ac.id.ippperformance.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.ippperformance.API.Repository.MyRepository;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.AddDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.CreateDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.ViewDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;

import java.util.List;

public class DetailPekerjaanListViewModel extends ViewModel {
    private MutableLiveData<List<ViewDetailPekerjaanVO>> mMyModelListMutableLiveData;
    private MutableLiveData<List<CreateDetailPekerjaanVO>> mCreateDetail;
    private MutableLiveData<ViewDetailPekerjaanVO> mDetailPekerjaanVO;
    private MutableLiveData<List<ViewDetailPekerjaanVO>> mVoDetailPekerjaanInPekerjaan;
    private MyRepository mMyRepository;

    public DetailPekerjaanListViewModel() {
        mMyRepository = MyRepository.get();
    }
    public MutableLiveData<ViewDetailPekerjaanVO> getDetailDetail(String id) {
        mDetailPekerjaanVO = mMyRepository.getDetail(id);
        return mDetailPekerjaanVO;
    }
    public MutableLiveData<List<ViewDetailPekerjaanVO>> getDetailByPkjId(String pkjId) {
        mVoDetailPekerjaanInPekerjaan = mMyRepository.getDetailByPkjId(pkjId);
        return mVoDetailPekerjaanInPekerjaan;
    }

    public MutableLiveData<List<ViewDetailPekerjaanVO>> getListModel() {
        mMyModelListMutableLiveData = mMyRepository.getDetailPekerjaan();
        return mMyModelListMutableLiveData;
    }

    public MutableLiveData<List<CreateDetailPekerjaanVO>> getCreateDetail() {
        mCreateDetail = mMyRepository.getCreateDetail();
        return mCreateDetail;
    }
    public LiveData<String> insert(AddDetailPekerjaanVO addDetailPekerjaanVO, String photo) {
        return mMyRepository.insertDetail(addDetailPekerjaanVO, photo);
    }
    public LiveData<List<PekerjaanModel>> getPkjByKategori(String ktg_id) {
        return mMyRepository.getPkjByKategori(ktg_id);
    }

    public LiveData<String> post(String id) {
        return mMyRepository.postDetail(id);
    }
    public LiveData<String> hapus(String id) {
        return mMyRepository.hapusDetail(id);
    }
    public LiveData<String> selesai(String id) {
        return mMyRepository.selesaikanDetail(id);
    }


}
