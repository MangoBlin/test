package com.hbw.springbootapplication.controller;

import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptPage;
import com.hbw.springbootapplication.entity.ReceiptQueryVo;
import com.hbw.springbootapplication.service.ReceiptScanService;
import com.hbw.springbootapplication.service.impl.ReceiptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/receipt")
public class ReceiptController {

    @Autowired
    ReceiptServiceImpl receiptService;

    @Autowired
    ReceiptScanService receiptScanService;

    private ReceiptPage setReceiptPage(List<Receipt> receiptList,Integer total){
        ReceiptPage receiptPage = new ReceiptPage();
        if (!receiptList.isEmpty()){
            receiptPage.setCode(0);
            receiptPage.setMsg("");
            receiptPage.setCount(total);
            receiptPage.setData(receiptList);
        }else {
            receiptPage.setCode(-1);
            receiptPage.setMsg("查询为空");
            receiptPage.setCount(0);
            receiptPage.setData(null);
        }
        return receiptPage;
    }

    private void judgeCertification(Integer returnCode,ReceiptPage receiptPage){
        if (returnCode==1){
            receiptPage.setCode(0);
            receiptPage.setMsg("操作成功");
        }else if(returnCode==0){
            receiptPage.setCode(-1);
            receiptPage.setMsg("操作失败");
        }else {
            receiptPage.setCode(-2);
            receiptPage.setMsg("系统错误");
        }
    }

    /**
     * 查询所有数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(path = "/findAll")
    public @ResponseBody ReceiptPage findAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        List<Receipt> receiptList = receiptService.findAll(pageNum,pageSize);

        Integer total = receiptService.countReceiptNum(new ReceiptQueryVo());
        ReceiptPage receiptPage = setReceiptPage(receiptList,total);
        return receiptPage;
    }

    /**
     * 条件查询数据
     * @param receiptQueryVo
     * @return
     */
    @RequestMapping(path = "/findReceiptByMsg")
    public @ResponseBody ReceiptPage findReceiptByMsg(@RequestBody ReceiptQueryVo receiptQueryVo){

        Integer pageNum = receiptQueryVo.getPageNum();
        Integer pageSize = receiptQueryVo.getPageSize();

        List<Receipt> receiptList = receiptService.findReceiptByMsg(receiptQueryVo,pageNum,pageSize);
        for (int i=0;i<receiptList.size();i++){
            receiptList.get(i).setSerialNumber((pageNum-1)*pageSize+i+1);
        }
        Integer total = receiptService.countReceiptNum(receiptQueryVo);
        ReceiptPage receiptPage = setReceiptPage(receiptList,total);
        return receiptPage;
    }

    /**
     * 查询数量
     * @param receiptQueryVo
     * @return
     */
    @RequestMapping(path = "/countReceiptNum")
    public @ResponseBody Integer countReceiptNum(@RequestBody ReceiptQueryVo receiptQueryVo){
        return receiptService.countReceiptNum(receiptQueryVo);
    }

    /**
     * 认证
     * @param id
     * @return
     */
    @RequestMapping(path = "/certReceipt")
    public @ResponseBody ReceiptPage certReceipt(@RequestParam Integer id){
        Integer returnCode = receiptService.certReceipt(id);
        ReceiptPage receiptPage = new ReceiptPage();
        judgeCertification(returnCode,receiptPage);
        return receiptPage;
    }

    /**
     * 取消认证
     * @param id
     * @return
     */
    @RequestMapping(path = "/cancelReceipt")
    public @ResponseBody ReceiptPage cancelReceipt(@RequestParam Integer id){
        Integer returnCode = receiptService.cancelReceipt(id);
        ReceiptPage receiptPage = new ReceiptPage();
        judgeCertification(returnCode,receiptPage);
        return receiptPage;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/delReceipt")
    public @ResponseBody ReceiptPage delReceipt(@RequestParam Integer id) {
        Integer returnCode = receiptService.delReceipt(id);
        ReceiptPage receiptPage = new ReceiptPage();
        judgeCertification(returnCode,receiptPage);
        return receiptPage;
    }

    /**
     * 编辑
     * @param receipt
     * @return
     */
    @RequestMapping(path = "/editReceipt")
    public @ResponseBody ReceiptPage editReceipt(@RequestBody Receipt receipt) {
        return receiptService.editReceipt(receipt);
    }

    /**
     * 手工添加
     * @param receipt
     * @return
     */
    @RequestMapping(path = "/addReceipt")
    public @ResponseBody ReceiptPage addReceipt(@RequestBody Receipt receipt) {
        //System.out.println(receipt);
        Integer returnCode = receiptService.addReceipt(receipt);
        ReceiptPage receiptPage = new ReceiptPage();
        receiptPage.setCode(returnCode);
        if (returnCode==0){
            receiptPage.setMsg("操作成功");
        }else if(returnCode==-1){
            receiptPage.setMsg("发票已存在,发票信息如下");
            Receipt receipt1 = receiptService.selectRecpByNum(receipt.getReceiptNum());
            List<Receipt> list = new ArrayList<>();
            list.add(receipt1);
            receiptPage.setData(list);
        }else {
            receiptPage.setMsg("请输入发票号码");
        }
        return receiptPage;
    }

    /**
     * 获取发票种类
     * @return
     */
    @GetMapping(path = "/selectCategoryList")
    public @ResponseBody List<String> selectCategoryList(){
        return receiptService.selectCategoryList();
    }

    @RequestMapping("/selectContentList")
    public @ResponseBody List<String> selectContentList(){
        return receiptService.selectContentList();
    }

    @GetMapping("/selectReceiptById")
    public @ResponseBody Receipt selectReceiptById(Integer id){
        return receiptService.selectReceiptById(id);
    }
}
