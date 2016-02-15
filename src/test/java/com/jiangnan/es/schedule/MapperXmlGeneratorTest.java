/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule;

import java.io.IOException;

import org.junit.Test;

import com.jiangnan.es.orm.mybatis.util.MybatisMapperXmlGenerator;
import com.jiangnan.es.schedule.dao.JobExecutionLogDao;
import com.jiangnan.es.schedule.entity.JobExecutionLog;

/**
 * @description xml mapper测试
 * @author ywu@wuxicloud.com
 * 2016年1月12日 下午4:56:10
 */
public class MapperXmlGeneratorTest {

	@Test
	public void testGenerator() {
		MybatisMapperXmlGenerator generator = new MybatisMapperXmlGenerator(JobExecutionLog.class, JobExecutionLogDao.class, "d:/");
		//generator.setIncludeParentProperty(true);
		try {
			generator.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
