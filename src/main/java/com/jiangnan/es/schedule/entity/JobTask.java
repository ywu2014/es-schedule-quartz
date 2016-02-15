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
 * @description 调度任务类
 * @author ywu@wuxicloud.com
 * 2016年2月3日 下午3:29:33
 */
public class JobTask extends BaseEntity<Integer> {

	private static final long serialVersionUID = 513196822421262150L;
	/**主键*/
	private Integer id;
	/**spring bean id*/
	private String beanId;
	/**bean 方法名称*/
	private String methodName;
	/**组名称*/
	private String group = "default";
	/**cron表达式*/
	private String expression;
	/**描述*/
	private String description;
	/**状态*/
	private JobStatus status = JobStatus.RUNNING;
	/**创建时间*/
	private Date createTime = new Date();
	/**更新时间*/
	private Date lastUpdate = new Date();
	/**首次运行时间*/
	private Date firstExecuteTime;
	/**下次运行时间*/
	private Date nextExecuteTime;
	/**上次运行时间*/
	private Date previoustExecuteTime;
	/**调度参数*/
	private Map<String, String> params = new HashMap<String, String>();

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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public String getParam(String name) {
		return this.params.get(name);
	}
	
	public void addParam(String name, String value) {
		this.params.put(name, value);
	}

	public Date getNextExecuteTime() {
		return nextExecuteTime;
	}

	public void setNextExecuteTime(Date nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Date getFirstExecuteTime() {
		return firstExecuteTime;
	}

	public void setFirstExecuteTime(Date firstExecuteTime) {
		this.firstExecuteTime = firstExecuteTime;
	}

	public Date getPrevioustExecuteTime() {
		return previoustExecuteTime;
	}

	public void setPrevioustExecuteTime(Date previoustExecuteTime) {
		this.previoustExecuteTime = previoustExecuteTime;
	}
	
}
