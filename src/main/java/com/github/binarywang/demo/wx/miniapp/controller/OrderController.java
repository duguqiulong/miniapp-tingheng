package com.github.binarywang.demo.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.github.binarywang.demo.wx.miniapp.service.FileService;
import com.github.binarywang.demo.wx.miniapp.service.TradeRecord;
import com.github.binarywang.demo.wx.miniapp.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="https://github.com/noffline">lws</a>
 */
@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final WxMaService wxMaService;
    private final WxMaMessageRouter wxMaMessageRouter;

    @Autowired
    FileService fileService;

    @GetMapping(value="wx/orderlist", produces = "application/json;charset=utf-8")
    public String orderlist() {
        log.info("接收到来自微信服务器的查询请求");
        List<TradeRecord> tradeRecords = fileService.getTradeRecordsAsJson();
        log.info("查询结果为：{}", tradeRecords);
        return JsonUtils.toJson(tradeRecords);

    }


}
