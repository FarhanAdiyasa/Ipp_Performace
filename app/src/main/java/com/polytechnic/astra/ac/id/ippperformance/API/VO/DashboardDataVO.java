package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.polytechnic.astra.ac.id.ippperformance.Model.UserModel;

import java.util.List;

public class DashboardDataVO {
    @SerializedName("results3")
    @Expose
    private DashboardResultVo dashboardResultVo;
    @SerializedName("results")
    @Expose

    private List<ChartVO> results;
    @SerializedName("results2")
    @Expose
    private List<ChartVO> results2;

    @SerializedName("user")
    @Expose
    private UserModel mUserModel;

    public DashboardDataVO() {
    }

    public DashboardDataVO(DashboardResultVo dashboardResultVo, List<ChartVO> results, List<ChartVO> results2, UserModel userModel) {
        this.dashboardResultVo = dashboardResultVo;
        this.results = results;
        this.results2 = results2;
        this.mUserModel = userModel;
    }

    public UserModel getUser() {
        return mUserModel;
    }

    public void setUser(UserModel userModel) {
        this.mUserModel = userModel;
    }

    // Getters and Setters
    public DashboardResultVo getDashboardResultVo() {
        return dashboardResultVo;
    }

    public void setDashboardResultVo(DashboardResultVo dashboardResultVo) {
        this.dashboardResultVo = dashboardResultVo;
    }

    public List<ChartVO> getResults() {
        return results;
    }

    public void setResults(List<ChartVO> results) {
        this.results = results;
    }

    public List<ChartVO> getResults2() {
        return results2;
    }

    public void setResults2(List<ChartVO> results2) {
        this.results2 = results2;
    }
}
