package com.github.binarywang.demo.wx.miniapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private static final String FILE_PATH = "C:\\064064\\Datas\\RecordOneFile.txt";

    @Override
    public List<String> readFileLines() {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.error("Error reading file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("Error closing reader: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return lines;
    }

    @Override
    public List<TradeRecord> getTradeRecordsAsJson() {
        List<String> lines = readFileLines();
        List<TradeRecord> records = new ArrayList<>();
        List<TradeRecord> records_full = new ArrayList<>();

        for (String line : lines) {
            try {
                // 解析每行数据
                TradeRecord record = parseLineToTradeRecord(line);
                if (record != null) {
                    records_full.add(record);
                }
            } catch (Exception e) {
                log.error("Error parsing line: " + line, e);
            }
        }
        records = records_full.subList(Math.max(records_full.size() - 5, 0), lines.size());
        return records;
    }

    private TradeRecord parseLineToTradeRecord(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        // 示例行: 时间:2025-08-18 22:09:44,合约:sc2510P480,方向:买,开平:平昨,价格:12.95,手数:11
        TradeRecord record = new TradeRecord();

        // 分割各个字段
        String[] parts = line.split(",");

        for (String part : parts) {
            String[] keyValue = part.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "时间":
                        // 将时间格式从 2025-08-18 22:09:44 转换为 2025/8/18 22:09:44
                        record.setTime(convertDateFormat(value));
                        break;
                    case "合约":
                        record.setContract(value);
                        break;
                    case "方向":
                        String direction = value;
                        // 需要获取下一项来确定开平信息
                        record.setDirection(direction);
                        break;
                    case "开平":
                        // 更新方向字段，格式为 "方向.开平"
                        String currentDirection = record.getDirection();
                        record.setDirection(currentDirection + "." + value);
                        break;
                    case "价格":
                        record.setPrice(Double.parseDouble(value));
                        break;
                    case "手数":
                        record.setCount(Integer.parseInt(value));
                        break;
                }
            }
        }

        return record;
    }

    private String convertDateFormat(String dateTime) {
        // 将 2025-08-18 22:09:44 转换为 2025/8/18 22:09:44
        return dateTime.replace("-", "/");
    }

}

