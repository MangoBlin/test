package com.hbw.springbootapplication.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptPage;
import com.hbw.springbootapplication.entity.scan.BuyUnit;
import com.hbw.springbootapplication.entity.scan.RowAndWord;
import com.hbw.springbootapplication.entity.scan.ScanReceiptRes;
import com.hbw.springbootapplication.mapper.ScanReceiptMapper;
import com.hbw.springbootapplication.service.ReceiptScanService;
import com.hbw.springbootapplication.util.ImgUtil;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class ReceiptScanServiceImpl implements ReceiptScanService{

    @Autowired(required = false)
    private ScanReceiptMapper scanReceiptMapper;

    @Override
    public void scanDeal(String json,MultipartFile file,HttpServletRequest request){
        //解析json成Receipt插入到数据库
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+08"));
        URLDecoder ud = new URLDecoder();

        ScanReceiptRes scanReceipt=null;
        try {
            String s = ud.decode(json, "utf-8");
            Gson gson = new Gson();
            scanReceipt = gson.fromJson(s, ScanReceiptRes.class);
            if (scanReceipt!=null){
                Receipt receipt = dataFormatChange(scanReceipt);
                Integer countByRecNum = scanReceiptMapper.scanCountByRecNum(receipt);
                if (!countByRecNum.equals(0)){
                    receipt.setErrorCode("1");
                    scanReceiptMapper.scanAddReceipt(receipt);
                }else{
                    Receipt trueReceipt = scanReceiptMapper.selectScanReceiptsByRecNum(receipt.getReceiptNum());
                    if (trueReceipt!=null){
                        receipt.setId(trueReceipt.getId());
                        scanReceiptMapper.updateScanReceipt(receipt);
                    }else {
                        scanReceiptMapper.scanAddReceipt(receipt);
                    }
                }
                flieUpload(request,file,receipt);
            }
        }catch (Exception e){
            System.out.println(e);

        }
    }

    @Override
    public ReceiptPage flieUpload(HttpServletRequest request,MultipartFile file, Receipt receipt) {
        ReceiptPage receiptPage = new ReceiptPage();
        BufferedOutputStream stream = null;
        if (!file.isEmpty()) {
            try {
                String uploadFilePath = file.getOriginalFilename();
                System.out.println("uploadFlePath:" + uploadFilePath);
                // 截取上传文件的文件名
                String uploadFileName = uploadFilePath
                        .substring(uploadFilePath.lastIndexOf('\\') + 1,
                                uploadFilePath.indexOf('.'));
                System.out.println("multiReq.getFile()" + uploadFileName);

                //String path = "/upload/";
                //String path=request.getSession().getServletContext().getRealPath("/upload/");
                String path ="F:/springboot_project/receipt_manage/upload/";

                // 截取上传文件的后缀
                String uploadFileSuffix = uploadFilePath.substring(
                        uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
                System.out.println("uploadFileSuffix:" + uploadFileSuffix);

                File uploadFile = new File(path + receipt.getReceiptNum() + "." + uploadFileSuffix);
                if(uploadFile.exists()&&"1".equals(receipt.getErrorCode())){
                    System.out.println("文件已存在");
//                    path=request.getSession().getServletContext().getRealPath("/repeatupload/");
                    path ="F:/springboot_project/receipt_manage/repeatupload/";
                    uploadFile = new File(path + receipt.getReceiptNum() + "." + uploadFileSuffix);
                }

                stream = new BufferedOutputStream(new FileOutputStream(uploadFile));
                byte[] bytes = file.getBytes();
                stream.write(bytes,0,bytes.length);
                ImgUtil.rotateImage90(uploadFile);
//                file.transferTo(new File(""));
            } catch (Exception e) {
                receiptPage.setMsg(e.toString());
                receiptPage.setCode(-1);
                e.printStackTrace();
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException e) {
                    receiptPage.setMsg(e.toString());
                    receiptPage.setCode(-1);
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("上传失败");
        }
        System.out.println("文件接受成功了");
        receiptPage.setMsg("success");
        receiptPage.setCode(1);
        return receiptPage;
    }

    @Override
    public Receipt dataFormatChange(ScanReceiptRes scanReceiptRes) {
        Receipt receipt = new Receipt();
        try {
            BuyUnit buyUnit = new BuyUnit();
            buyUnit.setBuyerName(scanReceiptRes.getWords_result().getPurchaserName());
            buyUnit.setTaxpayersNum(scanReceiptRes.getWords_result().getPurchaserRegisterNum());
            buyUnit.setAddAndTel(scanReceiptRes.getWords_result().getPurchaserAddress());
            buyUnit.setAccountName(scanReceiptRes.getWords_result().getPurchaserBank());

//            if ("河南省水利勘测有限公司".equals(buyUnit.getBuyerName())
//                    &&"914101054158051427".equals(buyUnit.getTaxpayersNum())
//                    &&"".equals(buyUnit.getAddAndTel())
//                    &&"".equals(buyUnit.getAccountName())
//                    ){
//                receipt.setErrorCode("0");
//            }else if("河南省水利勘测有限公司".equals(buyUnit.getBuyerName())
//                    &&"914101054158051427".equals(buyUnit.getTaxpayersNum())
//                    &&"郑州市黄河路7号0371-65351169".equals(buyUnit.getAddAndTel())
//                    &&"上海浦东发展银行郑州分行花园路支行76110154800000688".equals(buyUnit.getAccountName())
//                    ){
//                receipt.setErrorCode("0");
//            }else if("河南省水利勘测有限公司".equals(buyUnit.getBuyerName())
//                    &&"914101054158051427".equals(buyUnit.getTaxpayersNum())
//                    &&"郑州市黄河路7号65351169".equals(buyUnit.getAddAndTel())
//                    &&"上海浦东发展银行郑州分行花园路支行76110154800000688".equals(buyUnit.getAccountName())
//                    ){
//                receipt.setErrorCode("0");
//            }else {
//                receipt.setErrorCode("2");
//            }
            receipt.setErrorCode("0");
            if (!"河南省水利勘测有限公司".equals(buyUnit.getBuyerName())){
                receipt.setErrorCode("2");
            }
            if (!"914101054158051427".equals(buyUnit.getTaxpayersNum())){
                receipt.setErrorCode("6");
            }
            if (!("郑州市黄河路7号0371-65351169".equals(buyUnit.getAddAndTel())||"郑州市黄河路7号65351169".equals(buyUnit.getAddAndTel())||"".equals(buyUnit.getAddAndTel()))){
                receipt.setErrorCode("4");
            }
            if (!("上海浦东发展银行郑州分行花园路支行76110154800000688".equals(buyUnit.getAccountName())||"".equals(buyUnit.getAccountName()))){
                receipt.setErrorCode("5");
            }

            RowAndWord rowAndWord = new RowAndWord();
            rowAndWord.setRow("");
            rowAndWord.setWord("");
            if (scanReceiptRes.getWords_result().getCommodityName().size()==0){
                scanReceiptRes.getWords_result().getCommodityName().add(0,rowAndWord);
            }
            if (scanReceiptRes.getWords_result().getCommodityTaxRate().size()==0){
                scanReceiptRes.getWords_result().getCommodityTaxRate().add(0,rowAndWord);
            }
            if (scanReceiptRes.getWords_result().getCommodityAmount().size()==0){
                scanReceiptRes.getWords_result().getCommodityAmount().add(0,rowAndWord);
            }
            if (scanReceiptRes.getWords_result().getCommodityType().size()==0){
                scanReceiptRes.getWords_result().getCommodityType().add(0,rowAndWord);
            }
            if (scanReceiptRes.getWords_result().getCommodityTax().size()==0){
                scanReceiptRes.getWords_result().getCommodityTax().add(0,rowAndWord);
            }
            if (scanReceiptRes.getWords_result().getCommodityName().size()==0){
                scanReceiptRes.getWords_result().getCommodityName().add(0,rowAndWord);
            }

            String projectName= scanReceiptRes.getWords_result().getCommodityName().get(0).getWord();
            List<String> strList = projName(projectName);
            String checkDigit =subCheckString(scanReceiptRes.getWords_result().getCheckCode(),6);
            String projectNameSub = subCheckString(projectName,3);
            String rateSub = scanReceiptRes.getWords_result().getCommodityTaxRate().get(0).getWord();
            String head = scanReceiptRes.getWords_result().getInvoiceTypeOrg();

            if (scanReceiptRes.getWords_result().getInvoiceCode().length()>15){
                receipt.setReceiptCode(scanReceiptRes.getWords_result().getInvoiceCode().substring(0,14));
            }else {
                receipt.setReceiptCode(scanReceiptRes.getWords_result().getInvoiceCode());
            }

            if (scanReceiptRes.getWords_result().getSellerName().length()>30){
                receipt.setSellerUnitName(scanReceiptRes.getWords_result().getSellerName().substring(0,29));
            }else {
                receipt.setSellerUnitName(scanReceiptRes.getWords_result().getSellerName());
            }

            if (scanReceiptRes.getWords_result().getInvoiceNum().length()>10){
                receipt.setReceiptNum(scanReceiptRes.getWords_result().getInvoiceNum().substring(0,9));
                receipt.setErrorCode("4");
            }else {
                if (scanReceiptRes.getWords_result().getInvoiceNum().length()!=8){
                    receipt.setErrorCode("4");
                }
                receipt.setReceiptNum(scanReceiptRes.getWords_result().getInvoiceNum());
            }

//            if (scanReceiptRes.getWords_result().getInvoiceDate()!=null){
//                long time = 24*60*60*1000;
//                Date date2 = dateFormat(scanReceiptRes.getWords_result().getInvoiceDate());
//                Date date3 = new Date(date2.getTime()+time);
//                receipt.setReceiptTime(date3);
//            }else {
//                receipt.setReceiptTime(null);
//            }
            receipt.setReceiptTime(dateFormat(scanReceiptRes.getWords_result().getInvoiceDate()));

            receipt.setReceiptCategory(strList.get(0));
            receipt.setReceiptContent(strList.get(1));
            if (scanReceiptRes.getWords_result().getTotalAmount().length()>13){
                receipt.setMoney(scanReceiptRes.getWords_result().getTotalAmount().substring(0,12));
            }else {
                receipt.setMoney(scanReceiptRes.getWords_result().getTotalAmount());
            }

            if (scanReceiptRes.getWords_result().getCommodityTaxRate().get(0).getWord().length()>4){
                receipt.setRate(scanReceiptRes.getWords_result().getCommodityTaxRate().get(0).getWord().substring(0,3));
            }else {
                receipt.setRate(scanReceiptRes.getWords_result().getCommodityTaxRate().get(0).getWord());
            }

            if (scanReceiptRes.getWords_result().getTotalTax().length()>10){
                receipt.setTax(scanReceiptRes.getWords_result().getTotalTax().substring(0,9));
            }else {
                receipt.setTax(scanReceiptRes.getWords_result().getTotalTax());
            }

            receipt.setEntryTime(new Date());
            if (scanReceiptRes.getWords_result().getCommodityType().get(0).getWord().length()>25){
                receipt.setRemarks(scanReceiptRes.getWords_result().getCommodityType().get(0).getWord().substring(0,24));
            }else {
                receipt.setRemarks(scanReceiptRes.getWords_result().getCommodityType().get(0).getWord());
            }

            receipt.setCheckDigit(checkDigit);
            if (scanReceiptRes.getWords_result().getAmountInFiguers().length()>13){
                receipt.setLeviedTotal(scanReceiptRes.getWords_result().getAmountInFiguers().substring(0,12));
            }else {
                receipt.setLeviedTotal(scanReceiptRes.getWords_result().getAmountInFiguers());
            }

//            if("通行费".equals(projectNameSub)&&"%".equals(rateSub)&&"河南增值税专用发票".equals(head)){
//                receipt.setReceiptType("0");
//            }else {
//                receipt.setReceiptType("1");
//            }

            if("通行费".equals(projectNameSub)&&rateSub.contains("%")&&"河南增值税电子普通发票".equals(head)){
                receipt.setReceiptType("0");
            }else if("河南增值税专用发票".equals(head)){
                receipt.setReceiptType("0");
            }else {
                receipt.setReceiptType("1");
            }

            if ("0".equals(receipt.getReceiptType())){
                receipt.setIsCertification("2");
            }else if("1".equals(receipt.getReceiptType())){
                receipt.setIsCertification("0");
            }


            receipt.setFixedAssets("0");
        }catch (Exception e){
            receipt =null;
        }
        return receipt;
    }

    @Override
    public Date dateFormat(String str) {
        if (str.length()>0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            Date date;
            try {
                date = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
            System.out.println("字符串转换成时间:" + date);
            return date;
        }else {
            return null;
        }
    }

    @Override
    public List<String> projName(String str) {
        String[] strs = str.split("\\*");
        List<String> strList = new ArrayList<>();

        if (strs.length==0){
            strList.add(0,"");
            strList.add(1,"");
        }else if(strs.length==1){
            strList.add(0,"");
            if (strs[0].length()<100){
                strList.add(1,strs[0]);
            }else {
                strList.add(1,strs[0].substring(0,99));
            }
        }else if(strs.length==2){
            if (strs[0].length()<100){
                strList.add(0,strs[0]);
            }else {
                strList.add(0,strs[0].substring(0,99));
            }

            if (strs[1].length()<100){
                strList.add(1,strs[1]);
            }else {
                strList.add(1,strs[1].substring(0,99));
            }
        }else if (strs.length==3){

            if (strs[1].length()<100){
                strList.add(0,strs[1]);
            }else {
                strList.add(0,strs[1].substring(0,99));
            }

            if (strs[2].length()<100){
                strList.add(1,strs[2]);
            }else {
                strList.add(1,strs[2].substring(0,99));
            }
        }
        return strList;
    }

    @Override
    public Double doubleFormat(String str) {
        Double data;
        try {
            data = Double.parseDouble(str);
        }catch (Exception e){
            data=new Double(-1);
        }
        return data;
    }

    @Override
    public String subCheckString(String str,Integer subNum) {
        if (str.length()>=subNum){
            String res = str.substring(str.length()-subNum);
            return res;
        }else {
            return "";
        }
    }

    @Override
    public Integer countScanReceiptNum() {
        return scanReceiptMapper.countScanReceiptNum();
    }

    @Override
    public List<Receipt> selectScanReceipts(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Receipt> receiptList = scanReceiptMapper.selectScanReceipts();
        return receiptList;
    }

    @Override
    public Receipt selectReapetReceipt(String receiptNum) {
        return scanReceiptMapper.selectReapetReceipt(receiptNum);
    }

    @Override
    public Integer updateScanReceipt(Receipt receipt) {
        return scanReceiptMapper.updateScanReceipt(receipt);
    }

    @Override
    public Integer delScanReceipt(HttpServletRequest request, Integer receiptId) {
        String path ="F:/springboot_project/receipt_manage/upload/";
        Receipt receipt = scanReceiptMapper.selectSacnRecById(receiptId);
        if(receipt!=null){
            String receiptNum = receipt.getReceiptNum();
            File file = new File(path+receiptNum+".jpg");
            if (file.exists()){
                file.delete();
            }
        }
        return scanReceiptMapper.delScanReceipt(receiptId);
    }

    @Override
    public ReceiptPage handleScanReceipts(HttpServletRequest httpServletRequest,List<Receipt> receiptList) {
        ReceiptPage receiptPage = new ReceiptPage();
        List<Integer> returnList = new ArrayList<>();
        for (Receipt receipt:receiptList){
            Integer returnCode = scanReceiptMapper.handleScanReceipts(receipt);
            returnList.add(returnCode);
        }
        for (Integer code:returnList){
            if (code!=1){
                receiptPage.setCode(-1);
                receiptPage.setMsg("上传失败");
                return receiptPage;
            }
        }

        deleteFile(httpServletRequest);
        scanReceiptMapper.delErrorScanReceipt();
        receiptPage.setCode(0);
        receiptPage.setMsg("上传成功");
        return receiptPage;
    }

    @Override
    public ReceiptPage delUploadImg(HttpServletRequest request,String receiptnum) {
        ReceiptPage receiptPage = new ReceiptPage();
        String path ="F:/springboot_project/receipt_manage/upload/";
        File file = new File(path+receiptnum+".jpg");
        if (file.exists()){
            file.delete();
            receiptPage.setCode(1);
            receiptPage.setMsg("删除成功");
        }else {
            receiptPage.setCode(-1);
            receiptPage.setMsg("无该文件");
        }
        return receiptPage;
    }

    @Override
    public void deleteFile(HttpServletRequest request){

//        String path=request.getSession().getServletContext().getRealPath("/repeatupload/");
        String path ="F:/springboot_project/receipt_manage/repeatupload/";
        File file = new File(path);
        if (file == null || !file.exists()){
            return;
        }
        File[] files = file.listFiles();
        for (File f: files){
            f.delete();
        }
    }

    @Override
    public Integer delAllTempReceipt() {
        List<String> allReceiptNum = scanReceiptMapper.findAllReceiptNum();
        for (String fileName:allReceiptNum){
            String path ="F:/springboot_project/receipt_manage/upload/";
            File file = new File(path+fileName+".jpg");
            if (file.exists()){
                file.delete();
            }
        }

        String path1 ="F:/springboot_project/receipt_manage/repeatupload/";
        File file = new File(path1);
        File[] files = file.listFiles();
        for (File f: files){
            f.delete();
        }

        return scanReceiptMapper.delAllTempReceipt();
    }

    @Override
    public List<String> selectSubbmitList() {
        return scanReceiptMapper.selectSubbmitList();
    }

}
