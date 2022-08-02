package com.linjicong.cloud.stat.dao.typehandle.impl;

import com.linjicong.cloud.stat.dao.typehandle.JsonTypeHandler;

import java.util.Map;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class MapJsonTypeHandle extends JsonTypeHandler<Map> {
    public MapJsonTypeHandle() {
        super(Map.class);
    }
}
