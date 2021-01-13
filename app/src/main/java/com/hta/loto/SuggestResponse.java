package com.hta.loto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuggestResponse extends BaseResponse {

    @SerializedName("data")
    private List<String> data = null;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}
