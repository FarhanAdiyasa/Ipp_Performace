package com.polytechnic.astra.ac.id.ippperformance.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.JsonObject;
import com.polytechnic.astra.ac.id.ippperformance.API.ApiUtils;
import com.polytechnic.astra.ac.id.ippperformance.API.Service.MyService;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.AddDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.AddPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.ChartVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.CreateDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.CreatePekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DashboardDataVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DashboardResultVo;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DetailInPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.PekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.PekerjaanViewVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.RekapitulasiVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.RekapitulasiViewVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.ViewDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.Model.DetailPekerjaan;
import com.polytechnic.astra.ac.id.ippperformance.Model.KategoriModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.IntervalModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type My repository.
 */
public class MyRepository {
    private static final String ACCEPT = "application/json";
    private static final String TAG = "MyRepository";
    private static final String COOKIE =  "_ga=GA1.1.1986342977.1697444060; Idea-12005434=c8493f0d-95bb-49c0-82c3-c0bf2d00e2d8; cookie=%7B%22kry_id%22%3A%222%22%2C%22apk_id%22%3A%222%22%2C%22rol_id%22%3A%222%22%7D; ci_session=910p3784lbc9rhreucnjki23kjsgc0sr";
    private static MyRepository INSTANCE;
    private final MyService mMyService;
    private String mCookie;
    private boolean mFirstAttempt =true;
    private String mSession;// Variable to store dynamic cookie

    private MyRepository(Context context){
        mMyService = ApiUtils.getMyService();
    }

    /**
     * Initialize.
     *
     * @param context the context
     */
    public static synchronized void initialize(Context context){
        if (INSTANCE == null){
            INSTANCE = new MyRepository(context.getApplicationContext());
        }
    }

    /**
     * Get my repository.
     *
     * @return the my repository
     */
    public static MyRepository get(){
        return INSTANCE;
    }

    /**
     * Sets cookie.
     *
     * @param cookie the cookie
     */
// Method to set the dynamic cookie obtained from login
    public void setCookie(String cookie) {
        mCookie = cookie;
    }

    /**
     * Sets first attempt.
     *
     * @param attempt the attempt
     */
    public void setFirstAttempt(boolean attempt) {
        mFirstAttempt = attempt;
    }

    /**
     * Sets session.
     *
     * @param cookie the cookie
     */
    public void setSession(String cookie) {
        mSession = cookie;
    }

    /**
     * Get kategori by id kategori model.
     *
     * @param ktg the ktg
     * @param id  the id
     * @return the kategori model
     */
    public KategoriModel getKategoriById(List<KategoriModel> ktg, String id){
        for (KategoriModel ktgr : ktg) {
            if(ktgr.getKtg_id().equals(id)){
                return ktgr;
            }

        }
        return null;
    }

    /**
     * Get interval by id interval model.
     *
     * @param ktg the ktg
     * @param id  the id
     * @return the interval model
     */
    public IntervalModel getIntervalById(List<IntervalModel> ktg, String id){
        for (IntervalModel ktgr : ktg) {
            if(ktgr.getItv_id().equals(id)){
                return ktgr;
            }

        }
        return null;
    }

    /**
     * Get pekerjaan by id pekerjaan model.
     *
     * @param ktg the ktg
     * @param id  the id
     * @return the pekerjaan model
     */
    public PekerjaanModel getPekerjaanById(List<PekerjaanModel> ktg, String id){
        for (PekerjaanModel ktgr : ktg) {
            if(ktgr.getPkjId().equals(id)){
                return ktgr;
            }

        }
        return null;
    }


