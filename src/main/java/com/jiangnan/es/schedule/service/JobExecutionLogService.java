/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.service;

import java.util.Map;

import com.jiangnan.es.common.entity.query.Page;
import com.jiangnan.es.orm.mybatis.service.MybatisBaseService;
import com.jiangnan.es.schedule.entity.JobExecutionLog;
import com.jiangnan.es.schedule.entity.JobExecutionLogWrapper;

/**
 * @description 调度运行日志业务接口
 * @author ywu@wuxicloud.com
 * 2016年2月15日 下午3:54:31
 */
public interface JobExecutionLogService extends MybatisBaseService<JobExecutionLog> {
	/**
	 * map参数查询用户列表
	 * @param params
	 * @return
	 */
	public Page<JobExecutionLogWrapper> listMap(Map<String, Object> params);
}
