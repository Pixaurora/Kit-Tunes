package net.pixaurora.kit_tunes.impl.music;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.gson.annotations.SerializedName;

import net.pixaurora.kit_tunes.impl.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.impl.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.resource.TransformsInto;

public class ArtistImpl implements Artist {
	private final ResourcePath path;

	private final String name;
	private final Optional<NamespacedResourcePath> iconPath;

	public ArtistImpl(ResourcePath path, String name, Optional<NamespacedResourcePath> iconPath) {
		this.path = path;
		this.name = name;
		this.iconPath = iconPath;
	}

	public ResourcePath path() {
		return this.path;
	}

	public String name() {
		return this.name;
	}

	public Optional<NamespacedResourcePath> imagePath() {
		return this.iconPath;
	}

	public static class Data implements TransformsInto<Artist> {
		private final String name;
		@SerializedName("icon_path")
		@Nullable
		private final NamespacedResourcePath iconPath;

		public Data(String name, @Nullable NamespacedResourcePath iconPath) {
			this.name = name;
			this.iconPath = iconPath;
		}

		public Artist transform(ResourcePath path) {
			return new ArtistImpl(path, this.name, Optional.ofNullable(this.iconPath));
		}
	}

}
