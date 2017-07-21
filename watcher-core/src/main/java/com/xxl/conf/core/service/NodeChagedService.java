//package com.xxl.conf.core.service;
//
//import com.xxl.conf.core.XxlConfClient;
//import com.xxl.conf.core.dao.IXxlConfGroupMapper;
//import com.xxl.conf.core.dao.IXxlConfNodeMapper;
//import com.xxl.conf.core.model.XxlConfNode;
//import com.xxl.conf.core.util.SpirngUtil;
//
///**
// * Created by chengzhengrong on 2017/7/20.
// */
//public class NodeChagedService {
//
//    private static IXxlConfGroupMapper xxlConfGroupMapper = (IXxlConfGroupMapper)SpirngUtil.getService(IXxlConfGroupMapper.class);
//
//    private static IXxlConfNodeMapper xxlConfNodeMapper = (IXxlConfNodeMapper)SpirngUtil.getService(IXxlConfNodeMapper.class);
//
//    public static void remove(String key) {
//        XxlConfClient.remove(key);
//        String[] groupKey = key.split(".");
//        xxlConfNodeMapper.deleteByKey(groupKey[0], groupKey[1]);
//    }
//
//    public static void update(String key, String data) {
//        XxlConfClient.update(key, data);
//        String[] groupKey = key.split(".");
//        XxlConfNode xxlConfNode = new XxlConfNode();
//        xxlConfNode.setNodeGroup(groupKey[0]);
//        xxlConfNode.setNodeKey(groupKey[1]);
//        xxlConfNode.setNodeValue(data);
//        xxlConfNodeMapper.update(xxlConfNode);
//    }
//
//}
