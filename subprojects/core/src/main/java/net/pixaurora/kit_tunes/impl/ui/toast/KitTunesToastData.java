package net.pixaurora.kit_tunes.impl.ui.toast;

import java.util.List;

import net.pixaurora.kit_tunes.api.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.impl.ui.Component;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface KitTunesToastData {
	public NamespacedResourcePath icon();

	public Size iconSize();

	public Component title();

	public List<Component> messageLines();

	public KitTunesToastBackground background();
}
