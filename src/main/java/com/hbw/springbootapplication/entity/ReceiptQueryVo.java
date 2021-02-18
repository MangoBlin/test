package com.hbw.springbootapplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReceiptQueryVo {

    private String receiptCode;
    private String receiptNum;
    private String receiptCategory;
    private String receiptContent;

    //专票/普票
    private String receiptType;
    //部门
    private String department;

    private String  isCertification;
    //开票开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date receiptStartime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    //开票结束时间
    private Date receiptEndtime;

    //录入开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date entryStartime;

    //录入结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date entryEndtime;

    //是否固定资产
    private String fixedAssets;

    //公司/项目
    private String compProj;

    //资产分类一级目录
    private String assertClassFirdir;

    //资产分类二级目录
    private String assertClassSecdir;

    //当前页数
    private Integer pageNum;
    //每页显示最大数
    private Integer pageSize;

    //销售方单位名称
    private String sellerUnitName;


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

    public String getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(String isCertification) {
        this.isCertification = isCertification;
    }

    public Date getReceiptStartime() {
        return receiptStartime;
    }

    public void setReceiptStartime(Date receiptStartime) {
        this.receiptStartime = receiptStartime;
    }

    public Date getReceiptEndtime() {
        return receiptEndtime;
    }

    public void setReceiptEndtime(Date receiptEndtime) {
        this.receiptEndtime = receiptEndtime;
    }

    public Date getEntryStartime() {
        return entryStartime;
    }

    public void setEntryStartime(Date entryStartime) {
        this.entryStartime = entryStartime;
    }

    public Date getEntryEndtime() {
        return entryEndtime;
    }

    public void setEntryEndtime(Date entryEndtime) {
        this.entryEndtime = entryEndtime;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public String getCompProj() {
        return compProj;
    }

    public void setCompProj(String compProj) {
        this.compProj = compProj;
    }

    public String getSellerUnitName() {
        return sellerUnitName;
    }

    public void setSellerUnitName(String sellerUnitName) {
        this.sellerUnitName = sellerUnitName;
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
        return "ReceiptQueryVo{" +
                "receiptCode='" + receiptCode + '\'' +
                ", receiptNum='" + receiptNum + '\'' +
                ", receiptCategory='" + receiptCategory + '\'' +
                ", receiptContent='" + receiptContent + '\'' +
                ", receiptType='" + receiptType + '\'' +
                ", department='" + department + '\'' +
                ", isCertification='" + isCertification + '\'' +
                ", receiptStartime=" + receiptStartime +
                ", receiptEndtime=" + receiptEndtime +
                ", entryStartime=" + entryStartime +
                ", entryEndtime=" + entryEndtime +
                ", fixedAssets='" + fixedAssets + '\'' +
                ", compProj='" + compProj + '\'' +
                ", assertClassFirdir='" + assertClassFirdir + '\'' +
                ", assertClassSecdir='" + assertClassSecdir + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sellerUnitName='" + sellerUnitName + '\'' +
                '}';
    }
}
