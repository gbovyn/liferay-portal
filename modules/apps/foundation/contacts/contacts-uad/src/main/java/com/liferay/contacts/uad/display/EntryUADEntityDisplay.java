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

package com.liferay.contacts.uad.display;

import com.liferay.contacts.uad.constants.ContactsUADConstants;
import com.liferay.contacts.uad.entity.EntryUADEntity;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.BaseUADEntityDisplay;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + ContactsUADConstants.CLASS_NAME_ENTRY}, service = UADEntityDisplay.class)
public class EntryUADEntityDisplay extends BaseUADEntityDisplay {
	@Override
	public String getEditURL(UADEntity uadEntity,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse)
		throws Exception {
		EntryUADEntity entryUADEntity = (EntryUADEntity)uadEntity;

		return _entryUADEntityDisplayHelper.getEntryEditURL(entryUADEntity.getEntry(),
			liferayPortletRequest, liferayPortletResponse);
	}

	@Override
	public String getUADEntityTypeDescription() {
		return "";
	}

	@Override
	public String getUADEntityTypeName() {
		return "Entry";
	}

	@Override
	public List<String> getUADEntityTypeNonanonymizableFieldNamesList() {
		return _uadEntityAnonymizer.getUADEntityNonanonymizableFieldNames();
	}

	@Reference
	private EntryUADEntityDisplayHelper _entryUADEntityDisplayHelper;
	@Reference(target = "(model.class.name=" +
	ContactsUADConstants.CLASS_NAME_ENTRY + ")")
	private UADEntityAnonymizer _uadEntityAnonymizer;
}