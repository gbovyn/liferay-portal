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

package com.liferay.blogs.internal.util;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryReference;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public class BlogsEntryAttachmentFileEntryUtil {

	public static List<BlogsEntryAttachmentFileEntryReference>
			addBlogsEntryAttachmentFileEntries(
				long groupId, long userId, long blogsEntryId, long folderId,
				List<FileEntry> tempFileEntries)
		throws PortalException {

		List<BlogsEntryAttachmentFileEntryReference>
			blogsEntryAttachmentFileEntryReferences = new ArrayList<>();

		for (FileEntry tempFileEntry : tempFileEntries) {
			FileEntry blogsEntryAttachmentFileEntry =
				addBlogsEntryAttachmentFileEntry(
					groupId, userId, blogsEntryId, folderId,
					tempFileEntry.getTitle(), tempFileEntry.getMimeType(),
					tempFileEntry.getContentStream());

			blogsEntryAttachmentFileEntryReferences.add(
				new BlogsEntryAttachmentFileEntryReferenceAdapter(
					tempFileEntry.getFileEntryId(),
					blogsEntryAttachmentFileEntry));
		}

		return blogsEntryAttachmentFileEntryReferences;
	}

	public static FileEntry addBlogsEntryAttachmentFileEntry(
			long groupId, long userId, long blogsEntryId, long folderId,
			String fileName, String mimeType, byte[] bytes)
		throws PortalException {

		String uniqueFileName = _getUniqueFileName(groupId, fileName, folderId);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), blogsEntryId,
			BlogsConstants.SERVICE_NAME, folderId, bytes, uniqueFileName,
			mimeType, true);
	}

	public static FileEntry addBlogsEntryAttachmentFileEntry(
			long groupId, long userId, long blogsEntryId, long folderId,
			String fileName, String mimeType, File file)
		throws PortalException {

		String uniqueFileName = _getUniqueFileName(groupId, fileName, folderId);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), blogsEntryId,
			BlogsConstants.SERVICE_NAME, folderId, file, uniqueFileName,
			mimeType, true);
	}

	public static FileEntry addBlogsEntryAttachmentFileEntry(
			long groupId, long userId, long blogsEntryId, long folderId,
			String fileName, String mimeType, InputStream is)
		throws PortalException {

		String uniqueFileName = _getUniqueFileName(groupId, fileName, folderId);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), blogsEntryId,
			BlogsConstants.SERVICE_NAME, folderId, is, uniqueFileName, mimeType,
			true);
	}

	public static List<FileEntry> getTempBlogsEntryAttachmentFileEntries(
			String content)
		throws PortalException {

		List<FileEntry> tempBlogsEntryAttachmentFileEntries = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			EditorConstants.ATTRIBUTE_DATA_IMAGE_ID + "=.(\\d+)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			long fileEntryId = GetterUtil.getLong(matcher.group(1));

			FileEntry tempFileEntry =
				PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);

			tempBlogsEntryAttachmentFileEntries.add(tempFileEntry);
		}

		return tempBlogsEntryAttachmentFileEntries;
	}

	private static FileEntry _fetchPortletFileEntry(
		long groupId, String fileName, long folderId) {

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				groupId, folderId, fileName);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	private static String _getUniqueFileName(
			long groupId, String fileName, long folderId)
		throws PortalException {

		fileName = FileUtil.stripParentheticalSuffix(fileName);

		FileEntry fileEntry = _fetchPortletFileEntry(
			groupId, fileName, folderId);

		if (fileEntry == null) {
			return fileName;
		}

		int suffix = 1;

		for (int i = 0; i < _UNIQUE_FILE_NAME_TRIES; i++) {
			String curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));

			fileEntry = _fetchPortletFileEntry(groupId, curFileName, folderId);

			if (fileEntry == null) {
				return curFileName;
			}

			suffix++;
		}

		throw new PortalException(
			StringBundler.concat(
				"Unable to get a unique file name for ", fileName,
				" in folder ", String.valueOf(folderId)));
	}

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryAttachmentFileEntryUtil.class);

}