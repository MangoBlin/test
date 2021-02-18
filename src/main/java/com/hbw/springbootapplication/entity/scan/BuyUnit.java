package com.hbw.springbootapplication.entity.scan;

import java.io.Serializable;

public class BuyUnit implements Serializable{
    private String BuyerName;
    private String TaxpayersNum;
    private String AddAndTel;
    private String accountName;

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getTaxpayersNum() {
        return TaxpayersNum;
    }

    public void setTaxpayersNum(String taxpayersNum) {
        TaxpayersNum = taxpayersNum;
    }

    public String getAddAndTel() {
        return AddAndTel;
    }

    public void setAddAndTel(String addAndTel) {
        AddAndTel = addAndTel;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
