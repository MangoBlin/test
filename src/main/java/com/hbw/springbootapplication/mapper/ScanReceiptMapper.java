package com.hbw.springbootapplication.mapper;

import com.hbw.springbootapplication.entity.Receipt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScanReceiptMapper {

    //查询发票号码数量
    Integer scanCountByRecNum(@Param(value = "receipt")Receipt receipt);

    //插入记录
    Integer scanAddReceipt(@Param(value = "receipt")Receipt receipt);

    //查询扫描发票的数量
    Integer countScanReceiptNum();

    //查询所有未扫描的数量
    List<Receipt> selectScanReceipts();

    //查询未处理的发票中有没有重复
    Integer scanCountByRecNumNohandler(@Param(value = "receipt")Receipt receipt);

    //更新未处理的发票
    Integer updateScanReceipt(@Param(value = "receipt")Receipt receipt);

    //查询重复发票的信息
    Receipt selectReapetReceipt(String receiptNum);

    //根据发票号码查询发票信息
    Receipt selectScanReceiptsByRecNum(String receiptNum);

    //删除未处理的发票
    Integer delScanReceipt(Integer receiptId);

    Integer handleScanReceipts(@Param(value = "receipt")Receipt receipt);

    //删除错误的发票
    Integer delErrorScanReceipt();

    //根据id查询未扫描的发票信息
    Receipt selectSacnRecById(Integer id);

    //删除所有未处理的发票
    Integer delAllTempReceipt();

    //报销人员列表
    List<String> selectSubbmitList();

    //查询所有未处理的发票的发票号码
    List<String> findAllReceiptNum();

}
