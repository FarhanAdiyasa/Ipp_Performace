package com.polytechnic.astra.ac.id.ippperformance;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.ippperformance.API.Repository.MyRepository;
import com.polytechnic.astra.ac.id.ippperformance.API.Service.MyService;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.PekerjaanVO;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.PekerjaanViewVO;
import com.polytechnic.astra.ac.id.ippperformance.Model.IntervalModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.KategoriModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;
import com.polytechnic.astra.ac.id.ippperformance.API.ApiUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MyRepositoryTest {

    @Mock
    Context mockContext;

    @Mock
    MyService mockMyService;
    private static final String ACCEPT = "application/json";
    private static final String COOKIE =  "cookie=%7B%22kry_id%22%3A%222%22%2C%22apk_id%22%3A%222%22%2C%22rol_id%22%3A%222%22%7D; ci_session=ghfqdlocgdu1esdfmajnqh582c1077dg;";

    @Mock
    Call<List<PekerjaanVO>> mockCall;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MyRepository myRepository;

    @Before
    public void setUp() throws Exception {
        MyRepository.initialize(mockContext);
        myRepository = MyRepository.get();
        setMyService(myRepository, mockMyService);
    }

    private void setMyService(MyRepository myRepository, MyService mockMyService) throws Exception {
        Field myServiceField = MyRepository.class.getDeclaredField("mMyService");
        myServiceField.setAccessible(true);
        myServiceField.set(myRepository, mockMyService);
    }

    @Test
    public void testGetMyListProdi_success() {
        // Prepare dummy data
        List<PekerjaanVO> pekerjaanVOList = new ArrayList<>();
        PekerjaanVO pekerjaanVO = new PekerjaanVO();
        List<PekerjaanModel> pekerjaanList = new ArrayList<>();
        PekerjaanModel pekerjaan = new PekerjaanModel();
        pekerjaan.setPkjId("1");
        pekerjaan.setKtgId("1");
        pekerjaan.setItvId("1");
        pekerjaanList.add(pekerjaan);
        pekerjaanVO.setPekerjaan(pekerjaanList);

        List<KategoriModel> kategoriList = new ArrayList<>();
        KategoriModel kategori = new KategoriModel();
        kategori.setKtg_id("1");
        kategori.setKtg_nama("Kategori 1");
        kategoriList.add(kategori);
        pekerjaanVO.setKategori(kategoriList);

        List<IntervalModel> intervalList = new ArrayList<>();
        IntervalModel interval = new IntervalModel();
        interval.setItv_id("1");
        interval.setItv_semester("Semester 1");
        intervalList.add(interval);
        pekerjaanVO.setInterval(intervalList);

        pekerjaanVOList.add(pekerjaanVO);

        Response<List<PekerjaanVO>> response = Response.success(pekerjaanVOList);

        // Mock API response
        when(mockMyService.getPekerjaanList2(COOKIE, ACCEPT)).thenReturn(mockCall);

        // Execute the method
        myRepository.setCookie(COOKIE);
        MutableLiveData<List<PekerjaanViewVO>> liveData = myRepository.getMyListProdi();

        // Capture the callback
        ArgumentCaptor<Callback<List<PekerjaanVO>>> captor = ArgumentCaptor.forClass(Callback.class);
        verify(mockCall).enqueue(captor.capture());
        captor.getValue().onResponse(mockCall, response);

        // Validate the result
        assertNotNull(liveData.getValue());
        assertEquals(1, liveData.getValue().size());
        PekerjaanViewVO result = liveData.getValue().get(0);

        assertEquals("1", result.getPkjId());
        assertEquals("Kategori 1", result.getktgNama());
        assertEquals("Semester 1", result.getitvSemester());
    }

    @Test
    public void testGetMyListProdi_failure() {
        // Mock API response
        when(mockMyService.getPekerjaanList2(COOKIE, ACCEPT)).thenReturn(mockCall);

        // Execute the method
        myRepository.setCookie(COOKIE);
        MutableLiveData<List<PekerjaanViewVO>> liveData = myRepository.getMyListProdi();

        // Capture the callback
        ArgumentCaptor<Callback<List<PekerjaanVO>>> captor = ArgumentCaptor.forClass(Callback.class);
        verify(mockCall).enqueue(captor.capture());
        captor.getValue().onFailure(mockCall, new Throwable("API call failed"));

        // Validate the result
        assertNotNull(liveData.getValue());
        assertEquals(0, liveData.getValue().size());
    }
}