    /**
     * Gets my list prodi.
     *
     * @return the my list prodi
     */
    public MutableLiveData<List<PekerjaanViewVO>> getMyListProdi() {
        MutableLiveData<List<PekerjaanViewVO>> data = new MutableLiveData<>();
        Call<List<PekerjaanVO>> call = mMyService.getPekerjaanList2(mCookie, ACCEPT);
        call.enqueue(new Callback<List<PekerjaanVO>>() {
            @Override
            public void onResponse(Call<List<PekerjaanVO>> call, Response<List<PekerjaanVO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PekerjaanViewVO> viewVOList = new ArrayList<>();
                    for (PekerjaanVO pekerjaanVO : response.body()) {
                        for (PekerjaanModel pekerjaan : pekerjaanVO.getPekerjaan()) {
                            PekerjaanViewVO view = new PekerjaanViewVO();
                            KategoriModel kategori = getKategoriById(pekerjaanVO.getKategori(),pekerjaan.getKtgId());
                            IntervalModel interval = getIntervalById(pekerjaanVO.getInterval(), pekerjaan.getItvId());
                            view.setPkjId(pekerjaan.getPkjId());
                            view.setPkjNama(pekerjaan.getPkjNama());
                            view.setPkjKeterangan(pekerjaan.getPkjKeterangan());
                            view.setktgNama(kategori.getKtg_nama());
                            view.setitvSemester(interval.getItv_semester());
                            view.setPkjStatus(pekerjaan.getPkjStatus());
                            view.setPkjCreaby(pekerjaan.getPkjCreaby());
                            view.setPkjCreadate(pekerjaan.getPkjCreadate());
                            view.setPkjModiby(pekerjaan.getPkjModiby());
                            view.setTahunAjaran(interval.getItv_tahun_ajaran());
                            view.setPkjModidate(pekerjaan.getPkjModidate());
                            viewVOList.add(view);
                            Log.e(TAG,"Hasil" + mCookie + "CALLED : " + call.request().url().toString());
                        }
                        data.setValue(viewVOList);
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
                }
            }


            @Override
            public void onFailure(Call<List<PekerjaanVO>> call, Throwable t) {
                Log.e(TAG, "Error API call: " + t.getMessage() + "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
            }
        });
        return data;
    }

    /**
     * Gets create pekerjaan.
     *
     * @return the create pekerjaan
     */
    public MutableLiveData<List<CreatePekerjaanVO>> getCreatePekerjaan() {
        MutableLiveData<List<CreatePekerjaanVO>> data = new MutableLiveData<>();
        Call<List<CreatePekerjaanVO>> call = mMyService.getPekerjaanCreate(mCookie, ACCEPT);
        call.enqueue(new Callback<List<CreatePekerjaanVO>>() {
            @Override
            public void onResponse(Call<List<CreatePekerjaanVO>> call, Response<List<CreatePekerjaanVO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());

                } else {
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
                }
            }

            @Override
            public void onFailure(Call<List<CreatePekerjaanVO>> call, Throwable t) {
                Log.e(TAG, "Error API call123: " + t.getMessage());
            }
        });

