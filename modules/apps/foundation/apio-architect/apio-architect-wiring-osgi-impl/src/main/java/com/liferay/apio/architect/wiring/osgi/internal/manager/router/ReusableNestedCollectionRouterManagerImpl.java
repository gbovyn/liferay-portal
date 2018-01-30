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

package com.liferay.apio.architect.wiring.osgi.internal.manager.router;

import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.error.ApioDeveloperError.MustHaveValidGenericType;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.IdentifierClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ReusableNestedCollectionRouterManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ReusableNestedCollectionRouterManagerImpl
	extends BaseManager<ReusableNestedCollectionRouter, NestedCollectionRoutes>
	implements ReusableNestedCollectionRouterManager {

	public ReusableNestedCollectionRouterManagerImpl() {
		super(ReusableNestedCollectionRouter.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<NestedCollectionRoutes<T>>
		getNestedCollectionRoutesOptional(String name) {

		Optional<Class<T>> optional =
			_identifierClassManager.getIdentifierClassOptional(name);

		return optional.map(
			Class::getName
		).flatMap(
			this::getServiceOptional
		).map(
			routes -> (NestedCollectionRoutes<T>)routes
		);
	}

	@Override
	public <T> List<Operation> getOperations(Class<T> modelClass) {
		Optional<NestedCollectionRoutes> optional = getServiceOptional(
			modelClass);

		return optional.map(
			NestedCollectionRoutes::getOperations
		).orElseGet(
			Collections::emptyList
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected NestedCollectionRoutes map(
		ReusableNestedCollectionRouter reusableNestedCollectionRouter,
		ServiceReference<ReusableNestedCollectionRouter> serviceReference,
		Class<?> clazz) {

		Class<?> identifierClass = getTypeParamOrFail(
			reusableNestedCollectionRouter,
			ReusableNestedCollectionRouter.class, 1);

		ProvideFunction provideFunction =
			httpServletRequest -> provideClass ->
				_providerManager.provideOptional(
					provideClass, httpServletRequest);

		Optional<String> nameOptional = _nameManager.getNameOptional(
			clazz.getName());

		String name = nameOptional.orElseThrow(
			() -> new MustHaveValidGenericType(clazz));

		Builder builder = new Builder<>(
			clazz, "r", name, identifierClass, provideFunction);

		return reusableNestedCollectionRouter.collectionRoutes(builder);
	}

	@Reference
	private IdentifierClassManager _identifierClassManager;

	@Reference
	private NameManager _nameManager;

	@Reference
	private ProviderManager _providerManager;

}