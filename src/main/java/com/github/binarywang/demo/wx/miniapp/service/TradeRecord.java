package com.github.binarywang.demo.wx.miniapp.service;

import lombok.Data;

@Data
public class TradeRecord {
    private String time;
    private String contract;
    private String direction;
    private Double price;
    private Integer count;
}
