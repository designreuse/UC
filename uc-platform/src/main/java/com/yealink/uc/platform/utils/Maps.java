package com.yealink.uc.platform.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ChNan
 */
public class Maps {

    public static MapChain newMapChain() {
        return new MapChain();
    }

    public static class MapChain {
        Map<String, Object> map = new HashMap<>();

        public MapChain put(String k, Object v) {
            map.put(k, v);
            return this;
        }

        public Map<String, Object> getMap() {
            return map;
        }
    }

}
