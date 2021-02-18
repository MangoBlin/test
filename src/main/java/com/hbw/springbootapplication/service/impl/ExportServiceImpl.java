package com.hbw.springbootapplication.service.impl;

import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptExport;
import com.hbw.springbootapplication.entity.ReceiptPage;
import com.hbw.springbootapplication.mapper.ReceiptMapper;
import com.hbw.springbootapplication.service.ExportService;
import com.hbw.springbootapplication.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService{

    @Autowired(required = false)
    private ReceiptMapper receiptMapper;

    public ReceiptPage exportExcel3(HttpServletRequest request, HttpServletResponse response, List<Integer> idList) {
        //System.out.println(idList);

        ReceiptPage receiptPage = new ReceiptPage();

        String fileName = "receipt";
        ExportExcelUtil<ReceiptExport> util = new ExportExcelUtil<>();
        String[] columnNames = {"序号", "是否认证","认证时间","凭证号", "销售方单位名称","发票代码","发票号码",
                "开票日期","发票内容","价税合计","金额",
                "税率(%)", "税额","专票/普票","固定资产","资产分类", "报销人员","公司/项目",
                "部门","备注","录入时间"};

        List<Receipt> receiptList = receiptMapper.findListByIds(idList);
        List<ReceiptExport> receiptExportList =new ArrayList<>();
        for (int i=0;i<receiptList.size();i++) {
            Receipt receipt=receiptList.get(i);
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

            if ("0".equals(receipt.getFixedAssets())){
                receipt.setFixedAssets("非固定资产");
            }else if("1".equals(receipt.getFixedAssets())){
                receipt.setFixedAssets("固定资产");
            }else if("2".equals(receipt.getFixedAssets())){
                receipt.setFixedAssets("无形资产");
            }

            ReceiptExport receiptExport = new ReceiptExport();
            receiptExport.setIsCertification(receipt.getIsCertification());
            receiptExport.setCertificationTime(dateUtils(receipt.getCertificationTime()));
            receiptExport.setVoucherNum(receipt.getVoucherNum());
            receiptExport.setSellerUnitName(receipt.getSellerUnitName());
            receiptExport.setReceiptCode(receipt.getReceiptCode());
            receiptExport.setReceiptNum(receipt.getReceiptNum());
            receiptExport.setReceiptTime(dateUtils(receipt.getReceiptTime()));
//            receiptExport.setCheckDigit(receipt.getCheckDigit());
//            receiptExport.setReceiptCategory(receipt.getReceiptCategory());
//            receiptExport.setReceiptContent(receipt.getReceiptContent());
            receiptExport.setExportReceiptContent(receipt.getReceiptCategory()+"*"+receipt.getReceiptContent());
            receiptExport.setLeviedTotal(receipt.getLeviedTotal());
            receiptExport.setMoney(receipt.getMoney());
            receiptExport.setRate(receipt.getRate());
            receiptExport.setTax(receipt.getTax());
            receiptExport.setReceiptType(receipt.getReceiptType());
            receiptExport.setFixedAssets(receipt.getFixedAssets());
            if ("非固定资产".equals(receiptExport.getFixedAssets())){
                receiptExport.setAssertClassDir("");
            }else if("固定资产".equals(receiptExport.getFixedAssets())){
                if (receipt.getAssertClassSecdir()!=null&&receipt.getAssertClassSecdir()!=""){
                    receiptExport.setAssertClassDir(receipt.getAssertClassSecdir());
                }else{
                    receiptExport.setAssertClassDir(receipt.getAssertClassFirdir());
                }
            }else if("无形资产".equals(receiptExport.getFixedAssets())){
                receiptExport.setAssertClassDir(receipt.getAssertClassFirdir());
            }

            receiptExport.setSubmitAccountStaff(receipt.getSubmitAccountStaff());
            receiptExport.setCompProj(receipt.getCompProj());
            receiptExport.setDepartment(receipt.getDepartment());
            receiptExport.setRemarks(receipt.getRemarks());
            receiptExport.setEntryTime(dateUtils2(receipt.getEntryTime()));
            receiptExportList.add(receiptExport);
        }


        Collections.sort(receiptExportList);
        for (int i=0;i<receiptExportList.size();i++){
            receiptExportList.get(i).setSerialNumber(i+1);
        }
        try {
            util.exportExcel(response, fileName + ".xls", fileName, columnNames, receiptExportList, ExportExcelUtil.EXCEL_FILE_2003);
        } catch (Exception e) {
            receiptPage.setMsg(e.toString());
            receiptPage.setCode(-1);
            e.printStackTrace();
        }

        receiptPage.setMsg("success");
        receiptPage.setCode(1);

        return receiptPage;
    }

    public String dateUtils(Date date){
        if (date==null){
            return "";
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String str = sdf.format(date);
            return str;
        }
    }

    public String dateUtils2(Date date){
        if (date==null){
            return "";
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date2 = new Date(date.getTime());
            String str = sdf.format(date2);
            return str;
        }
    }
}



