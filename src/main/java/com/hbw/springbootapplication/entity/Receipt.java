package com.hbw.springbootapplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Receipt implements Serializable{

    //id
    private Integer id;
    //序号
    private Integer serialNumber;

    //发票代码
    private String receiptCode;

    //发票号码
    private String receiptNum;

    //销售方单位名称
    private String sellerUnitName;

    //开票日期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date receiptTime;

    //发票类别
    private String receiptCategory;

    //开票内容
    private String receiptContent;

    //金额
    private String money;

    //税率
    private String rate;

    //税额
    private String tax;

    //报销人员
    private String submitAccountStaff;

    //录入时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date entryTime;

    //备注
    private String remarks;

    //公司/项目
    private String compProj;

    //部门
    private String department;

    //是否认证(0:未认证,1:已认证)
    private String  isCertification;

    //认证时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date certificationTime;

    //校验码(后六位)
    private String checkDigit;

    //价税合计
    private String leviedTotal;

    //专票/普票(0:专票,1:普票)
    private String receiptType;

    //是否未固定资产(0:固定资产,1:非固定资产)
    private String fixedAssets;

    //凭证号
    private String voucherNum;

    //是否处理
    private String isHandle;

    //错误代码
    private String errorCode;

    //处理人
    private String handlerUser;

    //资产分类一级目录
    private String assertClassFirdir;

    //资产分类二级目录
    private String assertClassSecdir;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getReceiptCategory() {
        return receiptCategory;
    }

    public void setReceiptCategory(String receiptCategory) {
        this.receiptCategory = receiptCategory;
    }

    public String getReceiptContent() {
        return receiptContent;
    }

    public void setReceiptContent(String receiptContent) {
        this.receiptContent = receiptContent;
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

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
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

    public Date getCertificationTime() {
        return certificationTime;
    }

    public void setCertificationTime(Date certificationTime) {
        this.certificationTime = certificationTime;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(String checkDigit) {
        this.checkDigit = checkDigit;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public String getVoucherNum() {
        return voucherNum;
    }

    public void setVoucherNum(String voucherNum) {
        this.voucherNum = voucherNum;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(String isHandle) {
        this.isHandle = isHandle;
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

    public String getHandlerUser() {
        return handlerUser;
    }

    public void setHandlerUser(String handlerUser) {
        this.handlerUser = handlerUser;
    }

    public String getAssertClassFirdir() {
        return assertClassFirdir;
    }

    public void setAssertClassFirdir(String assertClassFirdir) {
        this.assertClassFirdir = assertClassFirdir;
    }

    public String getAssertClassSecdir() {
        return assertClassSecdir;
    }

    public void setAssertClassSecdir(String assertClassSecdir) {
        this.assertClassSecdir = assertClassSecdir;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", serialNumber=" + serialNumber +
                ", receiptCode='" + receiptCode + '\'' +
                ", receiptNum='" + receiptNum + '\'' +
                ", sellerUnitName='" + sellerUnitName + '\'' +
                ", receiptTime=" + receiptTime +
                ", receiptCategory='" + receiptCategory + '\'' +
                ", receiptContent='" + receiptContent + '\'' +
                ", money='" + money + '\'' +
                ", rate='" + rate + '\'' +
                ", tax='" + tax + '\'' +
                ", submitAccountStaff='" + submitAccountStaff + '\'' +
                ", entryTime=" + entryTime +
                ", remarks='" + remarks + '\'' +
                ", compProj='" + compProj + '\'' +
                ", department='" + department + '\'' +
                ", isCertification='" + isCertification + '\'' +
                ", certificationTime=" + certificationTime +
                ", checkDigit='" + checkDigit + '\'' +
                ", leviedTotal='" + leviedTotal + '\'' +
                ", receiptType='" + receiptType + '\'' +
                ", fixedAssets='" + fixedAssets + '\'' +
                ", voucherNum='" + voucherNum + '\'' +
                ", isHandle='" + isHandle + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", handlerUser='" + handlerUser + '\'' +
                ", assertClassFirdir='" + assertClassFirdir + '\'' +
                ", assertClassSecdir='" + assertClassSecdir + '\'' +
                '}';
    }
}
