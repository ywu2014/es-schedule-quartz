/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.dao;

import java.util.List;
import java.util.Map;

import com.jiangnan.es.common.repository.BaseRepository;
import com.jiangnan.es.schedule.entity.JobTask;

/**
 * @description 任务类dao
 * @author ywu@wuxicloud.com
 * 2016年2月3日 下午4:10:18
 */
public interface JobTaskDao extends BaseRepository<JobTask> {
	/**
	 * 分页
	 * @param params
	 * @return
	 */
	List<JobTask> list(Map<String, Object> params);
}
