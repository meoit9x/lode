package com.hta.loto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StattisticResponse extends BaseResponse {

    private Integer status;
    @SerializedName("data")
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("id")
        private Integer id;
        @SerializedName("ngay")
        private Integer ngay;
        @SerializedName("thang")
        private Integer thang;
        @SerializedName("nam")
        private Integer nam;
        @SerializedName("type")
        private Integer type;
        @SerializedName("value")
        private String value;
        @SerializedName("result")
        private String result;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getNgay() {
            return ngay;
        }

        public void setNgay(Integer ngay) {
            this.ngay = ngay;
        }

        public Integer getThang() {
            return thang;
        }

        public void setThang(Integer thang) {
            this.thang = thang;
        }

        public Integer getNam() {
            return nam;
        }

        public void setNam(Integer nam) {
            this.nam = nam;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
