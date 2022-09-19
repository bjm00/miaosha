package com.miaosha.util;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class UtilTest {

    @Test
    public void test() throws Exception {
        Double a = 1.001;
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("1","2");
        System.out.println(JSON.toJSONString(""+a));
        System.out.println(JSON.toJSONString(objectObjectHashMap));
    }
}
