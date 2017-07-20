package com.xxl.conf.example;

import com.xxl.conf.core.XxlConfClient;
import org.junit.Test;

/**
 * Created by chengzhengrong on 2017/6/25.
 */
public class TestA extends BaseTest{

    @Test
    public void a(){
        String paramByClient = XxlConfClient.get("default.key02", null);
    }
}
