/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.job;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.jiangnan.es.schedule.JobConstants;
import com.jiangnan.es.schedule.entity.JobExecutionLog;
import com.jiangnan.es.util.JsonUtils;

/**
 * @description TODO
 * @author ywu@wuxicloud.com
 * 2016年2月15日 上午11:17:29
 */
@Service
public class TestJob {
	
	public void test() {
		System.out.println("test job");
	}
	
	public void test(Map<String, Object> params) {
		JobExecutionLog executionLog = (JobExecutionLog)params.get(JobConstants.PARAM_EXECUTION_LOG);
		executionLog.addContext("xxx", "job with param");
		System.out.println("test job");
		throw new RuntimeException("test");
	}
	
	public void testWithParam(Map<String, Object> params) {
		JobExecutionLog executionLog = (JobExecutionLog)params.get(JobConstants.PARAM_EXECUTION_LOG);
		executionLog.addContext("xxx", "job with param");
		System.out.println("params:" + JsonUtils.object2JsonString(params));
		System.out.println("test job with param");
	}
	
	public void testWithException() {
		System.out.println("test job with exception");
		throw new RuntimeException("test");
	}
}
