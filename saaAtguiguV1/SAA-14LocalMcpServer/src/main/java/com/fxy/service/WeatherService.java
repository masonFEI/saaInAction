/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * WeatherService
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-06 14:20
 */
@Service
public class WeatherService {

    @Tool(description = "根据城市名称获取天气预报")
    public String getWeatherByCity(String city) {
        Map<String, String> map = Map.of(
                "北京", "111降雨频繁",
                "上海", "222多云",
                "深圳", "333多云40天"
        );

        return map.getOrDefault(city, "抱歉：未查询到对应城市！");
    }

}
