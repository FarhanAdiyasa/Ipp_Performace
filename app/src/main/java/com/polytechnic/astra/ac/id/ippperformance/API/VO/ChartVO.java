package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChartVO {
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("value")
    @Expose
    private String value;

    public ChartVO() {
    }

    public ChartVO(String data, String value) {
        this.data = data;
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

