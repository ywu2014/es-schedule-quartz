/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jiangnan.es.common.entity.BaseEntity;

/**
 * @description 调度执行日志
 * @author ywu@wuxicloud.com
 * 2016年2月3日 下午3:48:03
 */
public class JobExecutionLog extends BaseEntity<Integer> {

	private static final long serialVersionUID = 4397948528836682522L;
	/**主键*/
	private Integer id;
	/**任务类*/
	private JobTask jobTask;
	/**状态*/
	private JobExecutionStatus status;
	/**开始执行时间*/
	private Date startTime = new Date();
	/**执行结束时间*/
	private Date finishedTime;
	/**参数*/
	private Map<String, Object> context = new HashMap<String, Object>();

	public Integer getId() {
		return this.id;
	}

	public JobTask getJobTask() {
		return jobTask;
	}

	public void setJobTask(JobTask jobTask) {
		this.jobTask = jobTask;
	}

	public JobExecutionStatus getStatus() {
		return status;
	}

	public void setStatus(JobExecutionStatus status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void addContext(String key, Object value) {
		this.context.put(key, value);
	}
}
