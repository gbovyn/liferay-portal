/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.user.associated.data.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADEntityDisplayTestCase {

	@Before
	public void setUp() throws Exception {
		_uadEntityAggregator = getUADEntityAggregator();
		_uadEntityDisplay = getUADEntityDisplay();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testGetApplicationName() {
		Assert.assertEquals(
			getApplicationName(), _uadEntityDisplay.getApplicationName());
	}

	@Test
	public void testGetDisplayFieldNames() {
		Assert.assertArrayEquals(
			getDisplayFieldNames(), _uadEntityDisplay.getDisplayFieldNames());
	}

	@Test
	public void testGetUADEntityTypeDescription() {
		Assert.assertEquals(
			getUADEntityTypeDescription(),
			_uadEntityDisplay.getUADEntityTypeDescription());
	}

	@Test
	public void testGetUADEntityTypeName() throws Exception {
		BaseModel baseModel = addBaseModel(_user.getUserId());

		String simpleClassName = StringUtil.extractLast(
			baseModel.getModelClassName(), StringPool.PERIOD);

		Assert.assertEquals(
			simpleClassName, _uadEntityDisplay.getUADEntityTypeName());
	}

	protected abstract BaseModel<?> addBaseModel(long userId) throws Exception;

	protected abstract String getApplicationName();

	protected abstract String[] getDisplayFieldNames();

	protected abstract UADEntityAggregator getUADEntityAggregator();

	protected abstract UADEntityDisplay getUADEntityDisplay();

	protected abstract String getUADEntityTypeDescription();

	private UADEntity _createUADEntity() throws Exception {
		addBaseModel(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		return uadEntities.get(0);
	}

	private UADEntityAggregator _uadEntityAggregator;
	private UADEntityDisplay _uadEntityDisplay;

	@DeleteAfterTestRun
	private User _user;

}