package com.hbw.springbootapplication.entity.scan;

import com.hbw.springbootapplication.entity.Receipt;

import java.io.Serializable;

public class ScanResult implements Serializable{

    //错误/正确码
    private Integer code;

    //错误/正确信息
    private String msg;

    //扫描返回发票
    private Receipt receipt;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
