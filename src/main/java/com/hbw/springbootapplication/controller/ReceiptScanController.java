package com.hbw.springbootapplication.controller;

import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptPage;
import com.hbw.springbootapplication.entity.scan.ScanReceiptRes;
import com.hbw.springbootapplication.service.ExportService;
import com.hbw.springbootapplication.service.ReceiptScanService;
import com.hbw.springbootapplication.service.ReceiptService;
import com.hbw.springbootapplication.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/receiptScan")
public class ReceiptScanController {

    @Autowired
    ReceiptService receiptService;

    @Autowired
    ReceiptScanService receiptScanService;

    @Autowired
    ExportService exportService;

    @RequestMapping(value = "/receiptScan",method = RequestMethod.POST)
    public @ResponseBody ReceiptPage receiptScan(HttpServletRequest request, MultipartFile file, ScanReceiptRes scanReceiptRes) {

        Receipt receipt = receiptScanService.dataFormatChange(scanReceiptRes);
        ReceiptPage receiptPage = receiptScanService.flieUpload(request,file, receipt);
        receiptService.addReceipt(receipt);
        return receiptPage;
    }

    @PostMapping("/receive")
    public void receiveReceipt(HttpServletRequest request,
                        @RequestParam(value = "json", required = false) String json,
                        @RequestParam(value = "fileName", required = false) MultipartFile file){
        receiptScanService.scanDeal(json,file,request);
    }

    @GetMapping("/countScanReceiptNum")
    public Integer countScanReceiptNum(){
        return receiptScanService.countScanReceiptNum();
    }

    @GetMapping("/selectScanReceipts")
    public ReceiptPage selectScanReceipts(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        List<Receipt> receiptList = receiptScanService.selectScanReceipts(pageNum,pageSize);
        for (int i=0;i<receiptList.size();i++){
            receiptList.get(i).setSerialNumber((pageNum-1)*pageSize+i+1);
        }
        Integer count = receiptScanService.countScanReceiptNum();
        ReceiptPage receiptPage = new ReceiptPage();
        receiptPage.setMsg("success");
        receiptPage.setCode(0);
        receiptPage.setData(receiptList);
        receiptPage.setCount(count);
        return receiptPage;
    }

    @GetMapping("/selectReapetReceipt")
    public Receipt selectReapetReceipt(String receiptNum){
        return  receiptScanService.selectReapetReceipt(receiptNum);
    }

    @PostMapping("/updateScanReceipt")
    public Integer updateScanReceipt(@RequestBody Receipt receipt){
        System.out.println(receipt);
        return receiptScanService.updateScanReceipt(receipt);
    }

    @GetMapping("/delScanReceipt")
    public Integer delScanReceipt(HttpServletRequest request,Integer id){
        return receiptScanService.delScanReceipt(request,id);
    }

    @PostMapping("/handleScanReceipts")
    public ReceiptPage handleScanReceipts(HttpServletRequest httpServletRequest,@RequestBody List<Receipt> receiptList){
        return receiptScanService.handleScanReceipts(httpServletRequest,receiptList);
    }

    @GetMapping("/delUploadImg")
    public ReceiptPage delUploadImg(HttpServletRequest request,String receiptnum){
        return receiptScanService.delUploadImg(request,receiptnum);
    }

    @RequestMapping("/downReceipt")
    public @ResponseBody ReceiptPage downReceipt(HttpServletRequest request, HttpServletResponse response, @RequestParam List<Integer> idList) {
        ReceiptPage receiptPage = receiptService.downReceipt(request, response, idList);
        return receiptPage;
    }

    @RequestMapping("/downReceipt2")
    public void downReceipt2(HttpServletRequest request, HttpServletResponse response,@RequestParam List<Integer> idList) {
        receiptService.downReceipt2(request, response, idList);
    }

    @RequestMapping("/delAllScanReceipt")
    public Integer delAllScanReceipt(){
        Integer integer = receiptScanService.delAllTempReceipt();
        return integer;
    }

    @RequestMapping("/selectSubbmitList")
    public @ResponseBody List<String> selectSubbmitList(){
        return receiptScanService.selectSubbmitList();
    }

}
