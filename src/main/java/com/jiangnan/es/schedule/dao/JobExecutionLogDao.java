/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.dao;

import java.util.List;
import java.util.Map;

import com.jiangnan.es.common.repository.BaseRepository;
import com.jiangnan.es.schedule.entity.JobExecutionLog;
import com.jiangnan.es.schedule.entity.JobExecutionLogWrapper;

/**
 * @description 调度运行日志dao
 * @author ywu@wuxicloud.com
 * 2016年2月15日 下午3:50:06
 */
public interface JobExecutionLogDao extends BaseRepository<JobExecutionLog> {
	/**
	 * 分页
	 * @param params
	 * @return
	 */
	List<JobExecutionLogWrapper> list(Map<String, Object> params);
}
