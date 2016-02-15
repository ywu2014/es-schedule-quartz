/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.entity;

/**
 * @description 调度运行状态
 * @author ywu@wuxicloud.com
 * 2016年2月3日 下午3:51:44
 */
public enum JobExecutionStatus {
	RUNNING{
		@Override
		public String getName() {
			return "运行中...";
		}
		
	}, SUCCESS{
		@Override
		public String getName() {
			return "成功";
		}
	}, FAILURE{
		@Override
		public String getName() {
			return "失败";
		}
	};
	
	public abstract String getName();
}
