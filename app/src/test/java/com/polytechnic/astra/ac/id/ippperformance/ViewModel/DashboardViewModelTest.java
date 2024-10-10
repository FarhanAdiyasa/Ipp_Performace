package com.polytechnic.astra.ac.id.ippperformance.ViewModel;

import static org.junit.Assert.*;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.ippperformance.API.Repository.MyRepository;
import com.polytechnic.astra.ac.id.ippperformance.API.VO.DashboardDataVO;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MyRepository myRepository;

    private DashboardViewModel dashboardViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dashboardViewModel = new DashboardViewModel();
        dashboardViewModel.mMyRepository = myRepository; // Set the mock repository
    }

    @Test
    public void testGetDashboardData() {
        DashboardDataVO dummyDashboardData = new DashboardDataVO();
        MutableLiveData<DashboardDataVO> liveData = new MutableLiveData<>();
        liveData.setValue(dummyDashboardData);

        when(myRepository.getDashboardData()).thenReturn(liveData);

        LiveData<DashboardDataVO> result = dashboardViewModel.getDashboardData();
        assertEquals(dummyDashboardData, result.getValue());
    }

    @Test
    public void testGetKryFoto() {
        UserModel dummyUserModel = new UserModel();
        MutableLiveData<UserModel> liveData = new MutableLiveData<>();
        liveData.setValue(dummyUserModel);

        when(myRepository.getKryFoto()).thenReturn(liveData);

        LiveData<UserModel> result = dashboardViewModel.getKryFoto();
        assertEquals(dummyUserModel, result.getValue());
    }
}
