package net.pixaurora.kit_tunes.impl;

import net.minecraft.network.chat.MutableComponent;
import net.pixaurora.kit_tunes.impl.ui.text.Component;

public class FakeComponent implements Component {
	protected final MutableComponent parent;

	public FakeComponent(MutableComponent component) {
		this.parent = component;
	}

	public MutableComponent gameVer() {
		return this.parent;
	}

	@Override
	public Component concat(Component component) {
		return new FakeComponent(this.parent.copy().append(KitTunesUIImpl.componentToMinecraftType(component)));
	}
}
