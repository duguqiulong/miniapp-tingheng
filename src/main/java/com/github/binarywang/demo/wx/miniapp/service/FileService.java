package com.github.binarywang.demo.wx.miniapp.service;

import java.util.List;

public interface FileService {
    /**
     * 读取文件内容，按行返回
     * @return 文件每行内容组成的列表
     */
    List<String> readFileLines();

    List<TradeRecord> getTradeRecordsAsJson();
}
