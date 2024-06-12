package net.pixaurora.kit_tunes.impl.ui.toast;

import java.util.List;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.ui.Component;
import net.pixaurora.kit_tunes.impl.ui.math.Size;

public interface KitTunesToastData {
	public ResourcePath icon();

	public Size iconSize();

	public Component title();

	public List<Component> messageLines();

	public ToastBackground background();
}