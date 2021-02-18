package com.hbw.springbootapplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptExport implements Serializable,Comparable<ReceiptExport>{

    //序号
    private Integer serialNumber;

    //是否认证(0:未认证,1:已认证)
    private String  isCertification;

    //认证时间
    private String certificationTime;

    //凭证号
    private String voucherNum;

    //销售方单位名称
    private String sellerUnitName;

    //发票代码
    private String receiptCode;

    //发票号码
    private String receiptNum;

    //开票日期
    private String receiptTime;

    //发票内容
    private String ExportReceiptContent;

//    //发票类别
//    private String receiptCategory;
//
//    //开票内容
//    private String receiptContent;

    //价税合计
    private String leviedTotal;

    //金额
    private String money;

    //税率
    private String rate;

    //税额
    private String tax;

    //专票/普票(0:专票,1:普票)
    private String receiptType;
    //固定资产
    private String fixedAssets;

    //资产分类
    private String assertClassDir;

    //报销人员
    private String submitAccountStaff;
    //公司/项目
    private String compProj;
    //部门
    private String department;
    //备注
    private String remarks;

    //录入时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String entryTime;

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public String getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum;
    }

    public String getSellerUnitName() {
        return sellerUnitName;
    }

    public void setSellerUnitName(String sellerUnitName) {
        this.sellerUnitName = sellerUnitName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSubmitAccountStaff() {
        return submitAccountStaff;
    }

    public void setSubmitAccountStaff(String submitAccountStaff) {
        this.submitAccountStaff = submitAccountStaff;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCompProj() {
        return compProj;
    }

    public void setCompProj(String compProj) {
        this.compProj = compProj;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(String isCertification) {
        this.isCertification = isCertification;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getLeviedTotal() {
        return leviedTotal;
    }

    public void setLeviedTotal(String leviedTotal) {
        this.leviedTotal = leviedTotal;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getVoucherNum() {
        return voucherNum;
    }

    public void setVoucherNum(String voucherNum) {
        this.voucherNum = voucherNum;
    }

    public String getExportReceiptContent() {
        return ExportReceiptContent;
    }

    public void setExportReceiptContent(String exportReceiptContent) {
        ExportReceiptContent = exportReceiptContent;
    }

    public String getCertificationTime() {
        return certificationTime;
    }

    public void setCertificationTime(String certificationTime) {
        this.certificationTime = certificationTime;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public String getAssertClassDir() {
        return assertClassDir;
    }

    public void setAssertClassDir(String assertClassDir) {
        this.assertClassDir = assertClassDir;
    }

    @Override
    public int compareTo(ReceiptExport o) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        Date date1 = null;
        Date date2 = null;
        try {
            date1=simpleDateFormat.parse(this.getEntryTime());
            date2 = simpleDateFormat.parse(o.getEntryTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        int i = (int) (date1.getTime()-date2.getTime());
        if(i == 0){
            return 0;//如果年龄相等了再用分数进行排序
        }
        return i;
    }
}
