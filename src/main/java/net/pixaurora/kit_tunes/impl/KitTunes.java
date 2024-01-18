package net.pixaurora.kit_tunes.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;

public class KitTunes {
	public static final String MOD_ID = "kit_tunes";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceLocation resource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