        return data;
    }

    /**
     * Insert pekerjaan live data.
     *
     * @param addPekerjaanVO the add pekerjaan vo
     * @return the live data
     */
    public LiveData<String> insertPekerjaan(AddPekerjaanVO addPekerjaanVO) {
        MutableLiveData<String> msg = new MutableLiveData<>();

        Call<JsonObject> call = mMyService.tambahPekerjaan(mCookie, ACCEPT, addPekerjaanVO);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("status") && jsonObject.has("message")) {
                        String status = jsonObject.get("status").getAsString();
                        String message = jsonObject.get("message").getAsString();
                        msg.setValue(status + ": " + message);
                    } else {
                        msg.setValue("Response does not contain expected fields.");
                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
            }
        });

        return msg;
    }

    /**
     * Update pekerjaan live data.
     *
     * @param addPekerjaanVO the add pekerjaan vo
     * @return the live data
     */
    public LiveData<String> updatePekerjaan(AddPekerjaanVO addPekerjaanVO) {
        MutableLiveData<String> msg = new MutableLiveData<>();
        Log.d("UpdatePekerjaan", "AddPekerjaanVO content: " + addPekerjaanVO.toString());

        Call<JsonObject> call = mMyService.updatePekerjaan(mCookie, ACCEPT, addPekerjaanVO);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("status") && jsonObject.has("message")) {
                        String status = jsonObject.get("status").getAsString();
                        String message = jsonObject.get("message").getAsString();
                        msg.setValue(status + ": " + message);
                    } else {
                        msg.setValue("Response does not contain expected fields.");
                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
            }
        });

        return msg;
    }

    /**
     * Sets status.
     *
     * @param id     the id
     * @param status the status
     * @return the status
     */
    public LiveData<String> setStatus(String id, String status) {
        MutableLiveData<String> msg = new MutableLiveData<>();

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("pkj_id", id);
        requestBody.addProperty("status", (status.equals("1") ? "2" : "1"));
        Call<JsonObject> call = mMyService.updateStatus(mCookie, ACCEPT, requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("status") && jsonObject.has("message")) {
                        String status = jsonObject.get("status").getAsString();
                        String message = jsonObject.get("message").getAsString();
                        msg.setValue(status + ": " + message);
                    } else {
                        msg.setValue("Response does not contain expected fields.");
                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
            }
        });

        return msg;
    }

    /**
     * Gets detail pekerjaan.
     *
     * @return the detail pekerjaan
     */
    public MutableLiveData<List<ViewDetailPekerjaanVO>> getDetailPekerjaan() {
        MutableLiveData<List<ViewDetailPekerjaanVO>> data = new MutableLiveData<>();
        Call<List<DetailPekerjaanVO>> call = mMyService.getDetailPekerjaan(mCookie, ACCEPT);
        Log.e(TAG,"test2" + mCookie);
        call.enqueue(new Callback<List<DetailPekerjaanVO>>() {
            @Override
            public void onResponse(Call<List<DetailPekerjaanVO>> call, Response<List<DetailPekerjaanVO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<ViewDetailPekerjaanVO> viewVOList = new ArrayList<>();
                    for (DetailPekerjaanVO pekerjaanVO : response.body()) {
                        Log.e(TAG,"Hasil"  + pekerjaanVO.getDetail_pekerjaan().size());
                        for (DetailPekerjaan pekerjaan : pekerjaanVO.getDetail_pekerjaan()) {
                            PekerjaanModel pekerjaannya = getPekerjaanById(pekerjaanVO.getPekerjaan(), pekerjaan.getPkjId());
                            KategoriModel kategorinya = getKategoriById(pekerjaanVO.getKategori(), pekerjaannya.getKtgId());
                            IntervalModel intervalnya = getIntervalById(pekerjaanVO.getInterval(), pekerjaannya.getItvId());

                            ViewDetailPekerjaanVO view = new ViewDetailPekerjaanVO();
                            view.setDtpId(pekerjaan.getDtpId());
                            view.setPkjNama(pekerjaannya.getPkjNama());
                            view.setDtpNama(pekerjaan.getDtpNama());
                            view.setKtgNama(kategorinya.getKtg_nama());
                            view.setItvSemester(intervalnya.getItv_semester());
                            view.setItvTahunAjaran(intervalnya.getItv_tahun_ajaran());
                            view.setDtpStatus(pekerjaan.getDtpStatus().equals("1") ? "Progres" : pekerjaan.getDtpStatus().equals("0") ? "Draft" : "Selesai");
                            viewVOList.add(view);
                        }
                        Log.e(TAG,"Hasil" + mCookie + "CALLED" + call.request().url().toString());
                        data.setValue(viewVOList);
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
                }
            }

            @Override
            public void onFailure(Call<List<DetailPekerjaanVO>> call, Throwable t) {
                Log.e(TAG, "Error API call: " + t.getMessage() + "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
            }
        });

        return data;
    }

    /**
     * Gets create detail.
     *
     * @return the create detail
     */
    public MutableLiveData<List<CreateDetailPekerjaanVO>> getCreateDetail() {
        MutableLiveData<List<CreateDetailPekerjaanVO>> data = new MutableLiveData<>();
        Call<List<CreateDetailPekerjaanVO>> call = mMyService.getDetailPekerjaanCreate(mCookie, ACCEPT);
        Log.e(TAG,"getDetailPekerjaanCreate" + mCookie);
        call.enqueue(new Callback<List<CreateDetailPekerjaanVO>>() {
            @Override
            public void onResponse(Call<List<CreateDetailPekerjaanVO>> call, Response<List<CreateDetailPekerjaanVO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
                }
            }

            @Override
            public void onFailure(Call<List<CreateDetailPekerjaanVO>> call, Throwable t) {
                Log.e(TAG, "Error API call: " + t.getMessage() + "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
            }
        });

        return data;
    }

    /**
     * Gets pkj by kategori.
     *
     * @param ktg_id the ktg id
     * @return the pkj by kategori
     */
    public MutableLiveData<List<PekerjaanModel>> getPkjByKategori(String ktg_id) {
        MutableLiveData<List<PekerjaanModel>> data = new MutableLiveData<>();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ktg_id", ktg_id);
        Call<List<PekerjaanModel>> call = mMyService.getPkjByKategori(mCookie, ACCEPT, jsonObject);
        Log.e(TAG,"getDetailPekerjaanCreate" + ktg_id);
        call.enqueue(new Callback<List<PekerjaanModel>>() {
            @Override
            public void onResponse(Call<List<PekerjaanModel>> call, Response<List<PekerjaanModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG,"getDetailPekerjaanCreate" + call.request().url() + "as" + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
                }
            }

            @Override
            public void onFailure(Call<List<PekerjaanModel>> call, Throwable t) {
                Log.e(TAG, "Error API call: " + t.getMessage() + "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
            }
        });

        return data;
    }

    /**
     * Insert detail live data.
     *
     * @param addDetailPekerjaanVO the add detail pekerjaan vo
     * @param photo                the photo
     * @return the live data
     */
    public LiveData<String> insertDetail(AddDetailPekerjaanVO addDetailPekerjaanVO, String photo) {
        MutableLiveData<String> msg = new MutableLiveData<>();
        addDetailPekerjaanVO.setDtp_bukti_foto(photo);
        Log.d(TAG ,photo);
        Call<JsonObject> call = mMyService.tambahDetailPekerjaan(mCookie, ACCEPT, addDetailPekerjaanVO);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("status") && jsonObject.has("message")) {
                        String status = jsonObject.get("status").getAsString();
                        String message = jsonObject.get("message").getAsString();
                        msg.setValue(status + ": " + message);
                    } else {
                        msg.setValue("Response does not contain expected fields.");
                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
                // You can log the failure or handle it as needed
            }
        });

        return msg;
    }

    /**
     * Post detail live data.
     *
     * @param id the id
     * @return the live data
     */
    public LiveData<String> postDetail(String id) {
        MutableLiveData<String> msg = new MutableLiveData<>();

        Call<JsonObject> call = mMyService.postDetail(mCookie, ACCEPT, id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("status") && jsonObject.has("message")) {
                        String status = jsonObject.get("status").getAsString();
                        String message = jsonObject.get("message").getAsString();
                        msg.setValue(status + ": " + message);
                    } else {
                        msg.setValue("Response does not contain expected fields.");
                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
                // You can log the failure or handle it as needed
            }
        });

        return msg;
    }

    /**
     * Hapus detail live data.
     *
     * @param id the id
     * @return the live data
     */
    public LiveData<String> hapusDetail(String id) {
        MutableLiveData<String> msg = new MutableLiveData<>();

        Call<JsonObject> call = mMyService.hapusDetail(mCookie, ACCEPT, id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("status") && jsonObject.has("message")) {
                        String status = jsonObject.get("status").getAsString();
                        String message = jsonObject.get("message").getAsString();
                        msg.setValue(status + ": " + message);
                    } else {
                        msg.setValue("Response does not contain expected fields.");
                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
                // You can log the failure or handle it as needed
            }
        });

        return msg;
    }

    /**
     * Selesaikan detail live data.
     *
     * @param id the id
     * @return the live data
     */
    public LiveData<String> selesaikanDetail(String id) {
        MutableLiveData<String> msg = new MutableLiveData<>();

        Call<JsonObject> call = mMyService.selesaiDetail(mCookie, ACCEPT, id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("status") && jsonObject.has("message")) {
                        String status = jsonObject.get("status").getAsString();
                        String message = jsonObject.get("message").getAsString();
                        msg.setValue(status + ": " + message);
                    } else {
                        msg.setValue("Response does not contain expected fields.");
                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
                // You can log the failure or handle it as needed
            }
        });

        return msg;
    }

    /**
     * Gets detail.
     *
     * @param id the id
     * @return the detail
     */
    public MutableLiveData<ViewDetailPekerjaanVO> getDetail(String id) {
        MutableLiveData<ViewDetailPekerjaanVO> data = new MutableLiveData<>();
        Call<ViewDetailPekerjaanVO> call = mMyService.detailDetail(mCookie, ACCEPT, id);

        call.enqueue(new Callback<ViewDetailPekerjaanVO>() {
            @Override
            public void onResponse(Call<ViewDetailPekerjaanVO> call, Response<ViewDetailPekerjaanVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ViewDetailPekerjaanVO viewVOList = new ViewDetailPekerjaanVO(response.body());
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie + viewVOList);
                    data.setValue(viewVOList);
                } else {
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
                }
            }

            @Override
            public void onFailure(Call<ViewDetailPekerjaanVO> call, Throwable t) {
                Log.e(TAG, "Error API call: " + t.getMessage() + "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
            }
        });

        return data;
    }

    /**
     * Gets detail by pkj id.
     *
     * @param id the id
     * @return the detail by pkj id
     */
    public MutableLiveData<List<ViewDetailPekerjaanVO>> getDetailByPkjId(String id) {
        MutableLiveData<List<ViewDetailPekerjaanVO>> data = new MutableLiveData<>();
        Call<List<DetailInPekerjaanVO>> call = mMyService.detailPekerjaan(mCookie, ACCEPT, id);
        Log.e(TAG,"test2" + call.request().url());
        call.enqueue(new Callback<List<DetailInPekerjaanVO>>() {
            @Override
            public void onResponse(Call<List<DetailInPekerjaanVO>> call, Response<List<DetailInPekerjaanVO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ViewDetailPekerjaanVO> viewVOList = new ArrayList<>();
                    for (DetailInPekerjaanVO pekerjaanVO : response.body()) {
                        for (DetailPekerjaan pekerjaan : pekerjaanVO.getDetail_pekerjaan()) {
                            KategoriModel kategorinya = getKategoriById(pekerjaanVO.getKategori(), pekerjaanVO.getPekerjaan().getKtgId());
                            IntervalModel intervalnya = getIntervalById(pekerjaanVO.getInterval(), pekerjaanVO.getPekerjaan().getItvId());

                            ViewDetailPekerjaanVO view = new ViewDetailPekerjaanVO();
                            view.setDtpId(pekerjaan.getDtpId());
                            view.setPkjNama(pekerjaanVO.getPekerjaan().getPkjNama());
                            view.setDtpNama(pekerjaan.getDtpNama());
                            view.setKtgNama(kategorinya.getKtg_nama());
                            view.setItvSemester(intervalnya.getItv_semester());
                            view.setItvTahunAjaran(intervalnya.getItv_tahun_ajaran());
                            view.setDtpStatus(pekerjaan.getDtpStatus().equals("1") ? "Progres" : pekerjaan.getDtpStatus().equals("0") ? "Draft" : "Selesai");
                            viewVOList.add(view);

                        }
                        Log.e(TAG,"Hasil" + mCookie + "CALLED" + call.request().url().toString());
                        data.setValue(viewVOList);
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
                }
            }

            @Override
            public void onFailure(Call<List<DetailInPekerjaanVO>> call, Throwable t) {
                Log.e(TAG, "Error API call: " + t.getMessage() + "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie + "INI ID " + id);
            }
        });

        return data;
    }

    /**
     * Find one pekerjaan mutable live data.
     *
     * @param id the id
     * @return the mutable live data
     */
    public MutableLiveData<List<PekerjaanModel>> findOnePekerjaan(String id) {
        MutableLiveData<List<PekerjaanModel>> data = new MutableLiveData<>();
        Call<List<PekerjaanModel>> call = mMyService.findOnePekerjaan(mCookie, ACCEPT, id);

        call.enqueue(new Callback<List<PekerjaanModel>>() {
            @Override
            public void onResponse(Call<List<PekerjaanModel>> call, Response<List<PekerjaanModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "Response is not successful or body is null" +  "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
                }
            }

            @Override
            public void onFailure(Call<List<PekerjaanModel>> call, Throwable t) {
                Log.e(TAG, "Error API call: " + t.getMessage() + "CALLED : " + call.request().url().toString() + "COOKIE : " + mCookie);
            }
        });

        return data;
    }

    /**
     * Login live data.
     *
     * @param username the username
     * @param password the password
     * @return the live data
     */
    public LiveData<String> login(String username, String password) {
        MutableLiveData<String> msg = new MutableLiveData<>();
        JsonObject loginDetails = new JsonObject();
        loginDetails.addProperty("username", username);
        loginDetails.addProperty("password", password);
        Log.e(TAG, "ISI MCOOKIE : "+mCookie);

        Call<JsonObject> call = mMyService.login(ACCEPT, loginDetails);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("cookie")) {
                        String cookie = jsonObject.get("cookie").getAsString();
                        String token = jsonObject.get("token").getAsString();
                        List<Cookie> cookies = ApiUtils.getCookiesForUrl(call.request().url().toString());
                        String ci_session = "";
                        for (Cookie cooki3e : cookies) {
                            Log.e(TAG, cooki3e.name() + "=" + cooki3e.value() + ";");
                            if (cooki3e.name().equals("ci_session")) {
                                ci_session = cooki3e.name() + "=" + cooki3e.value() + ";";
                            }
                        }
                        setSession(ci_session);



                        redirectLogin(token, cookie).observeForever(new Observer<String>() {
                            @Override
                            public void onChanged(String result) {
                                msg.setValue(result);
                            }
                        });
                    } else {
                        if (jsonObject != null && jsonObject.has("status")){
                            String status = jsonObject.get("status").getAsString();
                            if(status.equals("salah")){
                                msg.setValue("salah");
                            }else{
                                msg.setValue("Wrong");
                            }
                        }

                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
            }
        });

        return msg;
    }
    // Method to get cookie from redirect API call
    private LiveData<String> redirectLogin(String token, String cookie) {
        MutableLiveData<String> msg = new MutableLiveData<>();

        Call<JsonObject> call = mMyService.redirect(ACCEPT, token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();

                    if (jsonObject != null && jsonObject.has("cookie")) {
                        String cookier = jsonObject.get("cookie").getAsString();
                        setCookie(cookier);

                        setSession(mSession + cookier);
                        msg.setValue("Login Successful!");
                    } else {
                        msg.setValue("Response body tidak lengkap!");
                    }
                } else {
                    msg.setValue("Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                msg.setValue("Failure: " + mCookie + "CALL : " + call.request().url().toString());
                Log.d("API_CALL", "Failure: " + mCookie + "CALL : " + call.request().url().toString());
            }
        });

        return msg;
    }

    /**
     * Gets dashboard data.
     *
     * @return the dashboard data
     */
    public LiveData<DashboardDataVO> getDashboardData() {
        MutableLiveData<DashboardDataVO> data = new MutableLiveData<>();

        if (mFirstAttempt) {
            Log.d(TAG, "First attempt, setting cookie...");
            Call<JsonObject> callC = mMyService.setSession(mCookie, ACCEPT);
            Log.d(TAG, "ini cookie: " + mCookie);
            callC.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> callC, Response<JsonObject> response) {
                    String ci_session = "";
                    List<Cookie> cookies = ApiUtils.getCookiesForUrl(callC.request().url().toString());
                    for (Cookie cookie : cookies) {
                        if (cookie.name().equals("ci_session")) {
                            ci_session = cookie.name() + "=" + cookie.value() + ";";
                        }
                    }
                    setCookie(mCookie + " " + ci_session);
                    Log.e(TAG, "INI COOKIE" + mCookie);

                    // Make the second call after setting the cookie
                    fetchDashboardData(data);
                }

                @Override
                public void onFailure(Call<JsonObject> callC, Throwable t) {
                    Log.d("API_CALL", "Failure: " + mCookie + " CALL: " + t.getMessage());
                }
            });
        } else {
            fetchDashboardData(data);
        }

        return data;
    }

    private void fetchDashboardData(MutableLiveData<DashboardDataVO> data) {
        Log.e(TAG, "Nah, its fine");
        Call<DashboardDataVO> call = mMyService.getPercentagePengajaran(mCookie, ACCEPT);
        call.enqueue(new Callback<DashboardDataVO>() {
            @Override
            public void onResponse(Call<DashboardDataVO> call, Response<DashboardDataVO> response) {
                Log.e(TAG, "Nah, its fine2");
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        if (mFirstAttempt) {
                            setFirstAttempt(false);
                        }

                        // Ensure LiveData is updated on the main thread
                        data.postValue(response.body());
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<DashboardDataVO> call, Throwable t) {
                Log.e("API_CALL", t.getMessage() + " CALLED : " + call.request().url() + " COOKIE : " + mCookie);
            }
        });
    }

    /**
     * Gets rekapitulasi.
     *
     * @return the rekapitulasi
     */
    public LiveData<RekapitulasiVO> getRekapitulasi() {
        MutableLiveData<RekapitulasiVO> data = new MutableLiveData<>();
        Call<RekapitulasiVO> call = mMyService.getRekapitulasi(mCookie, ACCEPT);

        call.enqueue(new Callback<RekapitulasiVO>() {
            @Override
            public void onResponse(Call<RekapitulasiVO> call, Response<RekapitulasiVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {

                        data.setValue(response.body());

                        Log.d(TAG, "Rekapitulasi Data : " + response.body().getTitle());
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<RekapitulasiVO> call, Throwable t) {
                Log.e("API_CALL", t.getMessage());
            }
        });

        return data;
    }

    /**
     * Logout live data.
     *
     * @return the live data
     */
    public LiveData<String> logout() {
        Call<ResponseBody> call = mMyService.logout(mCookie, ACCEPT);
        MutableLiveData<String> msg = new MutableLiveData<>();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Handle JSON response if needed
                        String jsonResponse = response.body().string();
                        ApiUtils.clearCookies();
                        msg.setValue(jsonResponse);
                        setCookie(null);
                        setFirstAttempt(true);
                        ApiUtils.getClient(ApiUtils.API_URL);
                    } catch (IOException e) {
                        e.printStackTrace();
                        msg.setValue("Error");
                    }
                } else {
                    msg.setValue("Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                msg.setValue("Failure");
                Log.e("API_CALL", t.getMessage());
            }
        });
        return msg;
    }

    /**
     * Gets kry foto.
     *
     * @return the kry foto
     */
    public LiveData<UserModel> getKryFoto() {
        MutableLiveData<UserModel> msg = new MutableLiveData<>();

        Call<UserModel> call = mMyService.getKryFoto(mCookie, ACCEPT);
        Log.d(TAG, "MASUK WOI");
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "GREATE");
                    msg.setValue(response.body());
                } else {
                    // Log the raw response body for debugging
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Log.d(TAG, "Error Body: " + errorBody);
                        } else {
                            Log.d(TAG, "Error Body is null");
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("API_CALL", "Failure: " + mCookie + " CALL : " + call.request().url().toString() + " Error: " + t.getMessage());
            }
        });

        return msg;
    }



}
