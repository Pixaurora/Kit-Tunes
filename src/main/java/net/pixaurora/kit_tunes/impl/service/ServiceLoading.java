package net.pixaurora.kit_tunes.impl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceLoading {
	public static <T> List<T> loadAll(Class<T> serviceType) {
		List<T> services = new ArrayList<>();

		for (T service : ServiceLoader.load(serviceType)) {
			services.add(service);
		}

		return services;
	}

	public static <T> T loadJustOneOrThrow(Class<T> serviceType) {
		List<T> services = loadAll(serviceType);

		if (services.size() == 1) {
			return services.get(0);
		} else {
			throw new RuntimeException("Expected exactly 1 of `" + serviceType.getCanonicalName() + "` but " + services.size() + " were loaded!");
		}
	}
}
