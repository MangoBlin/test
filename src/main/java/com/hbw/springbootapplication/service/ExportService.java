package com.hbw.springbootapplication.service;

import com.hbw.springbootapplication.entity.ReceiptPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExportService {

    ReceiptPage exportExcel3(HttpServletRequest request, HttpServletResponse response, List<Integer> idList);
}
