/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jiangnan.es.common.entity.query.Page;
import com.jiangnan.es.common.exception.ApplicationException;
import com.jiangnan.es.common.repository.BaseRepository;
import com.jiangnan.es.orm.mybatis.plugin.PageHelper;
import com.jiangnan.es.orm.mybatis.service.MybatisBaseServiceSupport;
import com.jiangnan.es.schedule.JobConstants;
import com.jiangnan.es.schedule.dao.JobTaskDao;
import com.jiangnan.es.schedule.entity.JobStatus;
import com.jiangnan.es.schedule.entity.JobTask;
import com.jiangnan.es.schedule.job.QuartzJobFactory;
import com.jiangnan.es.schedule.service.ScheduleService;
import com.jiangnan.es.util.CollectionUtils;
import com.jiangnan.es.util.StringUtils;

/**
 * @description 调度业务实现类
 * @author ywu@wuxicloud.com
 * 2016年2月3日 下午5:28:24
 */
@Service
public class ScheduleServiceImpl extends MybatisBaseServiceSupport<JobTask> implements ScheduleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
	@Resource(name = "clusterScheduler")
	Scheduler clusterScheduler;
	@Resource
	JobTaskDao jobTaskDao;
	
	@Override
	public JobTask save(JobTask entity) {
		//持久化
		super.save(entity);
		//参数
		
		//将调度放入schedule
		try {
			scheduleJob(entity);
		} catch (SchedulerException e) {
			LOGGER.error("调度创建失败", e);
			throw new ApplicationException(e);
		}
		
		return entity;
	}
	
	private void scheduleJob(JobTask jobTask) throws SchedulerException {
		String jobName = jobTask.getBeanId() + "_" + jobTask.getMethodName();
		String jobGroup = jobTask.getGroup();
		TriggerKey triggerKey = extractTriggerKey(jobTask);;
		
		//获取trigger
		CronTrigger trigger = (CronTrigger)clusterScheduler.getTrigger(triggerKey);
		if (null == trigger) {
			JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
					.withIdentity(jobName, jobTask.getGroup())
					.build();
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobTask.getExpression());
			//按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger()
									.withIdentity(jobName, jobGroup)
									.withSchedule(scheduleBuilder)
									.build();
			
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			jobDataMap.put(JobConstants.PARAM_JOB_ID, jobTask.getId());
			jobDataMap.put(JobConstants.PARAM_BEAN_ID, jobTask.getBeanId());
			jobDataMap.put(JobConstants.PARAM_METHOD_NAME, jobTask.getMethodName());
			
			//设置调度参数
			String params = jobTask.getParams();
			addJobParam(jobDataMap, params);
			
			clusterScheduler.scheduleJob(jobDetail, trigger);
		} else {
			//trigger已存在,更新
			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobTask.getExpression());
			//按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder()
							.withIdentity(triggerKey)
							.withSchedule(scheduleBuilder)
							.build();
			//按新的trigger重新设置job执行
			clusterScheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	/**
	 * 添加调度参数
	 * @param jobDataMap
	 * @param params
	 */
	private void addJobParam(JobDataMap jobDataMap, String params) {
		if (StringUtils.hasText(params)) {
			String[] paramArray = params.split(JobConstants.PARAM_JOB_PARAMS_SPLIT);
			for (String paramValue : paramArray) {
				String[] pv = paramValue.split(JobConstants.PARAM_JOB_PARAM_SPLIT);
				String paramName = pv[0].trim();
				String pValue = pv[1].trim();
				jobDataMap.put(paramName, pValue);
			}
		}
	}
	
	@Override
	public JobTask update(JobTask entity) {
		super.update(entity);
		try {
			//删除原有的调度
			removeJob(entity);
			//重新创建调度
			scheduleJob(entity);
		} catch (SchedulerException e) {
			LOGGER.error("update job error", e);
			throw new ApplicationException(e);
		}
		return entity;
	}
	
	@Override
	public void remove(Class<JobTask> clazz, Serializable id) {
		JobTask jobTask = get(clazz, id);
		super.remove(clazz, id);
		try {
			removeJob(jobTask);
		} catch (SchedulerException e) {
			LOGGER.error("error in remove job", e);
			throw new ApplicationException(e);
		}
	}
	
	private void removeJob(JobTask jobTask) throws SchedulerException {
		JobKey jobKey = extractJobKey(jobTask);
		if (clusterScheduler.checkExists(jobKey)) {
			clusterScheduler.deleteJob(jobKey);
		}
	}
	
	private TriggerKey extractTriggerKey(JobTask jobTask) {
		String jobName = jobTask.getBeanId() + "_" + jobTask.getMethodName();
		String jobGroup = jobTask.getGroup();
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		return triggerKey;
	}
	
	private JobKey extractJobKey(JobTask jobTask) {
		String jobName = jobTask.getBeanId() + "_" + jobTask.getMethodName();
		String jobGroup = jobTask.getGroup();
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		return jobKey;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<JobTask> list(JobTask params) {
		Page<JobTask> jobTasks = super.list(params);
		List<JobTask> data = jobTasks.getData();
		if (!CollectionUtils.isEmpty(data)) {
			for (JobTask jobTask : data) {
				try {
					setExecuteTime(jobTask);
				} catch (SchedulerException e) {
					LOGGER.error("error in set job execute time", e);
				}
			}
		}
		return jobTasks;
	}
	
	/**
	 * 设置调度执行时间
	 * @param jobTask
	 * @throws SchedulerException
	 */
	private void setExecuteTime(JobTask jobTask) throws SchedulerException {
		TriggerKey triggerKey = extractTriggerKey(jobTask);
		Trigger trigger = clusterScheduler.getTrigger(triggerKey);
		if (null != trigger) {
			jobTask.setNextExecuteTime(trigger.getNextFireTime());
			jobTask.setFirstExecuteTime(trigger.getStartTime());
			jobTask.setPrevioustExecuteTime(trigger.getPreviousFireTime());
		}
	}
	
	@Override
	protected BaseRepository<JobTask> getRepository() {
		return jobTaskDao;
	}

	public Page<JobTask> listMap(Map<String, Object> params) {
		PageHelper.startPage();
		List<JobTask> jobTasks = jobTaskDao.list(params);
		if (!CollectionUtils.isEmpty(jobTasks)) {
			for (JobTask jobTask : jobTasks) {
				try {
					setExecuteTime(jobTask);
				} catch (SchedulerException e) {
					LOGGER.error("error in set job execute time", e);
				}
			}
		}
		return PageHelper.getPageResult(jobTasks);
	}

	@Override
	public void pause(Integer id) {
		JobTask jobTask = get(JobTask.class, id);
		if (null != jobTask) {
			//更新调度任务状态
			jobTask.setStatus(JobStatus.PAUSED);
			update(jobTask);
			
			//暂定调度
			JobKey jobKey = extractJobKey(jobTask);
			try {
				boolean exist = clusterScheduler.checkExists(jobKey);
				if (exist) {
					clusterScheduler.pauseJob(jobKey);
				}
			} catch (SchedulerException e) {
				LOGGER.error("pause job error", e);
				throw new ApplicationException(e);
			}
		}
	}

	@Override
	public void recover(Integer id) {
		JobTask jobTask = get(JobTask.class, id);
		if (null != jobTask) {
			//更新调度状态
			jobTask.setStatus(JobStatus.RUNNING);
			update(jobTask);
			
			//恢复调度
			JobKey jobKey = extractJobKey(jobTask);
			try {
				boolean exist = clusterScheduler.checkExists(jobKey);
				if (exist) {
					clusterScheduler.resumeJob(jobKey);
				}
			} catch (SchedulerException e) {
				LOGGER.error("recover job error", e);
				throw new ApplicationException(e);
			}
		}
	}

	@Override
	public void runRightNow(Integer id) {
		JobTask jobTask = get(JobTask.class, id);
		if (null != jobTask) {
			JobKey jobKey = extractJobKey(jobTask);
			try {
				if (clusterScheduler.checkExists(jobKey)) {
					clusterScheduler.triggerJob(jobKey);
				}
			} catch (SchedulerException e) {
				LOGGER.error("run job error", e);
				throw new ApplicationException(e);
			}
		}
		
	}

}
