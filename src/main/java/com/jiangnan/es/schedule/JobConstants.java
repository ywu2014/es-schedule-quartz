/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule;

/**
 * @description job相关常亮
 * @author ywu@wuxicloud.com
 * 2016年2月4日 上午9:58:38
 */
public final class JobConstants {
	private JobConstants(){};
	
	/**job 数据库 id*/
	public static final String PARAM_JOB_ID = "jobId";
	/**spring bean id*/
	public static final String PARAM_BEAN_ID = "beanId";
	/**方法名称*/
	public static final String PARAM_METHOD_NAME = "methodName";
	/**调度执行日志*/
	public static final String PARAM_EXECUTION_LOG = "executionLog";
	
	public static final String PARAM_JOB_PARAMS_SPLIT = ";";
	
	public static final String PARAM_JOB_PARAM_SPLIT = "=";
}
