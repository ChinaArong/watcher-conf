package com.xxl.conf.example;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by chengzhengrong on 2017/6/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:applicationcontext-xxl-conf.xml"})
public class BaseTest {
}
