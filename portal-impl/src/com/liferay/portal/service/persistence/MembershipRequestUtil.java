/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MembershipRequestUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MembershipRequestUtil {
	public static com.liferay.portal.model.MembershipRequest create(
		long membershipRequestId) {
		return getPersistence().create(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest remove(
		long membershipRequestId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(membershipRequestId));
		}

		com.liferay.portal.model.MembershipRequest membershipRequest = getPersistence()
																		   .remove(membershipRequestId);

		if (listener != null) {
			listener.onAfterRemove(membershipRequest);
		}

		return membershipRequest;
	}

	public static com.liferay.portal.model.MembershipRequest remove(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(membershipRequest);
		}

		membershipRequest = getPersistence().remove(membershipRequest);

		if (listener != null) {
			listener.onAfterRemove(membershipRequest);
		}

		return membershipRequest;
	}

	public static com.liferay.portal.model.MembershipRequest update(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = membershipRequest.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(membershipRequest);
			}
			else {
				listener.onBeforeUpdate(membershipRequest);
			}
		}

		membershipRequest = getPersistence().update(membershipRequest);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(membershipRequest);
			}
			else {
				listener.onAfterUpdate(membershipRequest);
			}
		}

		return membershipRequest;
	}

	public static com.liferay.portal.model.MembershipRequest update(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = membershipRequest.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(membershipRequest);
			}
			else {
				listener.onBeforeUpdate(membershipRequest);
			}
		}

		membershipRequest = getPersistence().update(membershipRequest,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(membershipRequest);
			}
			else {
				listener.onAfterUpdate(membershipRequest);
			}
		}

		return membershipRequest;
	}

	public static com.liferay.portal.model.MembershipRequest findByPrimaryKey(
		long membershipRequestId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByPrimaryKey(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest fetchByPrimaryKey(
		long membershipRequestId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(membershipRequestId);
	}

	public static java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByGroupId_PrevAndNext(
		long membershipRequestId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByGroupId_PrevAndNext(membershipRequestId,
			groupId, obc);
	}

	public static java.util.List findByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByUserId_PrevAndNext(
		long membershipRequestId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByUserId_PrevAndNext(membershipRequestId,
			userId, obc);
	}

	public static java.util.List findByG_S(long groupId, int statusId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, statusId);
	}

	public static java.util.List findByG_S(long groupId, int statusId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, statusId, begin, end);
	}

	public static java.util.List findByG_S(long groupId, int statusId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, statusId, begin, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByG_S_First(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByG_S_First(groupId, statusId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByG_S_Last(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByG_S_Last(groupId, statusId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByG_S_PrevAndNext(
		long membershipRequestId, long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchMembershipRequestException {
		return getPersistence().findByG_S_PrevAndNext(membershipRequestId,
			groupId, statusId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByG_S(long groupId, int statusId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_S(groupId, statusId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByG_S(long groupId, int statusId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_S(groupId, statusId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static MembershipRequestPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(MembershipRequestPersistence persistence) {
		_persistence = persistence;
	}

	private static MembershipRequestUtil _getUtil() {
		if (_util == null) {
			_util = (MembershipRequestUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = MembershipRequestUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.MembershipRequest"));
	private static Log _log = LogFactory.getLog(MembershipRequestUtil.class);
	private static MembershipRequestUtil _util;
	private MembershipRequestPersistence _persistence;
}