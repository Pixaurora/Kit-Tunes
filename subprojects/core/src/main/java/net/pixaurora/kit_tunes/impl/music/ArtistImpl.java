package net.pixaurora.kit_tunes.impl.music;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.gson.annotations.SerializedName;

import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.resource.TransformsInto;

public class ArtistImpl implements Artist {
	private final ResourcePath path;

	private final String name;
	private final Optional<ResourcePath> iconPath;

	public ArtistImpl(ResourcePath path, String name, Optional<ResourcePath> iconPath) {
		this.path = path;
		this.name = name;
		this.iconPath = iconPath;
	}

	@Override
	public ResourcePath path() {
		return this.path;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public Optional<ResourcePath> iconPath() {
		return this.iconPath;
	}

	public static class Data implements TransformsInto<Artist> {
		private final String name;
		@SerializedName("icon_path")
		@Nullable
		private final ResourcePath iconPath;

		public Data(String name, @Nullable ResourcePath iconPath) {
			this.name = name;
			this.iconPath = iconPath;
		}

		public Artist transform(ResourcePath path) {
			return new ArtistImpl(path, this.name, Optional.ofNullable(this.iconPath));
		}
	}

}
