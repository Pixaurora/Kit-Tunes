package net.pixaurora.kit_tunes.impl.compat;

import java.util.Map;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.minecraft.client.gui.screens.Screen;
import net.pixaurora.kit_tunes.impl.Constants;
import net.pixaurora.kit_tunes.impl.KitTunes;
import net.pixaurora.kit_tunes.impl.gui.KitTunesScreenImpl;

public class ModMenuIntegration implements ModMenuApi {
	public KitTunesScreenImpl modHomeScreen(Screen parent) {
		return new KitTunesScreenImpl(parent);
	}

	public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
		return Map.of(Constants.MOD_ID, this::modHomeScreen);
	}
}
