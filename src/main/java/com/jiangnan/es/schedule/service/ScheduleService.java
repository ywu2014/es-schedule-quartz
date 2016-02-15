/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.service;

import java.util.Map;

import com.jiangnan.es.common.entity.query.Page;
import com.jiangnan.es.orm.mybatis.service.MybatisBaseService;
import com.jiangnan.es.schedule.entity.JobTask;

/**
 * @description 调度业务
 * @author ywu@wuxicloud.com
 * 2016年2月3日 下午5:25:13
 */
public interface ScheduleService extends MybatisBaseService<JobTask> {
	/**
	 * map参数查询列表
	 * @param params
	 * @return
	 */
	public Page<JobTask> listMap(Map<String, Object> params);
	/**
	 * 暂停调度
	 * @param id
	 */
	public void pause(Integer id);
	/**
	 * 恢复调度
	 * @param id
	 */
	public void recover(Integer id);
	/**
	 * 立即运行一次
	 * @param id
	 */
	public void runRightNow(Integer id);
}
