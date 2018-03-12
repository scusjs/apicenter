package me.liexing.apicenter.general.entity;

public class RestfulResult {
    private int code;
    private ResultData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultData getData() {
        return data;
    }

    public void setData(ResultData data) {
        this.data = data;
    }
}
