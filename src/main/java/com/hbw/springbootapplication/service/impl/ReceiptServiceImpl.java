package com.hbw.springbootapplication.service.impl;

import com.github.pagehelper.PageHelper;
import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptPage;
import com.hbw.springbootapplication.entity.ReceiptQueryVo;
import com.hbw.springbootapplication.mapper.ReceiptMapper;
import com.hbw.springbootapplication.service.ReceiptService;
import com.hbw.springbootapplication.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired(required = false)
    ReceiptMapper receiptMapper;

    //是否认证;专票/普票
    public List<Receipt> setCert(List<Receipt> receiptList){
        for (Receipt receipt:receiptList) {
            if ("2".equals(receipt.getIsCertification())){
                receipt.setIsCertification("未认证");
            }else if("1".equals(receipt.getIsCertification())){
                receipt.setIsCertification("已认证");
            }else if("0".equals(receipt.getIsCertification())){
                receipt.setIsCertification("-");
            }

            if ("0".equals(receipt.getReceiptType())){
                receipt.setReceiptType("专票");
            }else if ("1".equals(receipt.getReceiptType())){
                receipt.setReceiptType("普票");
            }

            if("0".equals(receipt.getFixedAssets())){
                receipt.setFixedAssets("非固定资产");
            }else if("1".equals(receipt.getFixedAssets())){
                receipt.setFixedAssets("固定资产");
            }else if("2".equals(receipt.getFixedAssets())){
                receipt.setFixedAssets("无形资产");
            }
        }
        return receiptList;
    }

    @Override
    public List<Receipt> findAll(Integer pageNum,Integer pageSize){
        Integer i=1;
        PageHelper.startPage(pageNum,pageSize);
        List<Receipt> receiptList = receiptMapper.findAll();
        for (Receipt receipt:receiptList){
            receipt.setSerialNumber(pageNum*pageNum+i++);
        }
        setCert(receiptList);
        return receiptList;
    }

    @Override
    public List<Receipt> findReceiptByMsg(ReceiptQueryVo receiptQueryVo,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Receipt> receiptList = receiptMapper.findReceiptByMsg(receiptQueryVo);
        setCert(receiptList);
        return receiptList;
    }

    @Override
    public Integer countReceiptNum(ReceiptQueryVo receiptQueryVo) {
        return receiptMapper.countReceiptNum(receiptQueryVo);
    }

    @Override
    public Integer certReceipt(Integer id) {
        return receiptMapper.certReceipt(id);
    }

    @Override
    public Integer cancelReceipt(Integer id) {
        return receiptMapper.cancelReceipt(id);
    }

    @Override
    public Integer delReceipt(Integer id) {
        String path ="F:/springboot_project/receipt_manage/upload/";
        Receipt receipt = receiptMapper.selectReceiptById(id);
        if(receipt!=null){
            String receiptNum = receipt.getReceiptNum();
            File file = new File(path+receiptNum+".jpg");
            System.out.println(file.getAbsolutePath());
            if (file.exists()){
                file.delete();
            }
        }
        return receiptMapper.delReceipt(id);
    }

    @Override
    public ReceiptPage editReceipt(Receipt receipt) {
        Integer countByRecNum = receiptMapper.countNoRepeatRecNum(receipt);
        ReceiptPage receiptPage = new ReceiptPage();
        if (countByRecNum!=0){
            receiptPage.setCode(-1);
            receiptPage.setMsg("发票号码已存在");
        }else {
            if ("1".equals(receipt.getReceiptType())){
                receipt.setIsCertification("0");
                receipt.setCertificationTime(null);
                receipt.setVoucherNum(null);
            }
            if (receipt.getIsCertification().equals("0")||receipt.getIsCertification().equals("2")){
                receipt.setCertificationTime(null);
            }
            if ("0".equals(receipt.getReceiptType())&&"1".equals(receipt.getIsCertification())&&("".equals(receipt.getCertificationTime())||receipt.getCertificationTime()==null)){
                receipt.setCertificationTime(new Date());
            }

            receiptPage.setCode(1);
            receiptPage.setMsg("编辑成功");
            receiptMapper.editReceipt(receipt);
        }

        return receiptPage;
    }

    @Override
    public Integer addReceipt(Receipt receipt) {
        Integer returnCode;
        if (receipt.getReceiptNum()==null||"".equals(receipt.getReceiptNum())){
            returnCode=-2;
        }else {
            Integer countByRecNum = receiptMapper.countByRecNum(receipt);
            if (countByRecNum==0){

                if ("1".equals(receipt.getReceiptType())){
                    receipt.setIsCertification("0");
                    receipt.setCertificationTime(null);
                }else {
                    receipt.setIsCertification("2");
                    receipt.setCertificationTime(null);
                }
                receiptMapper.addReceipt(receipt);
                returnCode=0;
            }else {
                returnCode=-1;
            }
        }
        return returnCode;
    }

    @Override
    public Receipt selectRecpByNum(String receiptNum) {
        return receiptMapper.selectRecpByNum(receiptNum);
    }

    @Override
    public List<String> selectCategoryList() {
        return receiptMapper.selectCategoryList();
    }

    @Override
    public ReceiptPage downReceipt(HttpServletRequest request, HttpServletResponse response, List<Integer> idList) {

        List<Receipt> receiptList = receiptMapper.findListByIds(idList);
        String path ="F:/springboot_project/receipt_manage/upload/";
        String zipName = "receipt";
        List<File> fileList = new ArrayList<>();
        for (Receipt receipt:receiptList){
            File file1 = new File(path+receipt.getReceiptNum()+".jpg");
            if (file1.exists()){
                fileList.add(file1);
            }
        }

        ZipUtil.zipFiles(fileList, zipName, response);

        ReceiptPage receiptPage = new ReceiptPage();
        receiptPage.setCode(1);
        receiptPage.setMsg("success");
        return receiptPage;
    }

    @Override
    public ReceiptPage downReceipt2(HttpServletRequest request, HttpServletResponse response, List<Integer> idList) {
        List<Receipt> receiptList = receiptMapper.findListByIds(idList);

        try {
            ZipUtil.transferFiles(receiptList);

            ZipUtil.copytAndDelEmptyDir();

            FileOutputStream fos1 = new FileOutputStream(new File("F:/springboot_project/receipt_manage/zipDir/receipt.zip"));
//            ZipUtil.toZip("F:/springboot_project/receipt_manage/uploadzip", fos1,true);

            File file = new File("F:/springboot_project/receipt_manage/uploadTrueZip/uploadzip");
            if (file.exists()){
                ZipUtil.toZip("F:/springboot_project/receipt_manage/uploadTrueZip/uploadzip", fos1,true);
            }else {
                ZipUtil.toZip("F:/springboot_project/receipt_manage/uploadTrueZip", fos1,true);
            }

            ZipUtil.downloadFile(response,"receipt.zip");

        }catch (Exception e){
            ReceiptPage receiptPage = new ReceiptPage();
            receiptPage.setCode(-1);
            receiptPage.setMsg("导出失败");
            return receiptPage;
        }finally {
            ZipUtil.deleteFolder("F:/springboot_project/receipt_manage/uploadzip");
            ZipUtil.deleteFolder("F:/springboot_project/receipt_manage/zipDir");
            ZipUtil.deleteAllDir(new File("F:/springboot_project/receipt_manage/uploadTrueZip/uploadzip"));
        }

        ReceiptPage receiptPage = new ReceiptPage();
        receiptPage.setCode(1);
        receiptPage.setMsg("success");
        return receiptPage;
    }

    @Override
    public List<String> selectContentList() {
        return receiptMapper.selectContentList();
    }

    @Override
    public Receipt selectReceiptById(Integer id) {
        return receiptMapper.selectReceiptById(id);
    }

}
