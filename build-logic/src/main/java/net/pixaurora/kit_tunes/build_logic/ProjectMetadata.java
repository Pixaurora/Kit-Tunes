package net.pixaurora.kit_tunes.build_logic;

import java.util.Map;

import org.gradle.api.Project;

public class ProjectMetadata {
	private final static String BASE_MOD_ID_KEY = "archives_base_name";
	private final static String SUB_MOD_ID_KEY = "sub_mod_name";

	private final Project project;

	public ProjectMetadata(Project project) {
		this.project = project;
	}

	public String mod_id() {
		Map<String, ?> properties = project.getProperties();

		String mod_id = String.valueOf(properties.get(BASE_MOD_ID_KEY));

		if (properties.containsKey(SUB_MOD_ID_KEY)) {
			mod_id += "_" + properties.get(SUB_MOD_ID_KEY);
		}

		return mod_id;
	}
}
