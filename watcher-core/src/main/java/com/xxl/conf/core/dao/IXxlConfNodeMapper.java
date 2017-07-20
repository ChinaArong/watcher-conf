package com.xxl.conf.core.dao;


import com.xxl.conf.core.model.XxlConfNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 配置
 * @author xuxueli
 */
@Repository
public interface IXxlConfNodeMapper {

	public List<XxlConfNode> pageList(@Param("offset") int offset,@Param("pagesize") int pagesize
			,@Param("nodeGroup") String nodeGroup,@Param("nodeKey") String nodeKey);

	public int pageListCount(@Param("offset") int offset,@Param("pagesize") int pagesize
			,@Param("nodeGroup") String nodeGroup,@Param("nodeKey") String nodeKey);

	public int deleteByKey(@Param("nodeGroup") String nodeGroup,@Param("nodeKey") String nodeKey);

	public void insert(XxlConfNode xxlConfNode);

	public XxlConfNode selectByKey(@Param("nodeGroup") String nodeGroup,@Param("nodeKey") String nodeKey);

	public int update(XxlConfNode xxlConfNode);
	
}
