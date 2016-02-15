/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jiangnan.es.common.entity.query.Page;
import com.jiangnan.es.common.web.PageView;
import com.jiangnan.es.common.web.controller.BaseController;
import com.jiangnan.es.schedule.entity.JobExecutionLogWrapper;
import com.jiangnan.es.schedule.entity.JobExecutionStatus;
import com.jiangnan.es.schedule.entity.JobTask;
import com.jiangnan.es.schedule.service.JobExecutionLogService;
import com.jiangnan.es.schedule.service.ScheduleService;

/**
 * @description 调度管理控制器
 * @author ywu@wuxicloud.com
 * 2016年2月3日 下午4:23:55
 */
@Controller
@RequestMapping("system/manage/schedule/")
public class JobTaskController extends BaseController {
	
	@Resource
	ScheduleService scheduleService;
	@Resource
	JobExecutionLogService executionLogService;
	
	/**
	 * 调度管理主页面
	 * @return
	 */
	@RequestMapping("/")
	public String main(Model model) {
		return "system/manage/schedule/list";
	}
	
	@RequestMapping(value = "/list")
	public void list(String methodName, String group, Date startExecuteTime, 
			Date endExecuteTime, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>(4);
		params.put("methodName", methodName);
		params.put("group", group);
		params.put("startExecuteTime", startExecuteTime);
		params.put("endExecuteTime", endExecuteTime);
		Page<JobTask> jobTasks = scheduleService.listMap(params);
		super.jsonResponse(response, new PageView<JobTask>(jobTasks));
	}
	
	/**
	 * 添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String toAdd(Model model) {
		addAction(model);
		return "system/manage/schedule/edit";
	}
	
	/**
	 * 添加调度
	 * @param jobTask
	 * @param response
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(JobTask jobTask, HttpServletResponse response) {
		scheduleService.save(jobTask);
		super.jsonResponse(response, ajaxResult(1, "调度添加成功"));
	}
	
	/**
	 * 修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable(value = "id") Integer id, Model model) {
		updateAction(model);
		JobTask jobTask = scheduleService.get(JobTask.class, id);
		model.addAttribute("jobTask", jobTask);
		return "system/manage/schedule/edit";
	}
	
	/**
	 * 更新调度
	 * @param jobTask
	 * @param response
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(JobTask jobTask, HttpServletResponse response) {
		scheduleService.update(jobTask);
		super.jsonResponse(response, ajaxResult(1, "调度修改成功"));
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value = "delete/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response) {
		scheduleService.remove(JobTask.class, id);
		super.jsonResponse(response, ajaxResult(1, "调度删除成功"));
	}
	
	/**
	 * 暂停
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "{id}/pause")
	public void pause(@PathVariable("id") Integer id, HttpServletResponse response) {
		scheduleService.pause(id);
		super.jsonResponse(response, ajaxResult(1, "调度暂停成功"));
	}
	
	/**
	 * 恢复
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "{id}/recover")
	public void recover(@PathVariable("id") Integer id, HttpServletResponse response) {
		scheduleService.recover(id);
		super.jsonResponse(response, ajaxResult(1, "调度恢复成功"));
	}
	
	/**
	 * 立即运行一次
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "{id}/run")
	public void runRightNow(@PathVariable("id") Integer id, HttpServletResponse response) {
		scheduleService.runRightNow(id);
		super.jsonResponse(response, ajaxResult(1, "运行成功"));
	}
	
	/**
	 * 运行记录页面
	 * @param jobId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "record/list/{id}")
	public String toRecordList(@PathVariable("id") Integer jobId, Model model) {
		model.addAttribute("jobId", jobId);
		return "system/manage/schedule/recordList";
	}
	
	/**
	 * 查看运行记录
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "{id}/records")
	public void runRecords(@PathVariable("id") Integer id, JobExecutionStatus status, 
			Date startBeginTime, Date endBeginTime, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>(4);
		params.put("id", id);
		params.put("status", status);
		params.put("startBeginTime", startBeginTime);
		params.put("endBeginTime", endBeginTime);
		Page<JobExecutionLogWrapper> jobExecutionLogs = executionLogService.listMap(params);
		super.jsonResponse(response, new PageView<JobExecutionLogWrapper>(jobExecutionLogs));
	}
}
