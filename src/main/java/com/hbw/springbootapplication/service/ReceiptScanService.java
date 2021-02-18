package com.hbw.springbootapplication.service;

import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptPage;
import com.hbw.springbootapplication.entity.scan.ScanReceiptRes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;

public interface ReceiptScanService {

    void scanDeal(String json,MultipartFile file,HttpServletRequest request);

    ReceiptPage flieUpload(HttpServletRequest request,MultipartFile file, Receipt receipt);

    Receipt dataFormatChange(ScanReceiptRes scanReceiptRes);

    Date dateFormat(String str);

    List<String> projName(String str);

    Double doubleFormat(String str);

    String subCheckString(String str,Integer subNum);

    Integer countScanReceiptNum();

    List<Receipt> selectScanReceipts(Integer pageNum,Integer pageSize);

    Receipt selectReapetReceipt(String receiptNum);

    Integer updateScanReceipt(Receipt receipt);

    Integer delScanReceipt(HttpServletRequest request, Integer receiptId);

    ReceiptPage handleScanReceipts(HttpServletRequest httpServletRequest,List<Receipt> receiptList);

    ReceiptPage delUploadImg(HttpServletRequest request,String receiptnum);

    void deleteFile(HttpServletRequest request);

    Integer delAllTempReceipt();

    List<String> selectSubbmitList();

}
