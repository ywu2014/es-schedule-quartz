/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.entity;

import java.util.Date;

import com.jiangnan.es.common.entity.BaseEntity;

/**
 * @description 调度运行记录包装,用于页面展示
 * @author ywu@wuxicloud.com
 * 2016年2月16日 上午10:48:57
 */
public class JobExecutionLogWrapper extends BaseEntity<Integer> {
	
	private static final long serialVersionUID = -7951686615434670733L;
	
	/**主键*/
	private Integer id;
	/**spring bean id*/
	private String beanId;
	/**bean 方法名称*/
	private String methodName;
	/**组名称*/
	private String group = "default";
	/**描述*/
	private String description;
	/**状态*/
	private JobExecutionStatus status;
	/**开始执行时间*/
	private Date startTime;
	/**执行结束时间*/
	private Date finishedTime;

	@Override
	public Integer getId() {
		return this.id;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setId(Integer id) {
		this.id = id;
	}
	
}
