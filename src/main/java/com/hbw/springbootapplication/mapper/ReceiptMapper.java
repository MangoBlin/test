package com.hbw.springbootapplication.mapper;

import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReceiptMapper {
    
    //查询所有的记录
    List<Receipt> findAll();

    //根据id查询receipt信息
    Receipt selectReceiptById(Integer id);

    //条件查询范围记录
    List<Receipt> findReceiptByMsg(@Param(value = "receiptQueryVo") ReceiptQueryVo receiptQueryVo);
    
    //查询数据总条数
    Integer countReceiptNum(@Param(value = "receiptQueryVo") ReceiptQueryVo receiptQueryVo);

    //认证
    Integer certReceipt(Integer id);

    //取消认证
    Integer cancelReceipt(Integer id);

    //删除记录
    Integer delReceipt(Integer id);

    //编辑记录
    Integer editReceipt(@Param(value = "receipt")Receipt receipt);

    //插入记录
    Integer addReceipt(@Param(value = "receipt")Receipt receipt);

    //查询发票号码数量
    Integer countByRecNum(@Param(value = "receipt")Receipt receipt);

    //查询发票号码数量
    Integer countNoRepeatRecNum(@Param(value = "receipt")Receipt receipt);

    //根据发票号码查询发票信息
    Receipt selectRecpByNum(@Param(value = "receiptNum")String receiptNum);

    //根据id查询对应的receiptList
    List<Receipt> findListByIds(@Param("idList")List<Integer> idList);

    //查询所有的发票类别
    List<String> selectCategoryList();

    //查询所有的发票内容
    List<String> selectContentList();
}
