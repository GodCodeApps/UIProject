package com.pym.uiproject.app.message;

import com.google.gson.annotations.SerializedName;

/**
 * 这里是萌萌哒注释君
 *
 * @author Peng YanMing 2017/5/6
 */
public class Result {
    @SerializedName("error_code")
    private int error_code;
    @SerializedName("reason")
    private String reason;
    @SerializedName("result")
    private DataResutl  result;

    public int getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public DataResutl getResult() {
        return result;
    }
}
