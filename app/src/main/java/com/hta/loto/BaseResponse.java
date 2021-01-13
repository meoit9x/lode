package com.hta.loto;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("status")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
