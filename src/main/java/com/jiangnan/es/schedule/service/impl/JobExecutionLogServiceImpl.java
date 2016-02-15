/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiangnan.es.common.entity.query.Page;
import com.jiangnan.es.common.repository.BaseRepository;
import com.jiangnan.es.orm.mybatis.plugin.PageHelper;
import com.jiangnan.es.orm.mybatis.service.MybatisBaseServiceSupport;
import com.jiangnan.es.schedule.dao.JobExecutionLogDao;
import com.jiangnan.es.schedule.entity.JobExecutionLog;
import com.jiangnan.es.schedule.entity.JobExecutionLogWrapper;
import com.jiangnan.es.schedule.service.JobExecutionLogService;

/**
 * @description 调度运行日志业务实现类
 * @author ywu@wuxicloud.com
 * 2016年2月15日 下午3:55:33
 */
@Service
public class JobExecutionLogServiceImpl extends MybatisBaseServiceSupport<JobExecutionLog> implements JobExecutionLogService {

	@Resource
	JobExecutionLogDao jobExecutionLogDao;
	
	@Override
	protected BaseRepository<JobExecutionLog> getRepository() {
		return jobExecutionLogDao;
	}

	@Override
	public Page<JobExecutionLogWrapper> listMap(Map<String, Object> params) {
		PageHelper.startPage();
		List<JobExecutionLogWrapper> executionLogs = jobExecutionLogDao.list(params);
		return PageHelper.getPageResult(executionLogs);
	}

}
