package com.miaosha.util;

import com.alibaba.fastjson.JSON;
import com.miaosha.utils.Md5Util;
import com.miaosha.utils.ValidatorUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class UtilTest {

    @Test
    public void test1() throws Exception {
        Double a = 1.001;
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("1","2");
        System.out.println(JSON.toJSONString(""+a));
        System.out.println(JSON.toJSONString(objectObjectHashMap));
    }
    @Test
    public void test2() throws Exception {
        String src = "123456";
        String s = Md5Util.inputMd5FormPass(src);
        String dbPass = Md5Util.FormPassMd5DbPass(s,"1a2b3c");
        System.out.println(s);
        System.out.println(dbPass);
    }
    @Test
    public void test3() throws Exception {
        String src = "13333333333";
        System.out.println(ValidatorUtil.isMobile(src));
    }
}
