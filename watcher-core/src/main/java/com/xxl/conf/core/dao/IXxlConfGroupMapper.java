package com.xxl.conf.core.dao;


import com.xxl.conf.core.model.XxlConfGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xuxueli on 16/10/8.
 */
@Repository
public interface IXxlConfGroupMapper {
    public List<XxlConfGroup> findAll();

    public int save(XxlConfGroup xxlJobGroup);

    public int update(XxlConfGroup xxlJobGroup);

    public int remove(String groupName);

    public XxlConfGroup load(String groupName);
}
