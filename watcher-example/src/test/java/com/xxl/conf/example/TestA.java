package com.xxl.conf.example;

import com.xxl.conf.core.XxlConfClient;
import com.xxl.conf.example.core.constant.B;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by chengzhengrong on 2017/6/25.
 */
public class TestA extends BaseTest{

    @Autowired
    private B b;

    @Test
    public void c(){
        String a = b.getA();
        String b = this.b.getB();
        System.out.println(a+b);
    }

    @Test
    public void a(){
        String paramByClient = XxlConfClient.get("default.key02", null);
        System.out.println(paramByClient);
    }
}
