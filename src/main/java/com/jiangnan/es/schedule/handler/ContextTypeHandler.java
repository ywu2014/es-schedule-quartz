/**
 * Copyright (c) 2015-2016 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jiangnan.es.schedule.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.jiangnan.es.util.CollectionUtils;
import com.jiangnan.es.util.JsonUtils;
import com.jiangnan.es.util.StringUtils;

/**
 * @description JobExecutionLog context type handler
 * @author ywu@wuxicloud.com
 * 2016年2月15日 下午5:02:10
 */
public class ContextTypeHandler implements TypeHandler<Map<String, Object>> {

	@Override
	public void setParameter(PreparedStatement ps, int i,
			Map<String, Object> parameter, JdbcType jdbcType)
			throws SQLException {
		if (CollectionUtils.isEmpty(parameter)) {
			ps.setNull(i, Types.VARCHAR);
		} else {
			ps.setString(i, JsonUtils.object2JsonString(parameter));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getResult(ResultSet rs, String columnName)
			throws SQLException {
		String context = rs.getString(columnName);
		if (StringUtils.hasText(context)) {
			return JsonUtils.jsonString2Object(context, Map.class);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String context = rs.getString(columnIndex);
		if (StringUtils.hasText(context)) {
			return JsonUtils.jsonString2Object(context, Map.class);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String context = cs.getString(columnIndex);
		if (StringUtils.hasText(context)) {
			return JsonUtils.jsonString2Object(context, Map.class);
		}
		return null;
	}
	
}
