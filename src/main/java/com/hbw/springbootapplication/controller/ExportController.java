package com.hbw.springbootapplication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hbw.springbootapplication.entity.Receipt;
import com.hbw.springbootapplication.entity.ReceiptExport;
import com.hbw.springbootapplication.entity.ReceiptPage;
import com.hbw.springbootapplication.entity.User;
import com.hbw.springbootapplication.mapper.ReceiptMapper;
import com.hbw.springbootapplication.service.ExportService;
import com.hbw.springbootapplication.service.ReceiptService;
import com.hbw.springbootapplication.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ExportController {

    @Autowired
    private ExportService exportService;

    @RequestMapping("/exportExcel3")
    public void exportExcel3(HttpServletRequest request, HttpServletResponse response, @RequestParam List<Integer> idList) {
        exportService.exportExcel3(request, response, idList);
    }
}