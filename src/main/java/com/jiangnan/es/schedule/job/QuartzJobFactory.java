/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.job;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.jiangnan.es.common.util.SpringUtils;
import com.jiangnan.es.schedule.JobConstants;
import com.jiangnan.es.schedule.entity.JobExecutionLog;
import com.jiangnan.es.schedule.entity.JobExecutionStatus;
import com.jiangnan.es.schedule.entity.JobTask;
import com.jiangnan.es.schedule.service.JobExecutionLogService;
import com.jiangnan.es.schedule.service.ScheduleService;

/**
 * @description 调度任务总入口
 * @author ywu@wuxicloud.com
 * 2016年2月3日 下午10:08:35
 */
public class QuartzJobFactory implements Job {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobFactory.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		String beanId = jobDataMap.getString(JobConstants.PARAM_BEAN_ID);
		String methodName = jobDataMap.getString(JobConstants.PARAM_METHOD_NAME);
		Integer jobId = jobDataMap.getInt(JobConstants.PARAM_JOB_ID);
		
		LOGGER.info("job " + beanId + "_" + methodName + " start to execute");
		JobExecutionLogService executionLogService = SpringUtils.getBean(JobExecutionLogService.class);
		//记录开始日志
		ScheduleService scheduleService = SpringUtils.getBean(ScheduleService.class);
		JobTask jobTask = scheduleService.get(JobTask.class, jobId);
		JobExecutionLog executionLog = new JobExecutionLog();
		executionLog.setJobTask(jobTask);
		executionLog.setStatus(JobExecutionStatus.RUNNING);
		executionLogService.save(executionLog);
		
		try {
			//执行调度业务
			Object bean = SpringUtils.getBean(beanId);
			//先查询无参的方法
			Method method = ReflectionUtils.findMethod(bean.getClass(), methodName);
			if (null != method) {
				ReflectionUtils.invokeMethod(method, bean);
			} else {
				//查询有参的方法
				method = ReflectionUtils.findMethod(bean.getClass(), methodName, Map.class);
				if (null != method) {
					Set<String> keySet = jobDataMap.keySet();
					Map<String, Object> params = new HashMap<String, Object>(keySet.size());
					//参数处理
					for (String key : keySet) {
						params.put(key, jobDataMap.get(key));
					}
					params.put(JobConstants.PARAM_EXECUTION_LOG, executionLog);
					ReflectionUtils.invokeMethod(method, bean, params);
				}
			}
			//更新结束日志
			executionLog.setStatus(JobExecutionStatus.SUCCESS);
			executionLog.setFinishedTime(new Date());
			executionLogService.update(executionLog);
		} catch (Exception e) {
			LOGGER.error("job execute error", e);
			//更新结束日志
			executionLog.setStatus(JobExecutionStatus.FAILURE);
			executionLog.setFinishedTime(new Date());
			executionLogService.update(executionLog);
		}
	}

}
