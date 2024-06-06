package net.pixaurora.kit_tunes.impl.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import net.pixaurora.kit_tunes.impl.Constants;

public class KitTunesThreadFactory implements ThreadFactory {
	private final AtomicInteger counter;
	private final ThreadFactory parent;

	public KitTunesThreadFactory() {
		this.counter = new AtomicInteger();
		this.parent = Executors.defaultThreadFactory();
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = this.parent.newThread(r);

		thread.setName(Constants.MOD_ID + "-" + counter.incrementAndGet());

		return thread;
	}
}
