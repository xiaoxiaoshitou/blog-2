package com.lx.blog.service.Impl;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lx.blog.dao.EssayDao;
import com.lx.blog.model.Essay;
import com.lx.blog.service.EssayService;
import com.lx.blog.util.JSONUtil;
import com.lx.blog.util.PageData;


@Service
public class EssayServiceImpl extends AbstractService<Essay, Long>implements EssayService {

	private static final Logger log = LogManager.getLogger(EssayServiceImpl.class);

	@Autowired
	private EssayDao essayDao;

	@Override
	public String getEssayList(Long userId,Integer currentPage) throws Exception {
		Long sizeOfAll = essayDao.getEssayCount(userId);
		PageData<Essay> pageData = new PageData<Essay>(currentPage);
		if(sizeOfAll>0){
			RowBounds rowBounds = new RowBounds(pageData.getOffset(currentPage),pageData.getPerPageNum());
			List<Essay> essayList = essayDao.getEssayList(userId,rowBounds);
			pageData.setData(sizeOfAll, essayList);
		}
		return JSONUtil.getEscapeJSONString(pageData);
	}
}