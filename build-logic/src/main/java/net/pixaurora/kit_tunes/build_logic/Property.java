package net.pixaurora.kit_tunes.build_logic;

public enum Property {
    BASE_MOD_ID("base_mod_name"),
	SUB_MOD_ID("sub_mod_name"),
	MOD_VERSION("mod_version"),
    UPDATE_TITLE("update_title"),
    MINECRAFT_VERSION_MIN("minecraft_version_min"),
    IS_MAIN_PROJECT("main_project");

    private final String key;

    Property(String key) {
        this.key = key;
    }

    public String key() {
        return this.key;
    }
}
