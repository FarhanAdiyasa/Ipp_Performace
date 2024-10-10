package com.polytechnic.astra.ac.id.ippperformance.API.Service;

import com.google.gson.JsonObject;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.AddDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.AddPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.CreateDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.CreatePekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DashboardDataVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DetailInPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.PekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.RekapitulasiVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.ViewDetailPekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyService {

    //LOGIN
    @POST("AlatBerat/auth")
    Call<JsonObject> login(
            @Header("Accept") String accept,
            @Body JsonObject loginAuth
    );
    @POST("AlatBerat/Redirect")
    Call<JsonObject> redirect(
            @Header("Accept") String accept,
            @Query("token") String token
    );
    @POST("AlatBerat/auth/logout")
    Call<ResponseBody> logout(@Header("Cookie") String token,
                              @Header("Accept") String accept);

    //Pekerjaan
    @GET("IPP/Dashboard/setAndroidSession")
    Call<JsonObject> setSession(@Header("Cookie") String token,
                                @Header("Accept") String accept);
    @GET("IPP/Dashboard/showDashboard")
    Call<DashboardDataVO> getPercentagePengajaran(@Header("Cookie") String token,
                                                  @Header("Accept") String accept);
    @GET("IPP/Pekerjaan")
    Call<List<PekerjaanVO>> getPekerjaanList2(@Header("Cookie") String token,
                                                  @Header("Accept") String accept);
    @GET("IPP/Pekerjaan/create")
    Call<List<CreatePekerjaanVO>> getPekerjaanCreate(@Header("Cookie") String token,
                                                     @Header("Accept") String accept);
    @POST("IPP/Pekerjaan/tambah")
    Call<JsonObject> tambahPekerjaan(@Header("Cookie") String token,
                                     @Header("Accept") String accept,
                                     @Body AddPekerjaanVO pekerjaanRequest);

    @GET("IPP/Dashboard/getUserLogged")
    Call<UserModel> getKryFoto(@Header("Cookie") String token,
                               @Header("Accept") String accept);

    @GET("IPP/Pekerjaan/edit/{id}")
    Call<List<PekerjaanModel>> findOnePekerjaan(
            @Header("Cookie") String token,
            @Header("Accept") String accept,
            @Path("id") String id
    );
    @POST("IPP/Pekerjaan/update")
    Call<JsonObject> updatePekerjaan(@Header("Cookie") String token,
                                     @Header("Accept") String accept,
                                     @Body AddPekerjaanVO pekerjaanRequest);

    @POST("IPP/Pekerjaan/updateStatus")
    Call<JsonObject> updateStatus(@Header("Cookie") String token,
                                     @Header("Accept") String accept,
                                     @Body JsonObject pekerjaanRequest);
    @GET("IPP/Pekerjaan/detail/{id}")
    Call<List<DetailInPekerjaanVO>> detailPekerjaan(
            @Header("Cookie") String token,
            @Header("Accept") String accept,
            @Path("id") String id
    );

    //DETAIL PEKERJAAN
    @GET("IPP/Detail_pekerjaan")
    Call<List<DetailPekerjaanVO>> getDetailPekerjaan(@Header("Cookie") String token,
                                                     @Header("Accept") String accept);

    @GET("IPP/Detail_pekerjaan/create")
    Call<List<CreateDetailPekerjaanVO>> getDetailPekerjaanCreate(@Header("Cookie") String token,
                                                                 @Header("Accept") String accept);
    @POST("IPP/Pekerjaan/pekerjaan_by_kategori")
    Call<List<PekerjaanModel>> getPkjByKategori(@Header("Cookie") String token,
                                                @Header("Accept") String accept,
                                                @Body JsonObject body);

    @POST("IPP/Detail_pekerjaan/tambah")
    Call<JsonObject> tambahDetailPekerjaan(@Header("Cookie") String token,
                                           @Header("Accept") String accept,
                                           @Body AddDetailPekerjaanVO detailPekerjaanRequest);

    @GET("IPP/Detail_pekerjaan/kirim/{id}")
    Call<JsonObject> postDetail(
            @Header("Cookie") String token,
            @Header("Accept") String accept,
            @Path("id") String id
    );
    @GET("IPP/Detail_pekerjaan/hapus/{id}")
    Call<JsonObject> hapusDetail(
            @Header("Cookie") String token,
            @Header("Accept") String accept,
            @Path("id") String id
    );
    @GET("IPP/Detail_pekerjaan/selesai/{id}")
    Call<JsonObject> selesaiDetail(
            @Header("Cookie") String token,
            @Header("Accept") String accept,
            @Path("id") String id
    );
    @GET("IPP/Detail_pekerjaan/detail/{id}")
    Call<ViewDetailPekerjaanVO> detailDetail(
            @Header("Cookie") String token,
            @Header("Accept") String accept,
            @Path("id") String id
    );
    @GET("IPP/Rekapitulasi")
    Call<RekapitulasiVO> getRekapitulasi(@Header("Cookie") String token,
                                                        @Header("Accept") String accept);
}
