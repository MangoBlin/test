package com.hbw.springbootapplication.service;

import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptPage;
import com.hbw.springbootapplication.entity.ReceiptQueryVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ReceiptService {
    List<Receipt> findAll(Integer pageNum,Integer pageSize);

    List<Receipt> findReceiptByMsg(ReceiptQueryVo receiptQueryVo,Integer pageNum,Integer pageSize);

    Integer countReceiptNum(ReceiptQueryVo receiptQueryVo);

    Integer certReceipt(Integer id);

    Integer cancelReceipt(Integer id);

    Integer delReceipt(Integer id);

    ReceiptPage editReceipt(Receipt receipt);

    Integer addReceipt(Receipt receipt);

    Receipt selectRecpByNum(String receiptNum);

    List<String> selectCategoryList();

    ReceiptPage downReceipt(HttpServletRequest request, HttpServletResponse response, List<Integer> idList);

    ReceiptPage downReceipt2(HttpServletRequest request, HttpServletResponse response, List<Integer> idList);

    List<String> selectContentList();

    Receipt selectReceiptById(Integer id);
}
