package net.pixaurora.kitty_music.impl.music;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MusicPathConverter {
	public static String titleCase(String snakeCase) {
		return String.join(" ", Stream.of(snakeCase.split("_")).map(StringUtils::capitalize).toList());
	}

	public static TrackInfo convertMusicPath(ResourceLocation soundPath) {
		String path = soundPath.getPath();
		String snakeCaseTrackTitle = path.substring(path.lastIndexOf("/") + 1);

		return new TrackInfo(Component.literal(titleCase(snakeCaseTrackTitle)));
	}
}
