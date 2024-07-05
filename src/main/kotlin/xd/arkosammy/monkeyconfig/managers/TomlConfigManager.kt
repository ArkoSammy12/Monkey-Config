package xd.arkosammy.monkeyconfig.managers

import com.electronwill.nightconfig.toml.TomlFormat
import xd.arkosammy.monkeyconfig.settings.ConfigSetting
import xd.arkosammy.monkeyconfig.groups.SettingGroup
import xd.arkosammy.monkeyconfig.groups.MutableSettingGroup
import java.nio.file.Path

open class TomlConfigManager : AbstractConfigManager {

    constructor(
        configName: String,
        settingGroups: List<SettingGroup>,
        configPath: Path
    ) : super(configName, settingGroups, TomlFormat.instance(), configPath.resolve("$configName.toml"))

    constructor(
        configName: String,
        settingGroups: List<MutableSettingGroup>? = null,
        settingBuilders: List<ConfigSetting.Builder<*, *, *>>,
        configPath: Path
    ) : super(configName, settingGroups, settingBuilders, TomlFormat.instance(), configPath.resolve("$configName.toml"))

}